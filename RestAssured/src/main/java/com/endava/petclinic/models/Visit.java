package com.endava.petclinic.models;

import java.util.Objects;

public class Visit {
    private Integer id;
    private String description;
    private String date;
    private Pet pet;
    public Visit() {
    }

    public Visit(String description, String date, Pet pet) {
        this.description = description;
        this.date = date;
        this.pet = pet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(description, visit.description) && Objects.equals(date, visit.date) && Objects.equals(pet, visit.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, date, pet);
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", pet=" + pet +
                '}';
    }
}
