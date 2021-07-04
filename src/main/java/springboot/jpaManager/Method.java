package springboot.jpaManager;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Method {

    private final ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {

        return source.stream().map(element ->
                modelMapper.map(element, targetClass)
        ).collect(Collectors.toList());
    }

}
