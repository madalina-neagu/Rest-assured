package com.endava.petclinic.controllers;

import com.endava.petclinic.models.Owner;
import com.endava.petclinic.models.Pet;
import com.endava.petclinic.models.PetType;
import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;

public class PetController {
    public static Pet generateNewPet(Owner owner, PetType type){
        Faker faker = new Faker();
        Pet pet = new Pet();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        pet.setName(faker.funnyName().name());
        pet.setBirthDate(sdf.format(faker.date().birthday()));
        pet.setOwner(owner);
        pet.setType(type);
        return pet;
    }
}
