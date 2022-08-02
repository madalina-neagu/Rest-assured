package com.endava.petclinic;

import com.endava.petclinic.controllers.OwnerController;
import com.endava.petclinic.controllers.PetController;
import com.endava.petclinic.controllers.PetTypeController;
import com.endava.petclinic.controllers.VisitController;
import com.endava.petclinic.models.Owner;
import com.endava.petclinic.models.Pet;
import com.endava.petclinic.models.PetType;
import com.endava.petclinic.models.Visit;
import com.endava.petclinic.utils.EnvReader;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PetClinicTestDay2 {

    //Homework exercise 3
    @Test
    public void addPetTest(){
        Owner owner = OwnerController.generateNewRandomOwner();

        ValidatableResponse postOwnerResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(owner).log().all()
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        owner.setId(postOwnerResponse.extract().jsonPath().getInt("id"));

        ValidatableResponse getOwnerResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .pathParam("ownerId", owner.getId())
                .when()
                .get("/api/owners/{ownerId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Owner ownerFromGetResponse = getOwnerResponse.extract().as(Owner.class);

        PetType type = PetTypeController.generateNewPetType();

        ValidatableResponse postPetTypeResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(type).log().all()
                .post("/api/pettypes").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        type.setId(postPetTypeResponse.extract().jsonPath().getInt("id"));

        ValidatableResponse getPetTypeResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .pathParam("petTypeId", type.getId())
                .when()
                .get("/api/pettypes/{petTypeId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        PetType petTypeFromGetResponse = getPetTypeResponse.extract().as(PetType.class);


        Pet pet = PetController.generateNewPet(ownerFromGetResponse, petTypeFromGetResponse);

        ValidatableResponse postPetResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(pet)
                .when().log().all()
                .post("/api/pets").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        pet.setId(postPetResponse.extract().jsonPath().getInt("id"));

        ValidatableResponse getPetResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .pathParam("petId", pet.getId())
                .when()
                .get("/api/pets/{petId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Pet petFromGetResponse = getPetResponse.extract().as(Pet.class);

        assertThat(petFromGetResponse, is(pet));
    }

    //Homework exercise 4
    @Test
    public void getPetList(){
        given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .when() //.log().all()
                .get("/api/pets")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

    }


    //Homework exercise 5
    @Test
    public void addVisit(){
        Owner owner = OwnerController.generateNewRandomOwner();

        ValidatableResponse postOwnerResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(owner).log().all()
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        owner.setId(postOwnerResponse.extract().jsonPath().getInt("id"));

        ValidatableResponse getOwnerResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .pathParam("ownerId", owner.getId())
                .when()
                .get("/api/owners/{ownerId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Owner ownerFromGetResponse = getOwnerResponse.extract().as(Owner.class);

        PetType type = PetTypeController.generateNewPetType();

        ValidatableResponse postPetTypeResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(type).log().all()
                .post("/api/pettypes").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        type.setId(postPetTypeResponse.extract().jsonPath().getInt("id"));

        ValidatableResponse getPetTypeResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .pathParam("petTypeId", type.getId())
                .when()
                .get("/api/pettypes/{petTypeId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        PetType petTypeFromGetResponse = getPetTypeResponse.extract().as(PetType.class);


        Pet pet = PetController.generateNewPet(ownerFromGetResponse, petTypeFromGetResponse);

        ValidatableResponse postPetResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(pet)
                .when().log().all()
                .post("/api/pets").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        pet.setId(postPetResponse.extract().jsonPath().getInt("id"));

        ValidatableResponse getPetResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .pathParam("petId", pet.getId())
                .when()
                .get("/api/pets/{petId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Pet petFromGetResponse = getPetResponse.extract().as(Pet.class);

        Visit visit = VisitController.generateNewRandomVisit(petFromGetResponse);

        ValidatableResponse postVisitResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(visit)
                .when().log().all()
                .post("/api/visits").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        visit.setId(postVisitResponse.extract().jsonPath().getInt("id"));

        ValidatableResponse getVisitResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .pathParam("visitId", visit.getId())
                .when()
                .get("/api/visits/{visitId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Visit visitFromGetResponse = getVisitResponse.extract().as(Visit.class);

        assertThat(visitFromGetResponse, is(visit));

    }








    //Examples
    @Test
    public void postOwnerTest(){

        //serializare dintr-un map
        HashMap<String, String> owner = new HashMap<>();
        owner.put("id", null);
        owner.put("firstName", "George");
        owner.put("lastName", "Popescu");
        owner.put("address", "Tineretului 10");
        owner.put("city", "Bucharest");
        owner.put("telephone", "7788996634");

        ValidatableResponse response = given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .contentType(ContentType.JSON)
                .body(owner).log().all()
                .when()
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Integer ownerId = response.extract().jsonPath().getInt("id");

        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .pathParam("ownerId",ownerId)
                .when()
                .get("/api/owners/{ownerId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(ownerId));

    }

    @Test
    public void postOwnerTestWithObject(){
//        Faker faker = new Faker();
//        Owner owner = new Owner(faker.name().lastName(),
//                faker.name().firstName(),
//                faker.address().streetAddress(),
//                faker.address().city(),
//                faker.number().digits(10));

        Owner owner = OwnerController.generateNewRandomOwner();

        ValidatableResponse response = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(owner).log().all()
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        owner.setId(response.extract().jsonPath().getInt("id"));

        ValidatableResponse getResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .pathParam("ownerId", owner.getId())
                .when()
                .get("/api/owners/{ownerId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Owner ownerFromGetResponse = getResponse.extract().as(Owner.class); //verificam daca cei doi owneri sunt egali

        assertThat(ownerFromGetResponse, is(owner)); //verificam ca toate field urile corespund


    }

    @Test
    public void putOwnerTest(){
        Faker faker = new Faker();
        Owner owner = OwnerController.generateNewRandomOwner();

        ValidatableResponse postResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(owner)
                .when().log().all()
                .post("/api/owners")
                .then()
                .statusCode(HttpStatus.SC_CREATED); //call de post cand am creat owner initial

        owner.setId(postResponse.extract().jsonPath().getInt("id")); // am luat id ul
        owner.setAddress(faker.address().streetAddress()); //i-am modificat datele
        owner.setCity(faker.address().city());
        owner.setTelephone(faker.number().digits(10));

        given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .pathParam("ownerId", owner.getId())
                .body(owner).log().all()
                .put("/api/owners/{ownerId}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT); //call de put

        ValidatableResponse getResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .pathParam("ownerId", owner.getId())
                .get("/api/owners/{ownerId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK); //urmeaza sa comparam owner de la put cu owner primit pe get

        Owner actualOwner = getResponse.extract().as(Owner.class); // aici am extras owner ul venit pe get

        assertThat(actualOwner, is(owner));


    }

}
