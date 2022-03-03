package com.rebels.alliance.usecases.validators;

import com.rebels.alliance.domains.Location;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationValidator {
    public List<String> validate(Location location) {
        List<String> validationErrors = new ArrayList<>();

        if (location == null) return List.of("Rebel not provided");

        if (!StringUtils.hasText(location.getGalaxyName())) {
            validationErrors.add("Missing Galaxy's name");
        }

        if (location.getLatitude() == null) {
            validationErrors.add("Missing Location's Latitude.");
        }

        if (location.getLongitude() == null) {
            validationErrors.add("Missing Location's Longitude.");
        }
        return validationErrors;
    }
}
