package fit.iterway.processor.dtos;

import lombok.Data;

@Data
public class AddressDTO {

    private String house_number;
    private String road;

    private String suburb;

    private String city;

    private String town;

    private String state_district;

    private String state;

    private String postcode;

    private String country;

    private String country_code;

    private String fullAddress;


}
