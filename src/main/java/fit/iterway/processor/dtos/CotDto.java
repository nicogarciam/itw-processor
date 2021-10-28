package fit.iterway.processor.dtos;

import lombok.Data;

@Data
public class CotDto {
    // <Odometer>;<Engine hour>;<IN1Frequency/Pulse data>;<IN2Frequency/Pulse data>;
    // <IN3 Frequency/Pulse data>;<IN4 Frequency/Pulse data>

    private double odometer;
//    Time format:<Hour>:<Minute>:<Second>
    private String engineHour;
    //<Digital input><P/F><Value>
    private String in1Data;
    private String in2Data;
    private String in3Data;
    private String in4Data;

}
