package springboot.jpaManager;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class beans {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }

}
