package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(SerenityRunner.class)
public class StoreTest extends TestBase {
    static int storeid;
    static String name = "Prime" + TestUtils.getRandomValue();

    static String UpdatedName = "Updated " + TestUtils.getRandomValue();
    static String type = "API Testing";
    static String address = "London";
    static String address2 = "UK";
    static String city = "Harrow";
    static String state = "Abc";
    static String zip = "B420052";

    @Steps
    StoreSteps storeSteps;

    @Title("This will create a new store")
    @Test
    public void test001() {
        ValidatableResponse response = storeSteps.createStore(name, type, address, address2, city, state, zip);
        response.log().all();
        storeid = response.extract().path("id");
    }

    @Title("This will get information by Store Name")
    @Test
    public void test002() {
        ArrayList<?> storeMap = storeSteps.getStoreInformationByStoreName(name);
    }

    @Title("Update store information")
    @Test
    public void test003() {
        ValidatableResponse response = storeSteps.updateStoreInformation(UpdatedName, type, address, address2, city, state, zip, storeid);
        Assert.assertEquals(UpdatedName, response.extract().path("name"));
    }

    @Title("This will get info by storeID")
    @Test
    public void test004() {
        ValidatableResponse response = storeSteps.getStoreId(storeid);
        response.log().all();
    }

    @Title("This will Delete the store information")
    @Test
    public void test005() {
        storeSteps.deleteStoreId(storeid)
                .statusCode(200);
        storeSteps.getStoreId(storeid)
                .statusCode(404);
    }


}
