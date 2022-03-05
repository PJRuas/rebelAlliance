package com.rebels.alliance.gateways.controllers;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.Traitor;
import com.rebels.alliance.usecases.RebelService;
import com.rebels.alliance.usecases.TraitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/traitors")
@RequiredArgsConstructor
@Slf4j
public class TraitorController {

    private final TraitorService traitorService;
    private final RebelService rebelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Traitor createTraitor(@RequestParam Long rebelId) {
        log.info("Checking rebel's report status");
        Rebel rebel = rebelService.listByParam("id", rebelId).get(0);
        Traitor markedTraitor = traitorService.markTraitor(rebel);
        if (markedTraitor.getId() == null) {
            log.info("Rebel {} [id = {}] is trustworthy", rebel.getName(), rebel.getId());
        } else {
            log.info("Traitor detected. Rebel {} [id = {}] has been marked", rebel.getName(), rebel.getId());
        }
        return markedTraitor;
    }

    @GetMapping
    public List<Traitor> getAllTraitors() {
        List<Traitor> result = traitorService.listAll();
        log.info("Listing all traitors [{} result(s)]", result.size());
        return result;
    }

    @GetMapping(value = "/filter")
    @ResponseStatus(HttpStatus.FOUND)
    public <V> List<Traitor> getBy(@RequestParam String param, @RequestParam V value) {
        List<Traitor> result = traitorService.listByParam(param, value);
        log.info("Searching traitors by {} [{} result(s)]", param, result.size());
        return result;
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Rebel delateRebel(@RequestParam Long targetId, @RequestParam Long accuserId) {
        Rebel target = rebelService.listByParam("id", targetId).get(0);
        Rebel accuser = rebelService.listByParam("id", accuserId).get(0);
        traitorService.delateTraitor(target, accuser);
        log.info("Rebel {} [id = {}] has prested charges against rebel {} [id = {}]", accuser.getName(), accuser.getId(), target.getName(), target.getId());
        return target;
    }

//    @DeleteMapping(value = "/{id}")
//    public void deleteTraitor(@PathVariable("id") Long traitorId) {
//        Traitor traitorToDelete = new Traitor();
//        traitorToDelete.setId(traitorId);
//        traitorService.delete(rebelToDelete);
//        log.info("Deleting Rebel [id = {}]", rebelId);
//        log.info("Rebel delete successful");
//    }
}
