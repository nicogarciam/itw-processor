package fit.iterway.processor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fit.iterway.processor.dtos.DeviceReportDTO;
import fit.iterway.processor.utils.DateHelper;
import fit.iterway.processor.utils.NumberHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Getter
@Setter
public class DeviceSpeedResume {

    @Id
    private Long id;

    private Long deviceId;

    private String state;

    private Double speed;

    private String color;

    private Date dateFrom;

    private Date dateTo;

    private Date dateOrigin;

    private int duration;

    private int ignition;

    private int minSpeed;

    private int maxSpeed;

    private int movement;

    private Double ralentiTime = 0.0;

    private Integer countRalenti = 0;

    private Date lastUpdate;

    private String initialAddress;

    private String finalAddress;

    private Integer reportCount = 0;

    private Double speedAvg = 0.0;

    private Long reportCreateId;

    private Long reportCloseId;

    public DeviceSpeedResume() {
    }

    public void setProperties(DeviceReportDTO deviceReport) {
        this.setReportCreateId(deviceReport.getId());
        this.setDeviceId(deviceReport.getDeviceId());
        this.setSpeed(Double.valueOf(deviceReport.getGpsSpeed()));
        this.setDateTo(deviceReport.getDate());
        this.setDateFrom(deviceReport.getDate());
        this.setDateOrigin(deviceReport.getDate());
        this.setDuration(0);
        this.setIgnition(1);
        this.setMinSpeed(deviceReport.getGpsSpeed());
        this.setMaxSpeed(deviceReport.getGpsSpeed());
        this.setColor(this.calculateColor());
        this.setMovement(this.calculateMovement() ? 1 : 0);
        this.setLastUpdate(deviceReport.getDate());
    }

    public void setUnknownProperties(Date dateFrom, Date dateTo, Long deviceId) {
        this.setDeviceId(deviceId);
        this.setDateTo(dateTo);
        this.setDateFrom(dateFrom);
        this.setDateOrigin(DateHelper.nowInUTC0());
        this.setDuration(0);
        this.setState("UNKNOWN");
        this.setColor("gray");
        this.setLastUpdate(this.getDateOrigin());
        this.calculateDuration();
        this.calculateSpeedAvg();
    }


    @Transient
    @JsonIgnore
    public String calculateColor() {
        this.calculateState();
        if ("STOP".equals(getState()))
            return "Gainsboro";
        else if ("VERY_SLOW".equals(getState()))
            return "green";
        else if ("SLOW".equals(getState()))
            return "lightgreen";
        else if ("MODERATE".equals(getState()))
            return "yellow";
        else if ("VERY_FAST".equals(getState()))
            return "orange";
        else if ("FAST".equals(getState()))
            return "red";
        else
            return "gray";

    }

    @Transient
    @JsonIgnore
    public void calculateState() {
        if (this.getSpeed() == null)
            this.setState("UNKNOWN");
        else if (this.getSpeed() >= 0 && this.getSpeed() <= 2)
            this.setState("STOP");
        else if (this.getSpeed() >= 2 && this.getSpeed() <= 30)
            this.setState("VERY_SLOW");
        else if (this.getSpeed() >= 30 && this.getSpeed() <= 60)
            this.setState("SLOW");
        else if (this.getSpeed() >= 60 && this.getSpeed() <= 90)
            this.setState("MODERATE");
        else if (this.getSpeed() >= 90 && this.getSpeed() <= 110)
            this.setState("FAST");
        else if (this.getSpeed() > 100)
            this.setState("VERY_FAST");
    }

    @Transient
    @JsonIgnore
    public void calculateRelanti(double timeBetweenReports) {
        if (this.getState().equals("STOP") && this.getIgnition() == 1) {
            this.setCountRalenti(this.getCountRalenti() + 1);
            this.setRalentiTime(this.getCountRalenti() + timeBetweenReports);
        }
    }

    @Transient
    @JsonIgnore
    public void calculateSpeedAvg() {
        Double avg = (double) (this.getMaxSpeed() - this.getMinSpeed()) / 2;
        this.setSpeedAvg(NumberHelper.formatearDecimales(avg, 2));
    }

    @Transient
    @JsonIgnore
    public void calculateDuration() {
        if (this.getDateTo() != null && this.getDateFrom() != null) {
            long diffInMillies = Math.abs(getDateTo().getTime() - getDateFrom().getTime());
            this.setDuration(diffInMillies > 0 ? (int) diffInMillies : 0);
        }
    }

    @Transient
    @JsonIgnore
    public boolean calculateMovement() {
        return this.getSpeed() == null ? false : (this.getSpeed() <= 5.5 ? false : true);
    }


    public void setFinalAddress(String address) {
        this.finalAddress = address;
        if (this.initialAddress == null || this.initialAddress.equals("")) {
            this.initialAddress = address;
        }
    }

}
