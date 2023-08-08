package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

/**
 * Created by Jay
 */
public class ProductSteps {

    @Step("Creating Product with name : {0}, type {1}, price{2}, shipping {3}, upc {4} , description {5}, manufacturer {6}, model{7}")
    public ValidatableResponse createProduct(String name, String type, double price, int shipping, String upc, String description,
                                             String manufacturer, String model ) {
        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, shipping, upc, description, manufacturer, model);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .when()
                .body(productPojo)
                .post(EndPoints.CREATE_PRODUCT)
                .then();
    }

    @Step("Getting the product information with productId : {0}")
    public ValidatableResponse getProductInfoById(int productId ) {
        return SerenityRest.given()
                .pathParam("id", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then()
                .statusCode(200)
                .log().all();
    }
    @Step("Updating product information with name:{0}")
    public ValidatableResponse updateProduct(int productId,String name) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParam("id",productId)
                .when()
                .body(productPojo)
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Deleting product information with productId : {0}")
    public ValidatableResponse deleteProduct(int productId) {
        return SerenityRest.given()
                .pathParam("id", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }

}
