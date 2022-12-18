package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateProjectTest {
    /*
     *  given() --> configuration: headers/ params / auth / body
     *  when() --> method : url
     *  then() --> response: headers/ body / response code / msg code / etc
     *             verification
     *             extract
     *  log
     * */
    @Test
    public void verifyCreateProject(){
        JSONObject body= new JSONObject();
        body.put("Content","FinalProyect");

        Response response=given()
                .auth()
                .preemptive()
                .basic("final@bootcamp.com","12345")
                .body(body.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/projects.json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("FinalProyect"));

        int idProj = response.then().extract().path("Id");

        body.put("Content","FinalProyectUpdate");
        body.put("Icon",5);
        response=given()
                .auth()
                .preemptive()
                .basic("final@bootcamp.com","12345")
                .body(body.toString())
                .log().all()
                .when()
                .put("https://todo.ly/api/projects/"+idProj+".json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Deleted",equalTo(false))
                .body("Icon",equalTo(5))
                .body("Content",equalTo("FinalProyectUpdate"));


        response=given()
                .auth()
                .preemptive()
                .basic("final@bootcamp.com","12345")
                .body(body.toString())
                .log().all()
                .when()
                .delete("https://todo.ly/api/projects/"+idProj+".json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Icon",equalTo(5))
                .body("Deleted",equalTo(true))
                .body("Content",equalTo("FinalProyectUpdate"));


    }
}
