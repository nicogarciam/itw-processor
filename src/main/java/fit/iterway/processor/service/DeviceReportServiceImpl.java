package fit.iterway.processor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.iterway.processor.dtos.DeviceReportDTO;
import fit.iterway.processor.dtos.DeviceSpeedResumeDTO;
import fit.iterway.processor.dtos.DeviceViewDTO;
import fit.iterway.processor.mappers.DeviceSpeedResumeMapper;
import fit.iterway.processor.model.DeviceMoveHistory;
import fit.iterway.processor.model.DeviceSpeedResume;
import fit.iterway.processor.repository.DeviceMoveHistoryRepository;
import fit.iterway.processor.repository.DeviceSpeedResumeRepository;
import fit.iterway.processor.utils.GpsUtils;
import fit.iterway.processor.utils.NumberHelper;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DeviceReportServiceImpl implements DeviceReportService {

    static Logger LOG;

    private DeviceMoveHistoryRepository deviceMoveHistoryRepository;

    private DeviceSpeedResumeRepository deviceSpeedResumeRepository;

    private DeviceSpeedResumeMapper deviceSpeedResumeMapper;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private final RabbitTemplate rabbitTemplate;


    public DeviceReportServiceImpl(DeviceMoveHistoryRepository deviceMoveHistoryRepository,
                                   DeviceSpeedResumeRepository deviceSpeedResumeRepository, RabbitTemplate rabbitTemplate,
                                   DeviceSpeedResumeMapper deviceSpeedResumeMapper) {
        this.deviceMoveHistoryRepository = deviceMoveHistoryRepository;
        this.deviceSpeedResumeRepository = deviceSpeedResumeRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.deviceSpeedResumeMapper = deviceSpeedResumeMapper;
    }

    @Override
    public void generateMoveHistory(DeviceReportDTO report) {
        DeviceMoveHistory lastMoveHist = deviceMoveHistoryRepository.findFirstByDeviceIdOrderByReportDateDesc(report.getDeviceId());
        DeviceMoveHistory deviceMoveHistory;
        DeviceReportDTO lastReport = null;

        if (lastMoveHist == null || lastMoveHist.getReportTime() > 60 || lastMoveHist.isClosed()) {
            lastMoveHist = new DeviceMoveHistory();
            lastMoveHist.setDateIni(report.getDate());
        }
        deviceMoveHistory = lastMoveHist;

        deviceMoveHistory.setDeviceId(report.getDeviceId());

        Double distanceSinceLastLatLong = 0.0;
        double distanceSinceLastOdometer = 0.0;
        boolean moving = report.getGpsSpeed() > 0;

        double timeBetweenReports = 0.0;

        if (lastReport != null && lastReport.getLatitude() != null && lastReport.getLongitude() != null) {
            long durationSeconds = TimeUnit.MILLISECONDS.toSeconds(report.getDate().getTime() - lastReport.getDate().getTime());
            durationSeconds = durationSeconds > 0 ? durationSeconds : 0;
            timeBetweenReports = (double) durationSeconds / 60;
            distanceSinceLastLatLong = GpsUtils.distance(report.getLatitude(), report.getLongitude(),
                    lastReport.getLatitude(), lastReport.getLongitude(), "K");
        } else {
            LOG.warn("Cannot calculate distanceSinceLastLatLong and timeBetweenReports for device " + report.getDeviceId() + ". LastReport: " + lastReport);
        }

        if (report.getCot() != null && lastReport != null && lastReport.getOdometer() >= 0) {
            distanceSinceLastOdometer = lastMoveHist.getDistanceOdometer() + (report.getCot().getOdometer() - lastReport.getOdometer());
        } else {
            LOG.warn("Cannot calculate distanceSinceLastOdometer for device " + report.getDeviceId() + ". LastReport: " + lastReport + ", cot: " + report.getCot());
        }

        if (moving)
            deviceMoveHistory.setMoveTime(deviceMoveHistory.getMoveTime() + timeBetweenReports);
        else
            deviceMoveHistory.setStopTime(deviceMoveHistory.getStopTime() + timeBetweenReports);


        Double avg = (report.getGpsSpeed() + deviceMoveHistory.getSpeedAvg()) / 2;
        deviceMoveHistory.setSpeedAvg(NumberHelper.formatearDecimales(avg, 2));
        deviceMoveHistory.setSpeedMax((report.getGpsSpeed() > deviceMoveHistory.getSpeedMax())
                ? report.getGpsSpeed()
                : deviceMoveHistory.getSpeedMax());

        deviceMoveHistory.setDistanceLatLong(lastMoveHist.getDistanceLatLong() + distanceSinceLastLatLong);
        deviceMoveHistory.setCity(report.getLocationDTO().getAddress().getCity());
        deviceMoveHistory.setCountry(report.getLocationDTO().getAddress().getCountry());
        deviceMoveHistory.setState(report.getLocationDTO().getAddress().getState());
        deviceMoveHistory.setRoad(report.getLocationDTO().getAddress().getRoad());
        deviceMoveHistory.setDistanceOdometer(distanceSinceLastOdometer);
        deviceMoveHistory.setReportTime((deviceMoveHistory.getReportTime() + timeBetweenReports));
        deviceMoveHistory.setCantReports(deviceMoveHistory.getCantReports() + 1);
        // Ver si tiene un vehiculo o una persona asignada
        deviceMoveHistory.setReportDate(report.getDate());
        deviceMoveHistoryRepository.save(deviceMoveHistory);

    }


    @Transactional
    @Override
    public void saveSpeedResume(DeviceReportDTO deviceReport, DeviceViewDTO deviceViewDTO) {
        /* speed resume list to refresh*/
        LOG.debug("SaveSpeedResume report id: " + deviceReport.getId());
        LOG.debug("SaveSpeedResume date From: " + deviceReport.getDate());
        List<DeviceSpeedResume> deviceSpeedResumes = new ArrayList<>(0);

        /*speed resume previous to date and device id */
        Page<DeviceSpeedResume> deviceSpeedResumeListPrevious;
        deviceSpeedResumeListPrevious = deviceSpeedResumeRepository.
                getPreviousToDateAndDevice(deviceReport.getDate(), deviceReport.getDeviceId(),
                        PageRequest.of(0, 1));
        DeviceSpeedResume deviceSpeedResumePrevious = deviceSpeedResumeListPrevious.hasContent() ?
                deviceSpeedResumeListPrevious.getContent().get(0) : null;

        /* speed resume list late to date and device id*/
        Page<DeviceSpeedResume> deviceSpeedResumeListLate;
        deviceSpeedResumeListLate = deviceSpeedResumeRepository.
                getListLateToDateAndDevice(deviceReport.getDate(), deviceReport.getDeviceId(),
                        PageRequest.of(0, 1));
        DeviceSpeedResume deviceSpeedResumeLate = deviceSpeedResumeListLate.hasContent() ?
                deviceSpeedResumeListLate.getContent().get(0) : null;

        /*Set properties*/
        DeviceSpeedResume deviceSpeedResume = new DeviceSpeedResume();
        deviceSpeedResume.setProperties(deviceReport);

        String address = deviceViewDTO.getDeviceState().getAddressDetail();

        //If is the first speed resume, only create
        if (deviceSpeedResumePrevious == null) {
            this.createDeviceSpeedResume(deviceSpeedResume, address, deviceReport.getId());
        }
        //else if not the first speed resume
        else {
            this.compareDeviceSpeedResume(deviceSpeedResume, address, deviceSpeedResumePrevious, true,
                    deviceReport.getId());
            //if exist previous and exist next
            if (deviceSpeedResumeLate != null) {
                /*if create speed resume send 'deviceSpeedResume'
               but if update send 'deviceSpeedResumePrevious'*/
                this.compareDeviceSpeedResume(deviceSpeedResumeLate, address,
                        deviceSpeedResume.getId() != null ?
                                deviceSpeedResume : deviceSpeedResumePrevious, false, deviceReport.getId());
            }
        }
    }

    @Transactional
    void compareDeviceSpeedResume(DeviceSpeedResume deviceSpeedResume, String address,
                                  DeviceSpeedResume deviceSpeedResumePrevious, boolean insertNew,
                                  Long reportId) {

        // si el tiempo entre la ultima actualización del ResumenPrevio y el Resumen Llegado
        // Supera el Tiempo de Reporte del Device
        long reportIntervalMili = 1200000; //20 minutos
        long diffInMilies = Math.abs(deviceSpeedResume.getDateFrom().getTime() - deviceSpeedResumePrevious.getDateTo().getTime());
        if (diffInMilies > reportIntervalMili) {
            // cerrar la anterior con fecha de cierre =
            Date dateTo = new Date(deviceSpeedResumePrevious.getDateTo().getTime() + reportIntervalMili);
            deviceSpeedResumePrevious.setReportCloseId(reportId);
            deviceSpeedResumePrevious.setDateTo(dateTo);
            deviceSpeedResumePrevious.calculateDuration();
            deviceSpeedResumePrevious.setLastUpdate(deviceSpeedResume.getLastUpdate());
            deviceSpeedResumePrevious.setReportCount(deviceSpeedResumePrevious.getReportCount() + 1);
            deviceSpeedResumePrevious.calculateRelanti(
                    reportIntervalMili);
            saveDeviceSpeedResumeRepository(deviceSpeedResumePrevious);
            // Crear otro resumen con estado Unknown
            DeviceSpeedResume resumeUnknown = new DeviceSpeedResume();
            resumeUnknown.setUnknownProperties(dateTo, deviceSpeedResume.getDateFrom(), deviceSpeedResume.getDeviceId());
            saveDeviceSpeedResumeRepository(resumeUnknown);
            this.createDeviceSpeedResume(deviceSpeedResume, address, reportId);
        } else if (deviceSpeedResumePrevious.getState().equals(deviceSpeedResume.getState())) {
            //Refresh date to, duration, last update, max speed and min speed
            this.updateSameStateDeviceSpeedResume(deviceSpeedResume, address, deviceSpeedResumePrevious, reportId);
        } else {
            this.updateCloseDeviceSpeedResume(deviceSpeedResume, address, deviceSpeedResumePrevious, reportId);
            /*Create - new resume*/
            if (insertNew)
                this.createDeviceSpeedResume(deviceSpeedResume, address, reportId);
        }

    }

    @Transactional
    void updateSameStateDeviceSpeedResume(DeviceSpeedResume deviceSpeedResume, String address,
                                          DeviceSpeedResume deviceSpeedResumePrevious, Long reportId) {
        deviceSpeedResumePrevious.setReportCloseId(reportId);
        deviceSpeedResumePrevious.setDateTo(deviceSpeedResume.getDateFrom());
        deviceSpeedResumePrevious.calculateDuration();
        deviceSpeedResumePrevious.setLastUpdate(deviceSpeedResume.getLastUpdate());
        deviceSpeedResumePrevious.setFinalAddress(address);
        deviceSpeedResumePrevious.setReportCount(deviceSpeedResumePrevious.getReportCount() + 1);
        deviceSpeedResumePrevious.setMaxSpeed((
                Math.max(deviceSpeedResumePrevious.getMaxSpeed(), deviceSpeedResume.getMaxSpeed())));
        deviceSpeedResumePrevious.setMinSpeed(
                (Math.min(deviceSpeedResumePrevious.getMinSpeed(), deviceSpeedResume.getMinSpeed())));
        deviceSpeedResumePrevious.calculateSpeedAvg();
        deviceSpeedResumePrevious.calculateRelanti(
                this.calculateTimeBetweenSpeedResume(deviceSpeedResume,
                        deviceSpeedResumePrevious));
        /*update*/
        saveDeviceSpeedResumeRepository(deviceSpeedResumePrevious);
    }

    @Transactional
    void updateCloseDeviceSpeedResume(DeviceSpeedResume deviceSpeedResume, String address,
                                      DeviceSpeedResume deviceSpeedResumePrevious,
                                      Long reportId) {
        //if the state is another, close the previous and save a new.
        deviceSpeedResumePrevious.setReportCloseId(reportId);
        deviceSpeedResumePrevious.setFinalAddress(address);
        deviceSpeedResumePrevious.setReportCount(deviceSpeedResumePrevious.getReportCount() + 1);
        deviceSpeedResumePrevious.setDateTo(deviceSpeedResume.getDateFrom());
        deviceSpeedResumePrevious.calculateDuration();
        deviceSpeedResumePrevious.setLastUpdate(deviceSpeedResume.getLastUpdate());
        deviceSpeedResumePrevious.calculateSpeedAvg();
        deviceSpeedResumePrevious.calculateRelanti(
                this.calculateTimeBetweenSpeedResume(deviceSpeedResume,
                        deviceSpeedResumePrevious));
        /*Update - close the previous*/
        saveDeviceSpeedResumeRepository(deviceSpeedResumePrevious);
    }

    @Transactional
    void createDeviceSpeedResume(DeviceSpeedResume deviceSpeedResume, String address,
                                 Long reportId) {
        /*Create*/
        deviceSpeedResume.setReportCreateId(reportId);
        deviceSpeedResume.setInitialAddress(address);
        deviceSpeedResume.setFinalAddress(address);
        deviceSpeedResume.setReportCount(1);
        saveDeviceSpeedResumeRepository(deviceSpeedResume);
    }

    private void saveDeviceSpeedResumeRepository(DeviceSpeedResume deviceSpeedResume) {
        this.deviceSpeedResumeRepository.save(deviceSpeedResume);
//        this.sendToElasticProcessor(deviceSpeedResume);
    }


    private double calculateTimeBetweenSpeedResume(DeviceSpeedResume deviceSpeedResumePrevious, DeviceSpeedResume deviceSpeedResume) {
        double timeBetweenReports = 0.0;
        long durationSeconds = TimeUnit.MILLISECONDS.toSeconds(
                deviceSpeedResume.getDateFrom().getTime() -
                        deviceSpeedResumePrevious.getDateFrom().getTime());
        durationSeconds = durationSeconds > 0 ? durationSeconds : 0;
        timeBetweenReports = (double) durationSeconds / 60;
        return timeBetweenReports;
    }

    public void sendToElasticProcessor(DeviceSpeedResume deviceSpeedResume) {
        try {
            DeviceSpeedResumeDTO deviceSpeedResumeDTO = this.deviceSpeedResumeMapper.toDto(deviceSpeedResume);
            if (deviceSpeedResumeDTO != null) {
                //String routingKey = "processor.alert." + (dto.getAccount() != null ? dto.getAccount().getId() : 0);
                String routingKey = "elastic.devicetimelines";

                LOG.info("Sending Elastic devicetimelines to Broker  QUEUE: " + routingKey);
                ObjectMapper objectMapper = new ObjectMapper();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                objectMapper.setDateFormat(df);
                String deviceSpeedAsString = objectMapper.writeValueAsString(deviceSpeedResumeDTO);
                rabbitTemplate.convertAndSend(routingKey, deviceSpeedAsString);
                LOG.info("Sent report with routing key " + routingKey + ".\nDeviceSpeedResume: " + deviceSpeedResume.getId());
            }
        } catch (Exception e) {
            LOG.error("Error sending to  devicetimelines: " + e.getMessage());
        }
    }
}
