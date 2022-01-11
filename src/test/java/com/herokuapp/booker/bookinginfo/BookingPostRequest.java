package com.herokuapp.booker.bookinginfo;

import com.herokuapp.booker.utils.TestUtils;
import com.herokuapp.booker.model.BookingPojo;
import com.herokuapp.booker.testbase.TestBase;
import com.herokuapp.booker.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

@RunWith(SerenityRunner.class)
public class BookingPostRequest extends TestBase {

    @Title("Creating a new booking in the API")
    @Test
    public void createANewBooking(){
        HashMap<String , Object> booking = new HashMap<>();
        booking.put("checkin", "2022-01-01");
        booking.put("checkout", "2022-02-01");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(TestUtils.getRandomValue());
        bookingPojo.setLastname(TestUtils.getRandomValue());
        bookingPojo.setTotalprice(200);
        bookingPojo.setDepositpaid(false);
        bookingPojo.setBookingdates(booking);
        bookingPojo.setAdditionalneeds("Access to Swimming");

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .body(bookingPojo)
                .when()
                .post()
                .then()
                .statusCode(200).log().all();
    }

}
