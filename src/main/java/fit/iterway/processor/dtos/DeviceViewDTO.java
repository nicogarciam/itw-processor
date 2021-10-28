package fit.iterway.processor.dtos;


import fit.iterway.processor.dtos.obd.Obd2Dto;
import lombok.Data;

import java.util.Date;

public @Data class DeviceViewDTO {

    private Long deviceId;

    private Date reportDate;

    private String routingKeyAccountsList;

    private String deviceCode;

    private String deviceName;

    private String deviceTypeName;

    private String eventId;

    private String eventAttribute;

    private String eventType;

    private Long accountId;

    private Long deviceReportId;

    private DeviceStateDTO deviceState = new DeviceStateDTO();

    private Obd2Dto obd2Dto = new Obd2Dto();

    private DeviceAlertStateDTO deviceAlertState = new DeviceAlertStateDTO();

}
