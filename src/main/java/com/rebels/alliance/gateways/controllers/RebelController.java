package com.rebels.alliance.gateways.controllers;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.gateways.controllers.requests.RebelRequest;
import com.rebels.alliance.gateways.controllers.responses.RebelResponse;
import com.rebels.alliance.usecases.InventoryService;
import com.rebels.alliance.usecases.RebelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rebels")
@RequiredArgsConstructor
@Slf4j
public class RebelController {

    private final RebelService rebelService;
    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RebelResponse createRebel(@RequestBody RebelRequest rebelRequest) {
        log.info("Registering rebel");
        Rebel rebel = rebelRequest.toRebel();
//        inventoryService.generateRandomItems(rebel.getInventory());
        rebelService.createRebel(rebel);
        log.info("Rebel {} [id = {}] registered successfully", rebel.getName(), rebel.getId());
        return new RebelResponse(rebel);
    }

    @PutMapping(value = "/{id}/inventory")
    public RebelResponse generateInvetory(@PathVariable("id") Long rebelId) {
        Rebel rebel = rebelService.listByParam("id", rebelId).get(0);
        inventoryService.generateRandomItems(rebel.getInventory());
        return new RebelResponse(rebel);
    }


    @GetMapping
    public List<RebelResponse> getAllRebels() {
        List<Rebel> result = rebelService.listAll();
        List<RebelResponse> responses = new ArrayList<>();
        for (Rebel rebel : result) {
            responses.add(new RebelResponse(rebel));
        }
        log.info("Listing all rebels [{} result(s)]", responses.size());
        return responses;
    }

    @GetMapping(value = "/filter")
    @ResponseStatus(HttpStatus.FOUND)
    public <V> List<RebelResponse> getBy(@RequestParam String param, @RequestParam V value) {
        List<Rebel> result = rebelService.listByParam(param, value);
        List<RebelResponse> responses = new ArrayList<>();
        for (Rebel rebel : result) {
            responses.add(new RebelResponse(rebel));
        }
        log.info("Searching rebels by {} [{} result(s)]", param, responses.size());
        return responses;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public RebelResponse updateRebel(@RequestBody RebelRequest rebelRequest) {
        log.info("Updating rebel");
        Rebel rebelToUpdate = rebelRequest.toRebel();
        return new RebelResponse(rebelService.updateRebel(rebelToUpdate));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteRebel(@PathVariable("id") Long rebelId) {
        Rebel rebelToDelete = new Rebel();
        rebelToDelete.setId(rebelId);
        log.info("Deleting Rebel [id = {}]", rebelId);
        rebelService.deleteRebel(rebelToDelete);
        log.info("Rebel delete successful");
    }
}
