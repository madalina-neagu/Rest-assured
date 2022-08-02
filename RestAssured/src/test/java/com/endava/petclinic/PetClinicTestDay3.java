package com.endava.petclinic;

import com.endava.petclinic.controllers.OwnerController;
import com.endava.petclinic.controllers.PetControllerv2;
import com.endava.petclinic.controllers.PetTypeController;
import com.endava.petclinic.models.Owner;
import com.endava.petclinic.models.Pet;
import com.endava.petclinic.models.PetType;
import com.endava.petclinic.models.User;
import com.endava.petclinic.utils.EnvReader;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PetClinicTestDay3 {

    //post on Twitter wall
    @Test
    public void postOnTwitter(){
        given().baseUri("https://api.twitter.com")
                .basePath("/1.1/statuses")
                .auth()
                .oauth(EnvReader.getApiKey(), EnvReader.getApiKeySecret(), EnvReader.getAccessToken(), EnvReader.getAccessTokenSecret())
                .queryParam("status", "Madalina Neagu").log().all()
                .header("Content-Type", "application/json")
                .when()
                .post("/update.json")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    //get all tweets from wall
    @Test
    public void getAllTweets(){
        given().baseUri("https://api.twitter.com")
                .basePath("/1.1/statuses")
                .auth()
                .oauth(EnvReader.getApiKey(), EnvReader.getApiKeySecret(), EnvReader.getAccessToken(), EnvReader.getAccessTokenSecret())
                .queryParam("screen_name","j3r3my84")
                .header("Content-Type", "application/json")
                .when()
                .get("user_timeline.json").prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();
    }




    //examples Day 3
    @Test
    public void createOwnerSecured(){
        Faker faker = new Faker();
        User user = new User(faker.name().username(), faker.internet().password(),"OWNER_ADMIN", "VET_ADMIN");

        given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPortSecured())
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(user)
                .post("/api/users").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Owner owner = OwnerController.generateNewRandomOwner();

        ValidatableResponse response = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPortSecured())
                .auth()
                .preemptive()
                .basic(user.getUsername(), user.getPassword())
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(owner).log().all()
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        owner.setId(response.extract().jsonPath().getInt("id"));

        ValidatableResponse getResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPortSecured())
                .auth()
                .preemptive()
                .basic(user.getUsername(), user.getPassword())
                .pathParam("ownerId", owner.getId())
                .when()
                .get("/api/owners/{ownerId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Owner ownerFromGetResponse = getResponse.extract().as(Owner.class);

        assertThat(ownerFromGetResponse, is(owner));

    }



    //post pe instanta securizata
    @Test
    public void postOwnerTestWithObject(){

        Owner owner = OwnerController.generateNewRandomOwner();

        ValidatableResponse response = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPortSecured())
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(owner).log().all()
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        owner.setId(response.extract().jsonPath().getInt("id"));

        ValidatableResponse getResponse  = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPortSecured())
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .pathParam("ownerId", owner.getId())
                .when()
                .get("/api/owners/{ownerId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Owner ownerFromGetResponse = getResponse.extract().as(Owner.class); //verificam daca cei doi owneri sunt egali

        assertThat(ownerFromGetResponse, is(owner)); //verificam ca toate field urile corespund


    }




//Homework v2
    @Test
    public void postPet(){
        Pet pet = PetControllerv2.generateNewRandomPet();

        ValidatableResponse creteOwnerResponse = given().baseUri(EnvReader.getBaseURI())//returneaza owner complet
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(pet.getOwner()).log().all()
                .post("/api/owners").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        pet.getOwner().setId(creteOwnerResponse.extract().jsonPath().getInt("id"));

        PetType type = PetTypeController.generateNewPetType();

        ValidatableResponse createPetTypeResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(type)
                .post("/api/pettypes").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        type.setId(createPetTypeResponse.extract().jsonPath().getInt("id"));
        pet.setType(type);

        ValidatableResponse createPetResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(pet)
                .when().log().all()
                .post("/api/pets").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        pet.setId(createPetResponse.extract().jsonPath().getInt("id"));

        ValidatableResponse getPetResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .pathParam("petId", pet.getId())
                .when()
                .get("/api/pets/{petId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Pet actual = getPetResponse.extract().as(Pet.class);

        assertThat(actual, is(pet));
    }

    @Test
    public void createPetWithOwnerAndPetTypeTest(){
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

        PetType petType = new PetType(new Faker().animal().name());

        ValidatableResponse postPetTypeResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .when().log().all()
                .body(petType).log().all()
                .post("/api/pettypes").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        petType.setId(postPetTypeResponse.extract().jsonPath().getInt("id"));

        Pet pet = PetControllerv2.generateNewRandomPet();
        pet.setOwner(owner);
        pet.setType(petType);

        ValidatableResponse createPetResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .contentType(ContentType.JSON)
                .body(pet)
                .when().log().all()
                .post("/api/pets").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        pet.setId(createPetResponse.extract().jsonPath().getInt("id"));

        ValidatableResponse getPetResponse = given().baseUri(EnvReader.getBaseURI())
                .basePath(EnvReader.getBasePath())
                .port(EnvReader.getPort())
                .pathParam("petId", pet.getId())
                .when()
                .get("/api/pets/{petId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Pet actual = getPetResponse.extract().as(Pet.class);

        assertThat(actual, is(pet));
    }
}
