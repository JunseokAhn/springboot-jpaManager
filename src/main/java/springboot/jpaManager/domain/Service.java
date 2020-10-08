package springboot.jpaManager.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
public class Service extends Member {

    @Enumerated(EnumType.STRING)
    private WorkType type;
}
