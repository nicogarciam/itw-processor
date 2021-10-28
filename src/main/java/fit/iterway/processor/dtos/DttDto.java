package fit.iterway.processor.dtos;

import lombok.Data;

@Data
public class DttDto {
    // :<Device status>;<I/O status>;<Number 0 to 119 Geo-fence status>;<Number 120 to 155 Geo-fence status>;
    // <Event status>;<Packet type indicator>

    private String deviceStatus;
    private String ioStatus;
    private String geoFencesStatus;
    private String eventStatus;
    private String packetType;

}
