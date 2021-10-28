package fit.iterway.processor.dtos;

import fit.iterway.processor.utils.DateHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DeviceSpeedResumeDTO implements Cloneable {

    private long id;

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

    private Date lastUpdate;

    private boolean lastReport = false;

    private String timeZone = "UTC";

    private String initialAddress;

    private String finalAddress;

    private Integer reportCount = 0;

    private Long deviceId;

    public static DeviceSpeedResumeDTO configurationLast(DeviceSpeedResumeDTO deviceSpeedResumeDTO) {
        DeviceSpeedResumeDTO clone = null;
        Date today = DateHelper.nowInUTC0();
        try {
            clone = (DeviceSpeedResumeDTO) deviceSpeedResumeDTO.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        clone.setState("UNDEFINED");
        clone.setDateFrom(deviceSpeedResumeDTO.getDateTo());
        clone.setDateTo(today);
        clone.setInitialAddress(deviceSpeedResumeDTO.getFinalAddress());
        clone.setLastUpdate(today);
        clone.calculateDuration();
        clone.setLastReport(true);
        return clone;
    }

    private void calculateDuration() {
        if (this.getDateTo() != null && this.getDateFrom() != null) {
            long diffInMillies = Math.abs(getDateTo().getTime() - getDateFrom().getTime());
            this.setDuration((int) diffInMillies);
        }
    }

    public DeviceSpeedResumeDTO(DeviceSpeedResumeDTO other) {
        this.setId(other.getId());
        this.state = other.state;
        this.speed = other.speed;
        this.color = other.color;
        this.dateFrom = other.dateFrom != null ? new Date(other.dateFrom.getTime()) : null;
        this.dateTo = other.dateTo != null ? new Date(other.dateTo.getTime()) : null;
        this.dateOrigin = other.dateOrigin != null ? new Date(other.dateOrigin.getTime()) : null;
        this.duration = other.duration;
        this.ignition = other.ignition;
        this.minSpeed = other.minSpeed;
        this.maxSpeed = other.maxSpeed;
        this.movement = other.movement;
        this.lastUpdate = other.lastUpdate != null ? new Date(other.lastUpdate.getTime()) : null;
        this.lastReport = other.lastReport;
        this.timeZone = other.timeZone;
        this.initialAddress = other.initialAddress;
        this.finalAddress = other.finalAddress;
        this.reportCount = other.reportCount;
    }

    public DeviceSpeedResumeDTO(String state, Date dateFrom, Date dateTo) {
        this.state = state;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.calculateDuration();
        this.color = "gray";
        this.dateOrigin = dateFrom;
        this.lastUpdate = DateHelper.nowInUTC0();
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
        this.calculateDuration();
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
        this.calculateDuration();
    }

}
