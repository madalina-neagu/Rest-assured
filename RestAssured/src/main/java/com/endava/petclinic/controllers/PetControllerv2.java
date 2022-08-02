package com.endava.petclinic.controllers;

import com.endava.petclinic.models.Pet;
import com.endava.petclinic.models.PetType;
import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;

public class PetControllerv2 {
    public static Pet generateNewRandomPet(){
        Faker faker = new Faker();
        Pet pet = new Pet();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        pet.setName(faker.dog().name());
        pet.setOwner(OwnerController.generateNewRandomOwner());
        pet.setType(new PetType(faker.animal().name()));
        pet.setBirthDate(sdf.format(faker.date().birthday(1, 10)));
        return pet;
    }

}
