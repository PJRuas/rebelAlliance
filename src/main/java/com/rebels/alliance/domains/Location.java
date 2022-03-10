package com.rebels.alliance.domains;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Location {
    String galaxyName;
    Double latitude;
    Double longitude;
}
