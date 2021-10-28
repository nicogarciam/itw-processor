package fit.iterway.processor.dtos;

import lombok.Data;

@Data
public class AdcDto {
    // < External power supply voltage>;< Backup battery voltage>;<ADC1 input voltage>;<ADC2 input voltage>
    private Float powerSupplyVoltage;
    private Float batteryVoltage;
    private Float adc1InVoltage;
    private Float adc2InVoltage;


}
