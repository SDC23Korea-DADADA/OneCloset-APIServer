package com.dadada.onecloset.domain.clothes.controller;

import com.dadada.onecloset.domain.clothes.service.AdminService;
import com.dadada.onecloset.global.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @RequestMapping("/clothes")
    public DataResponse<?> getAllClothesList(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return adminService.getAllClothesList(userId);
    }

}
