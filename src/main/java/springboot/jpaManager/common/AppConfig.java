package springboot.jpaManager.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.domain.Team;
import springboot.jpaManager.dto.MemberDTO;
import springboot.jpaManager.dto.TeamDTO;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        // private생성자 매핑 허용
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        // Member entity to DTO 매핑설정
        modelMapper.typeMap(Member.class, MemberDTO.List.class).addMappings(mapper -> {
            mapper.map(Member -> Member.getTeam().getName(), MemberDTO.List::setTeamName);
            mapper.map(Member -> Member.getTeam().getCompany().getName(), MemberDTO.List::setCompanyName);
        });

        // Team entity to DTO 매핑설정
        modelMapper.typeMap(Team.class, TeamDTO.Update.class).addMappings(mapper -> {
            mapper.map(Team -> Team.getCompany().getId(), TeamDTO.Update::setCompanyId);
            mapper.map(Team -> Team.getCompany().getName(), TeamDTO.Update::setCompanyName);
        });

        return modelMapper;
    }
}
