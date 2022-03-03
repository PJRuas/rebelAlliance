package com.rebels.alliance.gateways.controllers;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.gateways.implementations.collection.RebelRepositoryCollection;
import com.rebels.alliance.usecases.RebelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
        log.info("Rebel {} registered successfully", rebel.getName());
        return rebel;
    }

    @GetMapping
    public List<Rebel> getAllRebels() {
        log.info("Listing all rebels");
        return rebelService.listAll();
    }

    @GetMapping(value = "/filter")
    public <V> List<Rebel> getBy(@RequestParam String param, @RequestParam V value) {
        log.info("Searching rebels by {}", param);
        return rebelService.listByParam(param, value);
    }

    @PutMapping(value = "/{id}")
    public Rebel updateRebel(@RequestBody Rebel rebel) {
        log.info("Updating rebel");
        return rebelService.updateRebel(rebel);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteRebel(@PathVariable("id") Long rebelId) {
        log.info("Deleting Rebel (id = {})", rebelId);
        for (Rebel rebel : RebelRepositoryCollection.rebels) {
            if (Objects.equals(rebel.getId(), rebelId)) {
                rebelService.deleteRebel(rebel);
                log.info("Rebel delete successful");
                break;
            }
        }
    }
}
