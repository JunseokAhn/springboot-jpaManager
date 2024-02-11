package springboot.jpaManager.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import springboot.jpaManager.domain.Member;
import springboot.jpaManager.dto.MemberDTO;

import java.util.Collections;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        com.example.demo.User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//        User user= new User("14117020", "kcu14117020", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//        User user= new User("14117020", "$2a$12$wH6EpNMz7NXK311v1oUYpO.VgNA6nag6jCSp8Nykkfe1FPl6T8XBe", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//        User user= new User("admin", "$2a$12$Ib6q4zS IQed8u6mIRaugM.6B.Qr7T76Oqvv195nTrOQr9rms36ms6", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        User user= new User("admin", "admin", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//        return new User(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//TODO 그럼 여기서반환하는 USER가 @Athenticaton으로 반환되는지.
        return user;
    }

}
