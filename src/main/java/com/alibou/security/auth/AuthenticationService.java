package com.alibou.security.auth;

import com.alibou.security.check.Code;
import com.alibou.security.check.GenerateNumber;
import com.alibou.security.check.SendEmail;
import com.alibou.security.config.JwtService;
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
    private int save;
//    private int number =new GenerateNumber().generate();
    private final AuthenticationManager authenticationManager;
    public boolean check(Code s, RegisterRequest request){
        int number=getNumber();
        System.out.println(number);
//int number=new GenerateNumber().generate();
//        Check check= Check.builder().numberUser(Integer.parseInt(s)).numberGene(number).build();
        if(repository.findByEmail(request.getEmail()).isEmpty() &&number== Integer.parseInt(s.getCode())){
            register(request);
            return number==Integer.parseInt(s.getCode());
        }
        return false;

    }
    public static int getNumber(){
        return new GenerateNumber().generate();
    }
    public boolean preRegister(RegisterRequest request,Code s){
            int number = new GenerateNumber().generate();
if(s==null) {
    SendEmail sendEmail = new SendEmail();
    sendEmail.sendEmail(number, request.getEmail());
    save=number;
}
        if(s!=null) {

            if (repository.findByEmail(request.getEmail()).isEmpty() && save == Integer.parseInt(s.getCode())&&save!=0&&repository.findByLogin(request.getLogin()).isEmpty()) {
                register(request);
                return true;
            }
            return false;
        }
        number=0;

        return true;



    }
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

//        GenerateNumber generateNumber=new GenerateNumber();
//        int save=generateNumber.generate();

//        if(new Check().ch(save,request.))
                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()

                        .token(jwtToken)
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
