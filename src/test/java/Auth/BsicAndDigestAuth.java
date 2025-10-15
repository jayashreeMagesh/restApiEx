package Auth;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class BsicAndDigestAuth {
    private final RequestSpecification requestSpecification = RestAssured.given()
            .baseUri("https://restful-booker.herokuapp.com")
            .filters(new RequestLoggingFilter(),new ResponseLoggingFilter(LogDetail.STATUS));

    @Test
    public void preemptiveAuthDelete(){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("bookingId",1);
        Response response = requestSpecification.auth().preemptive().basic("admin","password123").basePath("/booking/{bookingId}").pathParams(paramMap).delete();
        System.out.println(response.asString());
    }
    @Test
    public void challangeBasicAuth(){

        Response challangedBasicAuth = RestAssured.given()
                .baseUri("https://the-internet.herokuapp.com/")
                .basePath("basic_auth")
                .auth().basic("admin","admin").filter(new RequestLoggingFilter()).get();
        System.out.println(challangedBasicAuth.asString());
    }
    @Test
    public void challangeDigestAuth(){

        Response challangeDigestAuth = RestAssured.given()
                .baseUri("https://the-internet.herokuapp.com/")
                .basePath("digest_auth")
                .auth().digest("admin","admin").filter(new RequestLoggingFilter()).get();
        System.out.println(challangeDigestAuth.asString());
    }


}
