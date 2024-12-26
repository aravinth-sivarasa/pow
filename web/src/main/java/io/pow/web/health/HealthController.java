package io.pow.web.health;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Value("${git.test.value:-1}")
    String git_value;

    @GetMapping("health")
    Map<String,String> health(Principal principal){
        return Map.of("loggedInUser","Hello : "+principal.getName()+", value from config server: "+git_value);
    }
    

    @GetMapping("admin")
    Map<String,String> healthAdmin(Principal principal){
        return Map.of("loggedInUser","Hello Admin: "+principal.getName());
    }
}
