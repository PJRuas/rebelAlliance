package com.rebels.alliance.domains;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Location {
    String galaxyName;
    Long latitude;
    Long longitude;
}
