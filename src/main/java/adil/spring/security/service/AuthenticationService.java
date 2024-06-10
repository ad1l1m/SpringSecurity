package adil.spring.security.service;

import adil.spring.security.DTO.AuthenticationRequestDTO;
import adil.spring.security.DTO.AuthenticationResponseDTO;
import adil.spring.security.DTO.RegisterRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationResponseDTO register(RegisterRequestDTO request);
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
}
