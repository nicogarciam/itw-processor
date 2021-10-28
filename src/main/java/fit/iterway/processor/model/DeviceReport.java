package fit.iterway.processor.model;

import fit.iterway.processor.dtos.DeviceReportDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
public class DeviceReport  {

    @Id
    private Long id;

    private String app;

    private Date dateReceived; //now en el api
    private String reportType;

    private String eventType;
    private long deviceId;

    private DeviceStatusReport deviceStatusReport;

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

    private String obd;

    private String dtt;



    @Transient
    public DeviceReport setReportData(DeviceReportDTO deviceReportDTO) {
        this.deviceId = deviceReportDTO.getDeviceId();
        this.dateReceived = deviceReportDTO.getDateReceived();
        this.deviceCode = deviceReportDTO.getDeviceCode();

        this.reportType = deviceReportDTO.getReportType();
        this.app = deviceReportDTO.getApp();

        this.eventId = deviceReportDTO.getEventId();
        this.eventType = deviceReportDTO.getEventType();
        this.obd = deviceReportDTO.getObd();
        this.dtt = deviceReportDTO.getDtt() != null ? deviceReportDTO.getDtt().getDeviceStatus() : "";


        if (deviceReportDTO.getDate() != null)
            this.date = deviceReportDTO.getDate();

        this.longitude = deviceReportDTO.getLongitude();
        this.latitude = deviceReportDTO.getLatitude();
        this.latLong = deviceReportDTO.getLatLong();

        this.gpsAltitud = deviceReportDTO.getGpsAltitud();

        if (deviceReportDTO.getLocationDTO() != null && deviceReportDTO.getLocationDTO().getAddress() != null) {
            this.road = deviceReportDTO.getLocationDTO().getAddress().getRoad();
            this.city = deviceReportDTO.getLocationDTO().getAddress().getCity();
            this.state = deviceReportDTO.getLocationDTO().getAddress().getState();
            this.country = deviceReportDTO.getLocationDTO().getAddress().getCountry();
            this.countryCode = deviceReportDTO.getLocationDTO().getAddress().getCountry_code();
            this.postCode = deviceReportDTO.getLocationDTO().getAddress().getPostcode();
            this.houseNumber = deviceReportDTO.getLocationDTO().getAddress().getHouse_number();
        }

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
        if (deviceReportDTO.getGpsValidSat() != null) {
            this.gpsValidSat = deviceReportDTO.getGpsValidSat();
        }


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


        if (deviceReportDTO.getObd2Dto() != null || deviceReportDTO.getDtt() != null) {
            DeviceStatusReport deviceStatusReport = new DeviceStatusReport();
            this.setDeviceStatusReport(deviceStatusReport);
            deviceStatusReport.setDttData(deviceReportDTO);
            deviceStatusReport.setObd2Data(deviceReportDTO.getObd2Dto());
        }

        return this;
    }



    @Transient
    public Address getAddres() {
        String address = this.getRoad() + " " + this.getHouseNumber() + ", " +
                this.getCity() + ", " + this.getState() + ", " + this.getCountry();
        Address lastAddress = new Address(address, this.getHouseNumber(), this.getRoad(),
                this.getCity(), this.getState(), this.getCountry(),
                this.getCountryCode(), this.getPostCode(), this.getLatitude(),
                this.getLongitude());

        return lastAddress;
    }
}
