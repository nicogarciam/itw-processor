package fit.iterway.processor.dtos;

import lombok.Data;

@Data
public class IwdDto {
    // <Data ID>;<Data type>;<Data field>;<Data ID>;<Data type>;<Data field>

    private int data1Id;
    private int data1Type;
    private String data1Field;
    private int data2Id;
    private int data2Type;
    private String data2Field;


}
