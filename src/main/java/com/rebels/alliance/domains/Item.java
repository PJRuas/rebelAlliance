package com.rebels.alliance.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Item {
    String name;
    int qtd;

    public Item(String name, int qtd) {
        setName(name);
        setQtd(qtd);
    }
}
