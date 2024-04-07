package adil.spring.security.auth;
import adil.spring.security.config.JwtService;
import adil.spring.security.learnPart.CreateRepository;
import adil.spring.security.user.Role;
import adil.spring.security.user.UserRepository;
import adil.spring.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;




    public  AuthenticationResponse register(RegisterRequest request) {
        try {
            if (repository.findByEmail(request.getEmail()).isEmpty()) {
                CreateRepository c=new CreateRepository();
                c.createNameTask(request.getLogin());
                var user = User.builder()
                        .login(request.getLogin())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .build();

                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken)
                        .build();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
                )

        );
        var user=repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
