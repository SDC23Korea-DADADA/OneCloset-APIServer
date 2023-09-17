package com.dadada.onecloset.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public String healthCheck() {
        return "dadada_0917_1756";
    }

}
