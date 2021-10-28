package fit.iterway.processor.dtos;

import lombok.Data;

@Data
public class LocationDTO {

    private String licence;

    private String osm_type;

    private String lat;

    private String lon;

    private String display_name;

    private AddressDTO address;

    private String status;

    private boolean toCheck = false;

    private String urlRequest;

    private boolean moreTranOne = false;


}
