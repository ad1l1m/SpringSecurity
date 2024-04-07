package com.alibou.security.auth;
import com.alibou.security.config.JwtService;
import com.alibou.security.config.SaveUser;
import com.alibou.security.learnPart.CreateRepository;
import com.alibou.security.user.Role;
import com.alibou.security.user.User;
import com.alibou.security.user.UserRepository;
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
