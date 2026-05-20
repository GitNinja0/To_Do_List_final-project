package com.juanmunguia.to_do_list_final_project.tasks;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RestController
@RequestMapping("${api-endpoint}/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboard(
            Principal principal,
            @RequestParam(required = false) String username) {
        return ResponseEntity.ok(dashboardService.getDashboardStats(principal.getName(), username));
    }
}
