//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibou.security.auth;

import com.alibou.security.check.Code;
import com.alibou.security.user.User;
import com.alibou.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
//@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    User user;
    RegisterRequest request;
    AuthenticationRequest request1;
    private final UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(
@RequestBody RegisterRequest request,
            Model model
    ) {
        this.request=request;
        if (repository.findByEmail(request.getEmail()).isEmpty() && repository.findByLogin(request.getLogin()).isEmpty()) {
            service.preRegister(request, null);
            return new ResponseEntity<>("Ok", HttpStatus.OK);

        }
        return new ResponseEntity<>("Email or login already exists", HttpStatus.OK);

    }
    @PostMapping("/check")
    public ResponseEntity<String> check(
            @RequestBody Code request
    ) {
        if (service.preRegister(this.request, request)) {
            this.request = null;
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/check")
    public ModelAndView check(Model model) {

        return new ModelAndView("check");
    }

    @GetMapping("/register")
        public ModelAndView register(Model model) {

            return new ModelAndView("register");
        }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody AuthenticationRequest request,
            Model model) {

            Optional<User> userOptional = repository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {

            User user = userOptional.get();
            String hashedPassword = user.getPassword();
            boolean matches = passwordEncoder.matches(request.getPassword(), hashedPassword);

        if (repository.findByEmail(request.getEmail()).isPresent() && matches) {
                service.authenticate(request);
                return new ResponseEntity<>("Ok", HttpStatus.OK);
            } else {

                return new ResponseEntity<>("no", HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<>("no", HttpStatus.NOT_FOUND);

        }
        }
    @GetMapping("/login")
    public ModelAndView login(Model model) {
        return new ModelAndView("authenticate");
    }
}