package com.rebels.alliance.gateways.controllers;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.usecases.RebelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rebels")
@RequiredArgsConstructor
@Slf4j
public class RebelController {

    private final RebelService rebelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rebel createRebel(@RequestBody Rebel rebel) {
        log.info("Registering rebel");
        rebelService.createRebel(rebel);
        log.info("Rebel {} [id = {}] registered successfully", rebel.getName(), rebel.getId());
        return rebel;
    }

    @GetMapping
    public List<Rebel> getAllRebels() {
        List<Rebel> result = rebelService.listAll();
        log.info("Listing all rebels [{} result(s)]", result.size());
        return result;
    }

    @GetMapping(value = "/filter")
    @ResponseStatus(HttpStatus.FOUND)
    public <V> List<Rebel> getBy(@RequestParam String param, @RequestParam V value) {
        List<Rebel> result = rebelService.listByParam(param, value);
        log.info("Searching rebels by {} [{} result(s)]", param, result.size());
        return result;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Rebel updateRebel(@RequestBody Rebel rebel) {
        log.info("Updating rebel");
        return rebelService.updateRebel(rebel);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteRebel(@PathVariable("id") Long rebelId) {
        Rebel rebelToDelete = new Rebel();
        rebelToDelete.setId(rebelId);
        rebelService.deleteRebel(rebelToDelete);
        log.info("Deleting Rebel [id = {}]", rebelId);
        log.info("Rebel delete successful");
    }
}
