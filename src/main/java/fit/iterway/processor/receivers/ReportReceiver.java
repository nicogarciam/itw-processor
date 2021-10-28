package fit.iterway.processor.receivers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fit.iterway.processor.dtos.DeviceReportDTO;
import fit.iterway.processor.dtos.DeviceViewDTO;
import fit.iterway.processor.dtos.ProcessDataDTO;
import fit.iterway.processor.service.DeviceReportService;
import org.springframework.stereotype.Component;


@Component
public class ReportReceiver {

    DeviceReportService deviceReportService;

    public ReportReceiver(DeviceReportService deviceReportService) {
        this.deviceReportService = deviceReportService;
    }

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        try {
//            DeviceViewDTO deviceViewDTO = (new ObjectMapper()).readValue(message, DeviceViewDTO.class);
            ProcessDataDTO processDataDTO = (new ObjectMapper()).readValue(message, ProcessDataDTO.class);
            deviceReportService.saveSpeedResume(processDataDTO.getDeviceReportDTO(), processDataDTO.getDeviceViewDTO());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
