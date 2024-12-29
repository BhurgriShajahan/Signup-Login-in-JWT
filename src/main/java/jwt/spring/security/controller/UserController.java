package jwt.spring.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/msg")
    @ResponseBody
    public String message() {
        return "Hello Jack";
    }
}
