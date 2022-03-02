package com.rebels.alliance.gateways.controllers;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.gateways.controllers.requests.RebelRequest;
import com.rebels.alliance.gateways.implementations.collection.RebelRepositoryCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@RestController
@RequestMapping(value = "/rebels")
@RequiredArgsConstructor
@Slf4j
public class RebelController {

    private final RebelRepositoryCollection rebelGateway;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRebel(@RequestBody Rebel rebelRequest) {
        log.info("Registering rebel");
        rebelGateway.register(rebelRequest);
        log.info("Rebel {} registered successfully", rebelRequest.getName());
    }

    @GetMapping
    public List<Rebel> getAllRebels() {
        log.info("Listing all rebels");
        return rebelGateway.findAll();
    }

    @GetMapping(value = "/{param}")
    public List<Rebel> getBy(@PathVariable("param") Predicate<? super Rebel> param) {
        log.info("Searching rebels by {}", param);
        return rebelGateway.findByParam(param);
    }

    @PutMapping(value = "/{id}")
    public Rebel updateRebel(@RequestBody RebelRequest rebelRequest) {
        log.info("Updating rebel");
        return rebelGateway.updateRebel(rebelRequest);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteRebel(@PathVariable("id") String rebelId) {
        for (Rebel rebel : RebelRepositoryCollection.rebels) {
            if (Objects.equals(rebel.getId(), rebelId)) {
                rebelGateway.delete(rebel);
                break;
            }
        }
    }
}
