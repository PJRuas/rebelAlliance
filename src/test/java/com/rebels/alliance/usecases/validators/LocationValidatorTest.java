package com.rebels.alliance.usecases.validators;

import com.rebels.alliance.domains.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class LocationValidatorTest {

    private LocationValidator validator = new LocationValidator();

    @Test
    public void validateNullLocation(){
        List<String> validationResult = validator.validate(null);
        Assertions.assertEquals(validationResult, List.of("Rebel not provided"));
    }

    @Test
    public void validateNotNullLocation(){
        Location location = new Location();
        List<String> validationResult = validator.validate(location);
        Assertions.assertEquals(validationResult, List.of(
                "Missing Galaxy's name",
                "Missing Location's Latitude.",
                "Missing Location's Longitude."
        ));
    }

}