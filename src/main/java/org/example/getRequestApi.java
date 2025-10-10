package org.example;

import groovy.json.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class getRequestApi {
    WebDriver driver;

@Test
public void getRequest(){

        RequestSpecification requestSpecification = RestAssured.given();
        Response response = requestSpecification.baseUri("https://www.automationexercise.com").basePath("/api/productsList").contentType(ContentType.JSON).get();
        String response1 = response.asString();
        System.out.println(response1);


}

public Map<String,Object> getQueryParam(){
    Map<String,Object> QueryParam = new HashMap<>();
    QueryParam.put("firstname","Jim");
    QueryParam.put("lastname","Brown");
return QueryParam;
}

@Test
    public void getRequestwithParms() throws ParseException, IOException {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("bookingId",20);
    RequestSpecification requestSpecification = RestAssured.given();
    Response response = requestSpecification.baseUri("https://restful-booker.herokuapp.com").basePath("/booking/{bookingId}").pathParams(paramMap).get();
    String apiResponse = response.asString();
    String path = "C:\\Users\\jayas\\IntellijWorkSpace\\restApiEx\\src\\main\\resources\\Schema\\jsonSchema.json";

    JSONParser parser = new JSONParser();
    JSONObject jsonObject;
    jsonObject = (JSONObject) parser.parse(apiResponse);
    try
        (FileWriter file = new FileWriter(path)){
        file.write(jsonObject.toJSONString());
        System.out.println("wrote into file"+path);

    }catch(Exception e){
        e.printStackTrace();
    }


}
    @Test
    public void getRequestwithQuery(){

        RequestSpecification requestSpecification = RestAssured.given();
        Response response = requestSpecification.baseUri("https://restful-booker.herokuapp.com").basePath("/booking").queryParams(getQueryParam()).get();
        System.out.println(response.asString());


    }

}
