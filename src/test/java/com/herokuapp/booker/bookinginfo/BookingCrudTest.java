package com.herokuapp.booker.bookinginfo;

import com.herokuapp.booker.utils.TestUtils;
import com.herokuapp.booker.testbase.TestBase;
import com.herokuapp.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class BookingCrudTest extends TestBase {
    static String firstname = TestUtils.getRandomText();
    static String lastname = TestUtils.getRandomText();
    static int totalprice = 500;
    static boolean depositpaid = true;
    static String additionalneeds = "Bed and Breakfast";
    static List<Integer> bookingIdsBeforePost;
    static List<Integer> bookingIdsAfterPost;
    static int bookingid;

    @Steps
    BookingSteps bookingSteps;

    @Title("Extracting a List of Ids before creating new record")
    @Test
    public void test001() {

        bookingIdsBeforePost = SerenityRest.given()
                .when()
                .get()
                .then()
                .extract()
                .path("bookingid");
        System.out.println("Old List of ID's are :" + bookingIdsBeforePost);
        System.out.println("********************************************");
    }

    @Title("Creating a new booking using POST method")
    @Test
    public void test002() {

        HashMap<String, Object> booking = new HashMap<>();
        booking.put("checkin", "2022-01-01");
        booking.put("checkout", "2022-02-01");
        ValidatableResponse response = bookingSteps.createNewBooking(firstname, lastname, totalprice, depositpaid, booking, additionalneeds);
       response.statusCode(200)
                .log().all();
    }


    @Title("Extracting ID and Finding the new booking by id to verify if it exists in the application")
    @Test
    public void test003() {

        bookingIdsAfterPost = SerenityRest.given()
                .when()
                .get()
                .then()
                .extract()
                .path("bookingid");

        System.out.println("Old List of ID's are :" + bookingIdsBeforePost);
        System.out.println("New List of ID's are :" + bookingIdsAfterPost);
        System.out.println("********************************************");


        bookingIdsAfterPost.removeAll(bookingIdsBeforePost);

        bookingid = (bookingIdsAfterPost.get(0));
        System.out.println("The newly generated id is: " + bookingid);
        System.out.println("********************************************");


        ValidatableResponse response = bookingSteps.findNewRecordById(bookingid);
        response.statusCode(200)
                .log().all();
        System.out.println(bookingid);
    }

    @Title("Updating the newly created record with ID and verifying it by extracting id " +
            "with updated firstname as query parameter")
    @Test
    public void test004() {

        firstname = firstname + "updated";
        HashMap<String, Object> booking = new HashMap<>();
        booking.put("checkin", "2022-01-01");
        booking.put("checkout", "2022-02-01");
        ValidatableResponse response = bookingSteps.updateBookingRecordById
                (firstname, lastname, totalprice, depositpaid, booking, additionalneeds, bookingid);

        response.statusCode(200)
                .log().all();


        List<Integer> id = bookingSteps.findSingleBookingRecordByFirstName(firstname, bookingid);
        System.out.println("Actual id is : " + id.get(0));
        System.out.println("Expected id is : " + bookingid);
        Assert.assertThat(id.get(0), equalTo(bookingid));
    }

    @Title("Delete the newly created record with ID")
    @Test
    public void test005(){

      bookingSteps.deleteBooking(bookingid);

      ValidatableResponse response = bookingSteps.findNewRecordById(bookingid);

      response.statusCode(404)
              .log().all();
    }
}




