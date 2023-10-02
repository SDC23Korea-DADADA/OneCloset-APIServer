package com.dadada.onecloset.domain.clothes.controller;

import com.dadada.onecloset.domain.clothes.service.AdminService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/clothes")
    public DataResponse<?> getAllClothesList(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return adminService.getAllClothesList(userId);
    }

    @PostMapping("/clothes/{id}")
    public CommonResponse switchIsTraining(@PathVariable("id") Long clothesId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return adminService.switchIsTraining(clothesId, userId);
    }

}
