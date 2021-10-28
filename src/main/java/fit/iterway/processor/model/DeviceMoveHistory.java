package fit.iterway.processor.model;

import fit.iterway.processor.dtos.DeviceReportDTO;
import fit.iterway.processor.utils.NumberHelper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Getter
@Setter
public class DeviceMoveHistory  {

    @Id
    private Long id;


    private Date created;

    private Date reportDate;

    private String country;

    private String state;

    private String city;

    private String road;

    private Long deviceId;

    private Long vehicleId;

    private Long personId;

    private Double moveTime = 0.0;

    private Double stopTime = 0.0;

    private Double reportTime = 0.0;

    private Double distanceLatLong = 0.0;

    private Double distanceOdometer = 0.0;

    private Long copiedId;

    private Integer cantReports = 0;

    private Date dateIni;

    private Double speedMax = 0.0;

    private Double speedAvg = 0.0;

    private boolean closed = false;

    public DeviceMoveHistory() {

    }

    public DeviceMoveHistory(DeviceReportDTO report) {
        super();
        this.setReportDate(report.getDate());
        this.setDeviceId(report.getDeviceId());

    }

    public DeviceMoveHistory(Long id, Date created, Date reportDate, String country, String state, String city,
                             String road, Long deviceId, Long vehicleId, Long personId, Double moveTime, Double stopTime,
                             Double reportTime, Double distanceLatLong, Double distanceOdometer, Long copiedId, Integer cantReports,
                             Date dateIni) {
        this.id = id;
        this.created = created;
        this.reportDate = reportDate;
        this.country = country;
        this.state = state;
        this.city = city;
        this.road = road;
        this.deviceId = deviceId;
        this.vehicleId = vehicleId;
        this.personId = personId;
        this.moveTime = moveTime;
        this.stopTime = stopTime;
        this.reportTime = reportTime;
        this.distanceLatLong = distanceLatLong;
        this.distanceOdometer = distanceOdometer;
        this.copiedId = copiedId;
        this.cantReports = cantReports;
        this.dateIni = dateIni;
    }

    public DeviceMoveHistory(Long deviceId, Date dateIni, Date reportDate, Double reportTime,
                             Double moveTime, Double stopTime, Long cantReports,
                             Double speedMax, Double speedAvg,
                             Double distanceLatLong, Double distanceOdometer
    ) {
        this.deviceId = deviceId;
        this.dateIni = dateIni;
        this.reportDate = reportDate;
        this.reportTime = reportTime;
        this.moveTime = moveTime;
        this.stopTime = stopTime;
        this.speedMax = speedMax;
        this.speedAvg = speedAvg != null ? NumberHelper.formatearDecimales(speedAvg,2) : 0.0;
        this.distanceLatLong = distanceLatLong;
        this.distanceOdometer = distanceOdometer;
        this.cantReports = cantReports.intValue();
    }

    @Transient
    public long hoursElapsed(Date newReportDate) {
        return TimeUnit.MILLISECONDS.toHours(this.getReportDate().getTime() - newReportDate.getTime());
    }

    @Transient
    public long minutesElapsed(Date newReportDate) {
        return TimeUnit.MILLISECONDS.toMinutes(newReportDate.getTime() - this.getReportDate().getTime() );
    }

}
