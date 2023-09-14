package com.dadada.onecloset.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public String healthCheck() {
        String hexString = "0xFF000000";
        Long longValue = Long.decode(hexString);
        System.out.println(longValue);
        return "dadada";
    }

}
