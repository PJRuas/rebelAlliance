package com.rebels.alliance.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BusinessValidationExceptionTest {

    @Test
    public void shouldCreateListOfValidators_when_receiveErrors(){
        List<String> errors = new ArrayList<>();
        errors.add("generic error");
        BusinessValidationException validation = new BusinessValidationException(errors);

        Assertions.assertEquals(validation.getValidationErrors(), List.of("generic error"));
    }

    @Test
    public void shouldCreateListOfValidators_when_receiveErrorMessage(){
        BusinessValidationException validation = new BusinessValidationException("generic error");

        Assertions.assertEquals(validation.getValidationErrors(), List.of("generic error"));
    }

}