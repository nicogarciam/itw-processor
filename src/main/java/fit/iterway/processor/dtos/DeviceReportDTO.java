package fit.iterway.processor.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fit.iterway.processor.dtos.obd.Obd2Dto;
import lombok.Data;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Data
public class DeviceReportDTO  {

    private long id;

    private String app;
    private Date dateReceived;

    private String eventType = "";
    private String reportType = "";

    private String timestamp; //timestamp en utc 0 que manda beeper

    private Long deviceId;
    private Long accountId;
    private Date date;
    private Double longitude;
    private Double latitude;
    private String eventId;
    private String eventAttribute;

    private String protocol;
    private String deviceName;
    private String deviceTypeName;
    private String deviceFirmware;
    private String deviceHardware;
    private String deviceCode;

    private String dateString;
    private String durationHHMM;

    private char gpsFixed;
    private Integer gpsValidSat;
    private Integer gpsSpeed;
    private Float gpsAzimuth;
    private Integer gpsAltitud;
    private Float gpsPresision;
    private Float gpsHDOP;
    private Float gpsVDOP;

    private double odometer;

    private String protocolVersion;

    private String obd;
    private Obd2Dto obd2Dto;

    private AdcDto adc;
    private CotDto cot;
    private DttDto dtt;
    private EtdDto etd;
    private FulDto ful;
    private IwdDto iwd;

    private LocationDTO locationDTO;

    private DeviceStateDTO deviceStateDTO;

    /*Report info*/
    private String latLong;

    private Long countLatLong;

    private String timeZone = "UTC";

    private Date dateEnd;

    private String logGps;





}
