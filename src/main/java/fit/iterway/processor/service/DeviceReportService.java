package fit.iterway.processor.service;

import fit.iterway.processor.dtos.DeviceReportDTO;
import fit.iterway.processor.dtos.DeviceViewDTO;

public interface DeviceReportService {

    void generateMoveHistory(DeviceReportDTO report);

    void saveSpeedResume(DeviceReportDTO report, DeviceViewDTO deviceViewDTO);

}
