package springboot.jpaManager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CompanyDTO {

    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
