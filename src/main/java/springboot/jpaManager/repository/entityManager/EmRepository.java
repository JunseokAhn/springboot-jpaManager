package springboot.jpaManager.repository.entityManager;

import lombok.RequiredArgsConstructor;
import springboot.jpaManager.repository.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public abstract class EmRepository<T> implements Repository<T> {

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
     /**
      * distinct가 없으므로 데이터레코드만큼 인스턴스 생성
      */
     abstract List<T> findAll_noDistinct();
     /**
      * distinct > db에서 중복데이터를 제거 <br>
      */
     abstract List<T> findAll_distinct();
     /**
      * 1. distinct가 없으므로 데이터레코드만큼 인스턴스 생성 <br>
      * 2. fetch조인이므로 Entity를 기준으로 인메모리에서 중복을 제거하고 List반환 <br>
      */
     abstract List<T> findAll_fetchJoin_noDistinct();
     /**
      * 1. distinct > db에서 중복데이터를 제거 <br>
      * 2. fetch조인이므로 Entity를 기준으로 인메모리에서 중복을 제거하고 List반환 <br>
      * 페이징이 없다면 이 방식이 권장옵션
      */
     abstract List<T> findAll_fetchJoin();

     /**
      * 페이징이 필요한 엔티티를 포함하여 조회 <br>
      * fetchJoin으로 모든 레코드를 가져와 어플리케이션 인메모리에서 페이징처리 <br>
      * toOne관계면 상관없지만, toMany관계면 어플리케이션에 부하가 발생하므로 비권장옵션
      * @param start 페이징 시작 인덱스
      * @param end 페이징 종료 인덱스
      */
     abstract List<T> findAll_paging_inMemory(int start, int end);
     /**
      * 1. toOne관계의 엔티티는 DB레벨에서 페이징처리 가능 <br>
      * 2. toMany관계의 엔티티는 제외하고 조회하고, 이후 Many엔티티가 필요하면 레코드단위로 새로 조회가 발생한다. <br>
      * 3. BatchSize 조정으로 where in 조건으로 치환가능. <br>
      * 이 부분은 옵티마이저가 최대한 최적해줄것이므로 페이징관련 최적의 옵션
      * @param start 페이징 시작 인덱스
      * @param end 페이징 종료 인덱스
      */
     abstract List<T> findAll_paging_inDB(int start, int end);

}
