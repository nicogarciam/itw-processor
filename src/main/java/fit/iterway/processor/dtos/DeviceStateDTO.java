package fit.iterway.processor.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fit.iterway.processor.utils.DateHelper;
import fit.iterway.processor.utils.GpsUtils;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceStateDTO {

    private Integer lastSpeed;

    private Float lastAzimut;

    private Integer lastAltitude;

    private Integer gpsValidSat;

    private Float lastPresision;

    private String houseNumber;

    private String road;

    private String city;

    private String state;

    private String country;

    private String countryCode;

    private String postCode;

    private Date lastUpdateTime;

    private Double odometer;

    private String engineHour;

    private Float powerSupplyVoltage;

    private Float batteryVoltage;

    private Integer signalStrength;

    private String socketAddress;

    private Double longitude;

    private Double latitude;

    private String timeZone = "UTC";

    private Boolean ignition;

    //From DTT
    private Boolean moving;
    private Boolean harshBehavior;
    private Boolean accidented;
    private Boolean engineOn;
    private Boolean stopParking;
    private Boolean farawayImmobilizer;
    private Boolean engineOverTemp;
    private Boolean serviceMillageAlert;
    private Boolean engineOilAlert;
    private Boolean tireAlert;

    private Boolean adcInput1;
    private Boolean adcInput2;
    private Boolean privateHour;


    // I/O Status 4603
    private Boolean output1;
    private Boolean output2;
    private Boolean output3;

    private Boolean input1;
    private Boolean input2;
    private Boolean input3;


    @JsonIgnore
    public Date getLastUpdateTimeByTimeZone() {
        return DateHelper.getDateByZone(lastUpdateTime, timeZone);
    }

    @JsonIgnore
    public String getLastUpdateTimeString() {
        if (lastUpdateTime != null) {
            org.joda.time.LocalDateTime dateTime = DateHelper.convertToLocalDateTime(getLastUpdateTimeByTimeZone());
            return dateTime.toString(DateTimeFormat.forPattern("MM/dd/yyyy HH:mm"));
        } else return "";
    }

    @JsonIgnore
    public String getDirection() {
        return GpsUtils.getDirecctionFromAzimuth(lastAzimut);
    }

    @JsonIgnore
    public String getAddressList() {
        return (((city == null || city.equals("")) ? "" : (city)) +
                ((state == null || state.equals("")) ? "" : (
                        ((city == null || city.equals("")) ? "" : ", ") + state)) +
                ((postCode == null || postCode.equals("")) ? "" : (
                        ((state == null || state.equals("")) ? "" : ", ") + postCode)));
    }

    @JsonIgnore
    public String getAddressDetail() {
        return (((houseNumber == null || houseNumber.equals("")) ? "" : (houseNumber)) +
                ((road == null || road.equals("")) ? "" : (
                        ((houseNumber == null || houseNumber.equals("")) ? "" : ", ") + road)) +
                (((houseNumber == null || houseNumber.equals("")) &&
                        (road == null || road.equals(""))) ? "" : " - ") +
                this.getAddressList());
    }

}
