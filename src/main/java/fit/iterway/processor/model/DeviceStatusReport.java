package fit.iterway.processor.model;

import fit.iterway.processor.dtos.DeviceReportDTO;
import fit.iterway.processor.dtos.obd.Obd2Dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Getter
@Setter
public class DeviceStatusReport {

    @Id
    private Long id;
    private String engineHour;
    private Float powerSupplyVoltage;
    private Float batteryVoltage;

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

    private Boolean output1;
    private Boolean output2;
    private Boolean output3;

    private Boolean input1;
    private Boolean input2;
    private Boolean input3;


    //FROM OBD2
    private Integer distanceSinceErrorCodesCleared;
    private Integer distanceWithMalfunction;
    private Double engineCoolantTemperature;
    private Double engineLoad;
    private Double engineSpeed;
    private Double fuelInjectionTiming;
    private Double fuelTankLevelInput;
    private Double fuelRate;
    private String fuelType;
    private Double fuelTrimPercentShortTerm;
    private Double fuelTrimPercentLongTerm;
    private Double fuelTrimPercentShortBank1;
    private Double fuelTrimPercentLongBank1;
    private Integer intakeAirTemperature;
    private Integer runTimeSinceEngineStart;
    private String secondaryAirStatus;
    private Double catalystTemperatureSensor1;
    private Double catalystTemperatureSensor2;
    private Integer ambientAirTemperature;
    private Integer engineOilTemperature;
    private Integer intakeAirTemperatureSensor;
    private Integer intakeAbsolutePressure;
    private Integer exhaustGasRecirculationTemperature;
    private Integer manifoldSurfaceTemperature;
    private Double commandedEgr;
    private Double egrError;
    private Double ethanolFuel;
    private Integer throttlePosition;
    private Integer vehicleSpeed;
    private String VIN;

    private DeviceReport deviceReport;


    public DeviceStatusReport() {
    }


    @Transient
    public void setDttData(DeviceReportDTO deviceReportDTO) {
        this.setMoving(deviceReportDTO.getDeviceStateDTO().getMoving());
        this.setHarshBehavior(deviceReportDTO.getDeviceStateDTO().getHarshBehavior());
        this.setTireAlert(deviceReportDTO.getDeviceStateDTO().getTireAlert());
        this.setServiceMillageAlert(deviceReportDTO.getDeviceStateDTO().getServiceMillageAlert());
        this.setStopParking(deviceReportDTO.getDeviceStateDTO().getStopParking());
        this.setEngineOn(deviceReportDTO.getDeviceStateDTO().getEngineOn());
        this.setAccidented(deviceReportDTO.getDeviceStateDTO().getAccidented());
        this.setEngineOverTemp(deviceReportDTO.getDeviceStateDTO().getEngineOverTemp());
        this.setBatteryVoltage(deviceReportDTO.getDeviceStateDTO().getBatteryVoltage());
        this.setFarawayImmobilizer(deviceReportDTO.getDeviceStateDTO().getFarawayImmobilizer());

        this.setOutput1(deviceReportDTO.getDeviceStateDTO().getOutput1());
        this.setOutput2(deviceReportDTO.getDeviceStateDTO().getOutput2());
        this.setOutput3(deviceReportDTO.getDeviceStateDTO().getOutput3());
        this.setInput1(deviceReportDTO.getDeviceStateDTO().getInput1());
        this.setInput2(deviceReportDTO.getDeviceStateDTO().getInput2());
        this.setOutput3(deviceReportDTO.getDeviceStateDTO().getInput3());

    }
    @Transient
    public void setObd2Data(Obd2Dto obd2Dto) {

        if (obd2Dto != null) {
            this.setDistanceSinceErrorCodesCleared(obd2Dto.getDistanceSinceErrorCodesCleared());
            this.setDistanceWithMalfunction(obd2Dto.getDistanceWithMalfunction());
            this.setEngineCoolantTemperature(obd2Dto.getEngineCoolantTemperature());
            this.setEngineLoad(obd2Dto.getEngineLoad());
            this.setEngineSpeed(obd2Dto.getEngineSpeed());
            this.setFuelInjectionTiming(obd2Dto.getFuelInjectionTiming());
            this.setFuelTankLevelInput(obd2Dto.getFuelTankLevelInput());
            this.setFuelRate(obd2Dto.getFuelRate());
//        this.setFuelStatus(obd2Dto.getFuelStatus());

            this.setFuelType(obd2Dto.getFuelType() != null ? obd2Dto.getFuelType().name() : null);
            this.setFuelTrimPercentShortTerm(obd2Dto.getFuelTrimPercentShortTerm());
            this.setFuelTrimPercentLongTerm(obd2Dto.getFuelTrimPercentLongTerm());
            this.setFuelTrimPercentShortBank1(obd2Dto.getFuelTrimPercentShortBank1());
            this.setFuelTrimPercentLongBank1(obd2Dto.getFuelTrimPercentLongBank1());
            this.setIntakeAirTemperature(obd2Dto.getIntakeAirTemperature());
            this.setRunTimeSinceEngineStart(obd2Dto.getRunTimeSinceEngineStart());
            this.setSecondaryAirStatus(obd2Dto.getSecondaryAirStatus() != null ? obd2Dto.getSecondaryAirStatus().name() : null);
            this.setCatalystTemperatureSensor1(obd2Dto.getCatalystTemperatureSensor1());
            this.setCatalystTemperatureSensor2(obd2Dto.getCatalystTemperatureSensor2());
            this.setAmbientAirTemperature(obd2Dto.getAmbientAirTemperature());
            this.setEngineOilTemperature(obd2Dto.getEngineOilTemperature());
            this.setIntakeAirTemperatureSensor(obd2Dto.getIntakeAirTemperatureSensor());
            this.setIntakeAbsolutePressure(obd2Dto.getIntakeAbsolutePressure());
            this.setExhaustGasRecirculationTemperature(obd2Dto.getExhaustGasRecirculationTemperature());
            this.setManifoldSurfaceTemperature(obd2Dto.getManifoldSurfaceTemperature());
            this.setCommandedEgr(obd2Dto.getCommandedEgr());
            this.setEgrError(obd2Dto.getEgrError());
            this.setEthanolFuel(obd2Dto.getEthanolFuel());
            this.setThrottlePosition(obd2Dto.getThrottlePosition());
            this.setVehicleSpeed(obd2Dto.getVehicleSpeed());
            this.setVIN(obd2Dto.getVIN());
        }

    }
}
