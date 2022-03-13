package com.rebels.alliance.gateways.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebels.alliance.domains.Inventory;
import com.rebels.alliance.domains.Item;
import com.rebels.alliance.domains.Location;
import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.enums.Gender;
import com.rebels.alliance.gateways.controllers.requests.RebelRequest;
import com.rebels.alliance.usecases.RebelService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
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
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RebelControllerTest {

    @Autowired private MockMvc mvc;
    private Item item = new Item("GUN", 1);
    private List<Item> items = new ArrayList<>(Arrays.asList(item));
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private RebelService rebelService;

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

    @Test
    public void shouldCreateANewRebel() throws Exception{
        RebelRequest rebelRequest = getRebelRequest();
        String jsonRebel = mapper.writeValueAsString(rebelRequest);
        Rebel rebel = rebelRequest.toRebel();
        rebel.setId(1L);

        Mockito.when(rebelService.createRebel(any(Rebel.class))).thenReturn(rebel);

        mvc.perform(
                post("/rebels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRebel)
        ).andExpect(status().isCreated());

        ArgumentCaptor<Rebel> captor = ArgumentCaptor.forClass(Rebel.class);

        Mockito.verify(rebelService).createRebel(captor.capture());

        Rebel capturedRebel = captor.getValue();

        Assertions.assertEquals(rebel.getName(), capturedRebel.getName());
        Assertions.assertEquals(rebel.getAge(), capturedRebel.getAge());
        Assertions.assertEquals(rebel.getLocation(), capturedRebel.getLocation());
        Assertions.assertEquals(rebel.getGender(), capturedRebel.getGender());
    }

    @Test
    public void shouldFindARebelById() throws Exception{

        RebelRequest rebelRequest = getRebelRequest();
        rebelRequest.setId(1L);
        List<Rebel> rebels = getListOfRebels();

        Mockito.when(rebelService.listByParam("id", rebelRequest.getId())).thenReturn(rebels);

        mvc.perform(
                get("/rebels/filter/?param=id&value=" + 1)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isFound());

    }

    @Test
    public void shouldListAllRebels() throws Exception{

        List<Rebel> rebels = getListOfRebels();

        Mockito.when(rebelService.listAll()).thenReturn(rebels);

        mvc.perform(
                get("/rebels")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

//    GenerateInventory
    @Test
    public void shouldGenerateInventory(){
        
    }

//    Update


//    Delete

    private List<Rebel> getListOfRebels() {
        RebelRequest rebelRequest = getRebelRequest();
        rebelRequest.setId(1L);
        Rebel rebel = rebelRequest.toRebel();
        List<Rebel> rebels = new ArrayList<>(List.of(rebel));
        return rebels;
    }
}