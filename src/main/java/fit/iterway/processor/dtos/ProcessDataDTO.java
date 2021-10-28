package fit.iterway.processor.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessDataDTO  {
    DeviceReportDTO deviceReportDTO;
    DeviceViewDTO deviceViewDTO;
    DeviceReportDTO lastReport;


    public ProcessDataDTO() {
    }

    public ProcessDataDTO(DeviceReportDTO deviceReportDTO, DeviceViewDTO deviceViewDTO,
                          DeviceReportDTO lastReport) {
        this.deviceReportDTO = deviceReportDTO;
        this.deviceViewDTO = deviceViewDTO;
        this.lastReport = lastReport;
    }
}
