package com.rebels.alliance.gateways.controllers;

import com.rebels.alliance.gateways.controllers.responses.ReportResponse;
import com.rebels.alliance.usecases.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ReportResponse getReports() {
        log.info("Reporting data...");
        return new ReportResponse(reportService.showReports());
    }

}
