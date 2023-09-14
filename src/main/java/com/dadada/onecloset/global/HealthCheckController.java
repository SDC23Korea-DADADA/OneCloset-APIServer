package com.dadada.onecloset.global;

import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
public class HealthCheckController {

//    private final UserRepository userRepository;

    @GetMapping("/")
    public String healthCheck() {

//        User user = userRepository.findById(12L).orElseThrow();
//        System.out.println(user.getCreatedDate().getYear());
//        System.out.println(user.getCreatedDate().getMonthValue());
//        System.out.println(user.getCreatedDate().getDayOfMonth());
//
//        String hexString = "0xFF000000";
//        long longValue = Long.decode(hexString);
//
//        System.out.println("변환된 long 값: " + longValue);

        return "dadada!!!";
    }

}
