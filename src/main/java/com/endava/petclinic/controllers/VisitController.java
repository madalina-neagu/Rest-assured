package com.endava.petclinic.controllers;

import com.endava.petclinic.models.Pet;
import com.endava.petclinic.models.Visit;
import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;

public class VisitController {
    public static Visit generateNewRandomVisit(Pet pet){
        Visit visit = new Visit();
        Faker faker = new Faker();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        visit.setDescription(faker.medical().diseaseName());
        visit.setDate(sdf.format(faker.date().birthday()));
        visit.setPet(pet);
        return visit;
    }
}
