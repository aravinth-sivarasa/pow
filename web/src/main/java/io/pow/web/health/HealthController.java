package io.pow.web.health;

import java.security.Principal;
import java.util.Map;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("health")
    Map<String,String> health(Principal principal){
        return Map.of("loggedInUser","Hello : "+principal.getName());
    }
    

    @GetMapping("admin")
    Map<String,String> healthAdmin(Principal principal){
        return Map.of("loggedInUser","Hello Admin: "+principal.getName());
    }
}
