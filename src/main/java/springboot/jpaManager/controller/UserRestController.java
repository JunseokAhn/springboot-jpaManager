package springboot.jpaManager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("api")
public class UserRestController {

    @GetMapping("user")
    public Object userInfo(@AuthenticationPrincipal Object user){
        return user;
    }

}
