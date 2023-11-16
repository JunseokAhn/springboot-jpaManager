package springboot.jpaManager.repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface Repository<T> {


    Map<String,String> map= new HashMap<>();
    final EntityManager em = null;
    T findOne(Long id);
    Long save(T t);

    List<T> findAll();

    List<T> findByName(String name);

}
