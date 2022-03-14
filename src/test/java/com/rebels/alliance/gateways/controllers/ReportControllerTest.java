package com.rebels.alliance.gateways.controllers;

import com.rebels.alliance.domains.Report;
import com.rebels.alliance.domains.Traitor;
import com.rebels.alliance.usecases.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerTest {

    @Autowired MockMvc mvc;
    @MockBean
    ReportService reportService;
    @MockBean
    Report report;

    @Test
    public void shouldListAllReports() throws Exception{

        Mockito.when(reportService.showReports()).thenReturn(report);

        mvc.perform(
                get("/reports")
        ).andExpect(status().isOk());

    }

}