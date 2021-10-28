package fit.iterway.processor.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Address implements Cloneable {

    @Id
    private Long id;

    private String fullAddressComplement;

    private String fullAddress;

    private String houseNumber;

    private String road;

    private String city;

    private String state;
    private String country;
    private String countryCode;

    private String postCode;

    private Double latitude;

    private Double longitude;


    public Address(String fullAddressComplement, String fullAddress, String houseNumber, String road, String city,
                   String state, String country, String countryCode, String postCode, Double latitude, Double longitude) {
        this.fullAddressComplement = fullAddressComplement;
        this.fullAddress = fullAddress;
        this.houseNumber = houseNumber;
        this.road = road;
        this.city = city;
        this.state = state;
        this.country = country;
        this.countryCode = countryCode;
        this.postCode = postCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address(String fullAddress, String houseNumber, String road, String city, String state, String country,
                   String countryCode, String postCode, Double latitude, Double longitude) {
        this.fullAddress = fullAddress;
        this.houseNumber = houseNumber;
        this.road = road;
        this.city = city;
        this.state = state;
        this.country = country;
        this.countryCode = countryCode;
        this.postCode = postCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    @Transient
    public Address clone() {
        Address address = new Address(fullAddressComplement, fullAddress, houseNumber,
                road, city, state, country, countryCode, postCode, latitude, longitude);
        return address;
    }

    @Transient
    public String shortAddress() {
        return Stream.of(road, houseNumber, city).filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining(", "));
    }
}
