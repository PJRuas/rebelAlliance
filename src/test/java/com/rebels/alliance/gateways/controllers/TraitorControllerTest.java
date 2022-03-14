package com.rebels.alliance.gateways.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebels.alliance.domains.*;
import com.rebels.alliance.domains.enums.Gender;
import com.rebels.alliance.gateways.controllers.requests.RebelRequest;
import com.rebels.alliance.usecases.RebelService;
import com.rebels.alliance.usecases.TraitorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TraitorControllerTest {

    @Autowired private MockMvc mvc;
    private Item item = new Item("GUN", 1);
    private List<Item> items = new ArrayList<>(Arrays.asList(item));
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean private TraitorService traitorService;
    @MockBean private RebelService rebelService;

    public RebelRequest getRebelRequest(){
        Inventory inventory = new Inventory();
        inventory.setItems(items);

        Location location = new Location();
        location.setGalaxyName("Via láctea");
        location.setLongitude(22D);
        location.setLongitude(25D);

        RebelRequest rebel = new RebelRequest();

        rebel.setAge(18);
        rebel.setGender(Gender.ALIEN);
        rebel.setInventory(inventory);
        rebel.setLocation(location);
        rebel.setName("Asuka");

        return rebel;
    }

    public Traitor getTraitor(){
        Traitor traitor = new Traitor(getRebelRequest().toRebel());
        return traitor;
    }

    @Test
    public void shouldCreateANewTraitor() throws Exception{
        RebelRequest rebelRequest = getRebelRequest();
        Rebel rebel = rebelRequest.toRebel();
        rebel.setId(1L);

        Mockito.when(rebelService.listByParam(any(String.class), any(Long.class))).thenReturn(List.of(rebel));
        Mockito.when(traitorService.markTraitor(rebel)).thenReturn(getTraitor());

        mvc.perform(
                post("/traitors/?rebelId=" + rebel.getId())
        ).andExpect(status().isCreated());
    }

    @Test
    public void shouldListAllTraitors() throws Exception{

        List<Traitor> traitors = new ArrayList<>(List.of(getTraitor()));

        Mockito.when(traitorService.listAll()).thenReturn(traitors);

        mvc.perform(
                get("/traitors")
        ).andExpect(status().isOk());

    }

    @Test
    public void shouldReturnTraitorsByParam() throws Exception{

        List<Traitor> traitors = new ArrayList<>(List.of(getTraitor()));

        Mockito.when(traitorService.listByParam(any(), any())).thenReturn(traitors);

        mvc.perform(
                get("/traitors/filter/?param=id&value=" + traitors.get(0).getId())
        ).andExpect(status().isFound());
    }

    @Test
    public void shouldDelateTraitor() throws Exception{

        List<Traitor> traitors = new ArrayList<>(List.of(getTraitor()));

        Mockito.when(traitorService.listByParam(any(), any())).thenReturn(traitors);
        Mockito.doNothing().when(traitorService).delateTraitor(any(), any());

        mvc.perform(
                get("/traitors/?targetId=" + 0 + "&accuserId=" + 1)
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTraitor() throws Exception{

        Traitor traitor = getTraitor();
        traitor.setId(1L);

        Mockito.doNothing().when(traitorService).delete(traitor);

        mvc.perform(
                delete("/traitors/" + traitor.getId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }
}