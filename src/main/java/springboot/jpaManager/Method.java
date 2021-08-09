package springboot.jpaManager;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Method {

    private final ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {

        return source.stream().map(element ->
                modelMapper.map(element, targetClass)
        ).collect(Collectors.toList());
    }

    public Long isNotReflected(Long id) {

        if (id <= 0)
            throw new RuntimeException("save failed");
        return id;
    }
}
