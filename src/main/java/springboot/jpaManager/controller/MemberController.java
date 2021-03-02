package springboot.jpaManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class MemberController {

    @GetMapping("register")
    public String memberRegister(){

        return "member/register";
    }

    @GetMapping("list")
    public String memberList(){

        return "member/list";
    }
}
