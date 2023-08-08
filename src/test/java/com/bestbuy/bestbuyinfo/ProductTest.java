package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class ProductTest extends TestBase {
    static String name = "Duracell - AAA Batteries (8-Pack)";
    static String type = "HardGood";
    static double price = 21.99;
    static int shipping = 2;
    static String upc = "052334424008";
    static String description = "Compatible with select electronic devices; AAA size; DURALOCK Power Preserve technology; 8-pack";
    static String manufacturer = "Duracell";
    static String model = "HN2400C4B";

    static int productId;

    @Steps
    ProductSteps productSteps;

    @Title("This will create a new product")
    @Test
    public void test001() {
        ValidatableResponse response = productSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model);
        response.log().all().statusCode(201);
        productId = response.extract().path("id");
    }

    @Title("Verify if the product was created")
    @Test
    public void test002() {
        productSteps.getProductInfoById(productId);
    }


    @Title("update the product information and verify the updated product")
    @Test
    public void test003() {
        name = name + " updated";
        productSteps.updateProduct(productId,name).log().all().statusCode(200);

    }

    @Title("Delete the product and verify if the product is deleted")
    @Test
    public void test004() {
        productSteps.deleteProduct(productId)
                .statusCode(200);

        productSteps.getProductInfoById(productId)
                .statusCode(404);
    }
}
