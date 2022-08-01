package com.endava.petclinic;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class PetClinicTest {

    //Homework exercise 5
    @Test
    public void createVisitTest(){
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"date\": \"2022/08/01\",\n" +
                        "  \"description\": \"vaccin\",\n" +
                        "  \"id\": null,\n" +
                        "  \"pet\": {\n" +
                        "    \"birthDate\": \"2022/07/07\",\n" +
                        "    \"id\": 82,\n" +
                        "    \"name\": \"Wiki\",\n" +
                        "    \"owner\": {\n" +
                        "      \"address\": \"2387 S. Fair Way\",\n" +
                        "      \"city\": \"Madison\",\n" +
                        "      \"firstName\": \"Peter\",\n" +
                        "      \"id\": 0,\n" +
                        "      \"lastName\": \"McTavish\",\n" +
                        "      \"pets\": [\n" +
                        "        null\n" +
                        "      ],\n" +
                        "      \"telephone\": \"6085552765\"\n" +
                        "    },\n" +
                        "    \"type\": {\n" +
                        "      \"id\": 2,\n" +
                        "      \"name\": \"dog\"\n" +
                        "    },\n" +
                        "    \"visits\": [\n" +
                        "      null\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}")
                .port(80)
                .when().log().all()
                .post("/api/visits").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    //Homework exercise 4
    @Test
    public void getPetListTest(){
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .when() //.log().all()
                .get("/api/pets")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    //Homework exercise 3
    @Test
    public void addPetTest(){
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"birthDate\": \"2022/07/07\",\n" +
                        "  \"id\": null,\n" +
                        "  \"name\": \"Wiki\",\n" +
                        "  \"owner\": {\n" +
                        "    \"address\": \"2387 S. Fair Way\",\n" +
                        "    \"city\": \"Madison\",\n" +
                        "    \"firstName\": \"Peter\",\n" +
                        "    \"id\": 5,\n" +
                        "    \"lastName\": \"McTavish\",\n" +
                        "    \"pets\": [\n" +
                        "      null\n" +
                        "    ],\n" +
                        "    \"telephone\": \"6085552765\"\n" +
                        "  },\n" +
                        "  \"type\": {\n" +
                        "    \"id\": 2,\n" +
                        "    \"name\": \"dog\"\n" +
                        "  }\n" +
                        "}")
                .port(80)
                .when().log().all()
                .post("/api/pets").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }


    //examples
    @Test
    public void getOwnerById(){
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
                .pathParam("ownerId",1)
                .when() //.log().all()
                .get("/api/owners/{ownerId}")
                .prettyPeek() //afisare dupa ce se face request
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(1))
                .body("firstName", is("George")) //verifica daca firstName este George
                .body("firstName", containsString("org")) //verif daca contine org
                .body("lastName", startsWith("Fr"))
                .body("city", equalToIgnoringCase("MadiSon")) //ignora daca are litere mici sau mari
                .body("telephone", hasLength(10));
    }

    @Test
    public void postOwnersTest(){
        ValidatableResponse response = given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .contentType(ContentType.JSON)  //sa stie cum sa interpreteze
                .body("{\n" +
                        "  \"address\": \"Unirii 10\",\n" +
                        "  \"city\": \"Buzau\",\n" +
                        "  \"firstName\": \"Madalina\",\n" +
                        "  \"id\": null,\n" +
                        "  \"lastName\": \"Neagu\",\n" +
                        "  \"telephone\": \"0177666555\"\n" +
                        "}")
                .port(80)
                .when().log().all()
                .post("/api/owners").prettyPeek() //primeste endpoint ca parametru
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
    public void test(){
        given().baseUri("http://api.petclinic.mywire.org/")
                .basePath("/petclinic")
                .port(80)
//                .pathParam("ownerId",1)
                .when() //.log().all()
                .get("/api/pets")
                .prettyPeek() //afisare dupa ce se face request
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
