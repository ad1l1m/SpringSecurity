package adil.spring.security.user.demo;

import adil.spring.security.auth.AuthenticationService;
import adil.spring.security.auth.RegisterRequest;
import adil.spring.security.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/r")
public class DemoController {
    RegisterRequest request;
    private  UserRepository repository;
    private  AuthenticationService service;

    @GetMapping("/demo-controller")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello secured endpoint");
    }
    @GetMapping("/h")
    public ModelAndView h(Model model) {
        model.addAttribute("message","sadasd");
        model.addAttribute("item","sadasd");
        return new ModelAndView("h");
         // specify the template name without the "templates/" prefix and without the ".html" extension
    }
//    @PostMapping("/register")
//    public String register(
//            @RequestParam String login,
//            @RequestParam String email,
//            @RequestParam String password,
//            Model model
//    ) {
//        RegisterRequest request=new RegisterRequest();
//        request.setLogin(login);
//        request.setLogin(email);
//        request.setLogin(password);
//        System.out.println("sadasdas");
//        this.request=request;
//        if (repository.findByEmail(request.getEmail()).isEmpty() && repository.findByLogin(request.getLogin()).isEmpty()) {
//            service.preRegister(request, null);
////            return new RedirectView("/api/v1/auth/check");
//        }
////        return new RedirectView("/api/v1/auth/register/account/register");
//        return "redirect:/check";
//
//    }
//    @PostMapping("/check")
//    public String check(
//            @RequestBody Code request
//    ) {
//
//        if (service.preRegister(this.request, request)) {
//            return "ok";
//        } else {
//            return "false";
//        }
//    }
//    @GetMapping("/register")
//    public ModelAndView register(Model model) {
//        model.addAttribute("request", new RegisterRequest());
//        return new ModelAndView("register");
//    }

}
