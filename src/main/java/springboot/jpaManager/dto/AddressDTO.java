package springboot.jpaManager.dto;

import lombok.*;
import springboot.jpaManager.domain.Address;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    @NotEmpty
    private String city;
    @NotEmpty
    private String street;
    @NotEmpty
    private String zipcode;

    public Address createEntity() {

        return Address.createAddress(city, street, zipcode);
    }
}
