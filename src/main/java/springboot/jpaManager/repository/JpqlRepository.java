package springboot.jpaManager.repository;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public abstract class JpqlRepository<T> implements Repository<T> {

     final EntityManager em;

     public void delete(T t) {
          em.remove(t);
     }
     public void flush() {
          em.flush();
     }

     public List<T> findAll(){
          return null;
     }
     abstract List<T> findAll_noDistinct();
     abstract List<T> findAll_distinct();
     /**
      * 1. distinct > db에서 중복데이터를 제거
      * 2. JVM 메모리에 데이터를 한방쿼리로 가져온 뒤, Entity를 기준으로 조인되는 Many측 데이터를 List로 집어넣어준다.
      */
     abstract List<T> findAll_fetchJoin();
     abstract List<T> findAll_fetchJoin_noDistinct();
     /**
      * 1. 페이징이 필요한 엔티티를 포함하여 조회
      * 2. fetchJoin으로 모든 레코드를 가져와 어플리케이션 인메모리에서 페이징처리
      * toOne관계면 상관없지만, toMany관계면 어플리케이션에 부하가 발생하므로 비권장옵션
      */
     abstract List<T> findAll_paging_inMemory();
     /**
      * 1. toOne관계의 엔티티는 DB레벨에서 페이징처리 가능
      * 2. toMany관계의 엔티티는 제외하고 조회하고, 이후 Many엔티티가 필요하면 레코드단위로 새로 조회가 발생한다.
      */
     abstract List<T> findAll_paging_inDB();

}
