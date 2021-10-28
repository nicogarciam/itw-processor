package fit.iterway.processor.dtos;

import lombok.Data;

@Data
public class FulDto {
    // <Data ID>;<Data type>;<Data field>;<Data ID>;<Data type>;<Data field>

    private int data1Id;
    private String data1Type;
    private String data1Field;
    private int data2Id;
    private String data2Type;
    private String data2Field;

}
