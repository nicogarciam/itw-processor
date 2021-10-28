package fit.iterway.processor.model;

import fit.iterway.processor.dtos.DeviceReportDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Getter
@Setter
public class DeviceReportView {

    @Id
    private Long id;

    private String app;
    private Date dateReceived; //now en el api
    private String reportType;

    private String eventType;

    private long deviceID;

    private DeviceStatusReport statusReport;

    private Date date;
    private Double longitude;
    private Double latitude;

    private String deviceCode;
    private String eventId;

    private char gpsFixed;
    private Integer gpsValidSat;
    private Integer gpsSpeed;
    private Float gpsAzimuth;
    private Integer gpsAltitud;
    private Float gpsPresision;


    private double odometer;
    private String engineHour;
    private Float powerSupplyVoltage;
    private Float batteryVoltage;
    private Integer signalStrength;

    /*lastAddress*/
    private String houseNumber;
    private String road;
    private String city;
    private String state;

    private String country;

    private String countryCode;
    private String postCode;
    /*End lastAddress*/
    private String latLong; //latitude longitud, recortado a 4 decimales

    /*    @Column(name = "display_name")
        private String displayName;*/
    private int data1Id;
    private String data1Type;
    private String data1Field;
    private int data2Id;
    private String data2Type;
    private String data2Field;

    private Integer gpsSats;

    public DeviceReportView() {
    }

//    @Transient
//    public String getEvent() {
//        return this.deviceEvent != null ? this.deviceEvent.getEventName() : "";
//    }

    @Transient
    public DeviceReportView setReportData(DeviceReportDTO deviceReportDTO) {
        this.deviceID = deviceReportDTO.getDeviceId();
        this.dateReceived = deviceReportDTO.getDateReceived();
        this.deviceCode = deviceReportDTO.getDeviceCode();

        this.reportType = deviceReportDTO.getReportType();
        this.app = deviceReportDTO.getApp();
        this.eventId = deviceReportDTO.getEventId();


        if (deviceReportDTO.getDate() != null)
            this.date = deviceReportDTO.getDate();


        this.longitude = deviceReportDTO.getLongitude();
        this.latitude = deviceReportDTO.getLatitude();
        this.latLong = deviceReportDTO.getLatLong();


        this.gpsAltitud = deviceReportDTO.getGpsAltitud();

        this.road = deviceReportDTO.getLocationDTO().getAddress().getRoad();
        this.city = deviceReportDTO.getLocationDTO().getAddress().getCity();
        this.state = deviceReportDTO.getLocationDTO().getAddress().getState();
        this.country = deviceReportDTO.getLocationDTO().getAddress().getCountry();
        this.countryCode = deviceReportDTO.getLocationDTO().getAddress().getCountry_code();
        this.postCode = deviceReportDTO.getLocationDTO().getAddress().getPostcode();
        this.houseNumber = deviceReportDTO.getLocationDTO().getAddress().getHouse_number();

        this.gpsAzimuth = deviceReportDTO.getGpsAzimuth();

        if (deviceReportDTO.getCot() != null) {
            this.odometer = deviceReportDTO.getCot().getOdometer();
        }

        if (deviceReportDTO.getCot() != null) {
            this.engineHour = deviceReportDTO.getCot().getEngineHour();
        }

        this.gpsSpeed = deviceReportDTO.getGpsSpeed();

        this.gpsSats = deviceReportDTO.getGpsValidSat();
        this.gpsFixed = deviceReportDTO.getGpsFixed();
        this.gpsPresision = deviceReportDTO.getGpsPresision();


        if (deviceReportDTO.getAdc() != null) {
            this.powerSupplyVoltage = deviceReportDTO.getAdc().getPowerSupplyVoltage();
        }

        if (deviceReportDTO.getAdc() != null) {
            this.batteryVoltage = deviceReportDTO.getAdc().getBatteryVoltage();
        }


        if (deviceReportDTO.getFul() != null) {
            this.data1Field = deviceReportDTO.getFul().getData1Field();
            this.data1Id = deviceReportDTO.getFul().getData1Id();
            this.data1Type = deviceReportDTO.getFul().getData1Type();
            this.data2Field = deviceReportDTO.getFul().getData2Field();
            this.data2Id = deviceReportDTO.getFul().getData2Id();
            this.data2Type = deviceReportDTO.getFul().getData2Type();
        }

        return this;
    }
}
