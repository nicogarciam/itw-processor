package fit.iterway.processor.dtos.obd;

import fit.iterway.processor.utils.DateHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Obd2Dto {
    private Date timestamp = DateHelper.nowInUTC0();
    private boolean[] supportedPIDS;
    private MonitorStatus monitorStatus;
    private Integer distanceSinceErrorCodesCleared;
    private Integer distanceWithMalfunction;
    private Double engineCoolantTemperature;
    private Double engineLoad;
    private Double engineSpeed;
    private Double engineFuelRate;
    private Integer engineRunTime;
    private Double fuelInjectionTiming;
    private Double fuelTankLevelInput;
    private Double fuelRate;
    private FuelStatus fuelStatus;
    private FuelType fuelType;
    private Double fuelTrimPercentShortTerm;
    private Double fuelTrimPercentLongTerm;
    private Double fuelTrimPercentShortBank1;
    private Double fuelTrimPercentLongBank1;
    private Integer intakeAirTemperature;
    private Integer runTimeSinceEngineStart;
    private AirStatus secondaryAirStatus;
    private EcuCompatibility ecuCompatibility;
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
    private Double TimeRunWithMilOn;
    private Double timeSinceTroubleCodeCleared;
    private Integer fuelPressure;

}
