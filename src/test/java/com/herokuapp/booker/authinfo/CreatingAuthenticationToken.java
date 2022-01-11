package com.herokuapp.booker.authinfo;

import com.herokuapp.booker.testbase.TestBaseAuth;
import com.herokuapp.booker.model.AuthPojo;
import com.herokuapp.booker.testbase.TestBaseAuth;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Test;

//@RunWith(SerenityRunner.class)
public class CreatingAuthenticationToken extends TestBaseAuth {

    @Title("Creating a new token to use for access to PUT and DELETE booking requests")
    @Test
    public void creatingNewToken(){
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername("admin");
        authPojo.setPassword("password123");
        String token = SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(authPojo)
                .when()
                .post()
                .then()
                .extract()
                .path("token");
        System.out.println(token);

    }






}
