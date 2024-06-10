package adil.spring.security.rest;

import adil.spring.security.DTO.AuthenticationRequestDTO;
import adil.spring.security.DTO.RegisterRequestDTO;
import adil.spring.security.DTO.CodeDTO;
import adil.spring.security.service.UserSessionManagerService;
import adil.spring.security.service.impl.AuthenticationServiceImpl;
import adil.spring.security.util.GenerateNumber;
import adil.spring.security.util.SendEmail;
import adil.spring.security.config.Element;
import adil.spring.security.entity.User;
import adil.spring.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.Optional;
@Controller
//@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

//    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());
    private final UserRepository repository;
    private final UserSessionManagerService saveUser;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationServiceImpl service;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        if (repository.findByEmail(request.getEmail()).isEmpty() && repository.findByLogin(request.getLogin()).isEmpty()) {
            int verificationCode = new GenerateNumber().generate();
            saveUser.addElement(request.getEmail(), new Element(String.valueOf(verificationCode)));
            saveUser.addUserRequest(request.getEmail(), request);
            System.out.println(saveUser);
            new Thread(() -> {
                SendEmail sendEmail = new SendEmail();
                sendEmail.sendEmail(verificationCode, request.getEmail());
            }).start();

            return new ResponseEntity<>("A verification code has been sent", HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("Email or login already exists");
    }

    @PostMapping("/check")
    public ResponseEntity<String> check(@RequestBody CodeDTO request1) {
        String email = saveUser.searchKeyByElement(request1.getCode());
        if (email != null && saveUser.searchElement(email) != null) {
            RegisterRequestDTO registerRequest = saveUser.getUserRequest(email);
            if (registerRequest != null) {
                service.register(registerRequest);
                saveUser.removeElement(email);
                return new ResponseEntity<>("Ok", HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid email or user not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid code");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        Optional<User> userOptional = repository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(service.authenticate(request));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid email or password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid email or password");
        }
    }
}
