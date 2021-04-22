package com.klayrocha.ifood.cadastro;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.Approvals;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.klayrocha.ifood.cadastro.model.Restaurant;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifeCycleManager.class)
public class RestaurantResourceTest {

    @Test
    @DataSet("restaurant-scene.yml")
    public void testfindRestaurants() {
        String result = given()
          .when().get("/restaurants")
          .then()
             .statusCode(200)
             .extract().asString();
        
        Approvals.verifyJson(result);
    }
    
    private RequestSpecification given() {
    	return RestAssured.given().contentType(ContentType.JSON);
    }

    
    @Test
    @DataSet("restaurant-scene.yml")
    public void testUpdateRestaurants() {
    	
    	Restaurant dto = new Restaurant();
    	dto.name = "newName";
    	
    	Long parameterValue = 123L;
    	
        given()
          .when().pathParam("id", parameterValue)
          .body(dto)
          .when().put("/restaurants/{id}")
          .then()
             .statusCode(Status.NO_CONTENT.getStatusCode())
             .extract().asString();
        
        Restaurant findById = Restaurant.findById(parameterValue);
        
        Assert.assertEquals(dto.name, findById.name);
    }

}