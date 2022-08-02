package com.endava.petclinic.controllers;

import com.endava.petclinic.models.PetType;
import com.github.javafaker.Faker;

public class PetTypeController {
    public static PetType generateNewPetType(){
        PetType type = new PetType();
        Faker faker = new Faker();
        type.setName(faker.animal().name());
        return type;
    }
}
