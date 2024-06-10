package adil.spring.security.service.impl;
import adil.spring.security.DTO.AuthenticationRequestDTO;
import adil.spring.security.DTO.AuthenticationResponseDTO;
import adil.spring.security.DTO.RegisterRequestDTO;
import adil.spring.security.config.JwtService;
import adil.spring.security.config.Role;
import adil.spring.security.repository.UserRepository;
import adil.spring.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger LOGGER = Logger.getLogger(AuthenticationServiceImpl.class.getName());




    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        try {
            if (repository.findByEmail(request.getEmail()).isEmpty()) {
                var user = User.builder()
                        .login(request.getLogin())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .build();

                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                LOGGER.info("User registered successfully: " + user.getEmail());
                return AuthenticationResponseDTO.builder().token(jwtToken).build();
            } else {
                LOGGER.warning("Email already in use: " + request.getEmail());
                throw new IllegalArgumentException("Email already in use");
            }
        } catch (Exception e) {
            LOGGER.severe("Registration failed for email: " + request.getEmail() + " - " + e.getMessage());
            throw new RuntimeException("Registration failed", e);
        }
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
                )

        );
        var user=repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        LOGGER.info("User authenticated successfully: " + request.getEmail());
        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

}
