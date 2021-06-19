package springboot.jpaManager.dto;

import lombok.*;
import springboot.jpaManager.domain.Address;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String city;
    private String street;
    private String zipcode;

    public Address transEntity() {

        return Address.createAddress(city, street, zipcode);
    }
}
