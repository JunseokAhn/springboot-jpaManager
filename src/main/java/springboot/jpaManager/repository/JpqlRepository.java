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
     abstract List<T> findAll_noOption();
     abstract List<T> findAll_distinct();
     /**
      * 1. distinct > db에서 중복데이터를 제거
      * 2. JVM 메모리에 데이터를 한방쿼리로 가져온 뒤, Entity를 기준으로 조인되는 Many측 데이터를 List로 집어넣어준다.
      *   하지만, 페이징처리가 불가능
      */
     abstract List<T> findAll_fetchJoin();


     /**
      * 1. 페이징이 필요한 엔티티를 제외하고 조회.
      * 2. toMany관계에서 Many의 레코드 수만큼 쿼리가 추가로 발생.
      * 3. JVM 메모리에 Many엔티티를 가져와 페이징처리가 가능해진다.
     */
     abstract List<T> findAll_paging_inMemory();
     abstract List<T> findAll_paging_inDB();

}
