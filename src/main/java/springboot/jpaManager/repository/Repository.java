package springboot.jpaManager.repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface Repository<T> {


    T findOne(Long id);
    Long save(T t);

    List<T> findAll();

    List<T> findByName(String name);

}
