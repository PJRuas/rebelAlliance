package com.rebels.alliance.domains.models;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Inventory {
    Rebel owner;
    List<String> itens;
    boolean status;
}
