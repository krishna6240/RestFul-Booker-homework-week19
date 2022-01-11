package com.herokuapp.booker.testbase;

import com.herokuapp.booker.constants.Path;
import com.herokuapp.booker.utils.PropertyReader;
import com.herokuapp.booker.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class TestBaseAuth {

    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
        RestAssured.basePath = Path.AUTH;


    }
}
