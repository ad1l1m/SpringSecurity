//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package adil.spring.security.auth;

import adil.spring.security.check.Code;
import adil.spring.security.check.GenerateNumber;
import adil.spring.security.check.SendEmail;
import adil.spring.security.config.Element;
import adil.spring.security.config.SaveUser;
import adil.spring.security.user.User;
import adil.spring.security.user.UserRepository;
import jakarta.annotation.PostConstruct;
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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.Optional;


@Controller
//@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    RegisterRequest request;

    private final UserRepository repository;
    private int save;
    private final SaveUser saveUser = new SaveUser();
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationService service;
    @PostConstruct
    public void init() {
        scheduler.scheduleAtFixedRate(saveUser::removeExpiredElements, 0, 3, TimeUnit.SECONDS);
//        scheduler.scheduleAtFixedRate(saveUser::removeExpiredElements,0, 3, TimeUnit.SECONDS);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(
@RequestBody RegisterRequest request,
            Model model
    ) {
        this.request=request;
        if (repository.findByEmail(request.getEmail()).isEmpty() && repository.findByLogin(request.getLogin()).isEmpty()) {
            SendEmail sendEmail = new SendEmail();
            save=new GenerateNumber().generate();
            sendEmail.sendEmail(save, request.getEmail());
            saveUser.addElement(request.getEmail(),new Element(String.valueOf(save)));
            return new ResponseEntity<>("A verification code has been sent", HttpStatus.OK);


        }
        return ResponseEntity.badRequest().body("Email or login already exists");
//return ResponseEntity.badRequest();
    }
    @PostMapping("/check")
    public ResponseEntity<String> check(
            @RequestBody Code request1
    ) {

        if (saveUser.searchKeyByElement(request1.getCode())!=null) {
            service.register(request);
            saveUser.removeElement(request.getEmail());
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
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "Hello world!");
        return modelAndView;
    }

    @GetMapping("customer")
    public ModelAndView customer (Model model) {
        return new ModelAndView("register");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthenticationRequest request,
            Model model) {
        System.out.println(request);
            Optional<User> userOptional = repository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {

            User user = userOptional.get();
            String hashedPassword = user.getPassword();
            boolean matches = passwordEncoder.matches(request.getPassword(), hashedPassword);

        if (repository.findByEmail(request.getEmail()).isPresent() && matches) {
//                service.authenticate(request);
                return ResponseEntity.ok(service.authenticate(request));
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