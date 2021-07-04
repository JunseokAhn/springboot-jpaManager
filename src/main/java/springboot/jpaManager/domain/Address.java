package springboot.jpaManager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public static final Address createAddress(String city, String street, String zipcode) {
        Address address = new Address();
        address.city = city;
        address.street = street;
        address.zipcode = zipcode;

        return address;
    }

    public void changeAddress(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
