package springboot.jpaManager.common;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Utils {
    private final ModelMapper modelMapper;

    /** 객체 인스턴스의 타입 변환
     * @param source 변환할 인스턴스
     * @param targetClass 변환할 타입
     * @return 변환된 인스턴스
     */
    public <S, T> T map(S source, Class<T> targetClass) {
        return this.modelMapper.map(source, targetClass);
    }

    /** List element의 타입 변환
     * @param source 변환할 인스턴스
     * @param targetClass 변환할 타입
     * @return 변환된 인스턴스
     */
    public <S, T> List<T> map(List<S> source, Class<T> targetClass) {

        return source.stream().map(element ->
                this.modelMapper.map(element, targetClass)
        ).collect(Collectors.toList());
    }

    public Long isNotReflected(Long id) {

        if (id <= 0)
            throw new RuntimeException("save failed");
        return id;
    }
}
