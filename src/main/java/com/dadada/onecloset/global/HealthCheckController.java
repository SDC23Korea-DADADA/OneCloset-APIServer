package com.dadada.onecloset.global;

import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.closet.repository.ClosetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final ClosetRepository closetRepository;

    @GetMapping("/")
    public String healthCheck() {
        Closet closet = closetRepository.findById(6L).orElseThrow();
        System.out.println(closet.getCreatedDate());
        return closet.getCreatedDate().toString();
    }

}
