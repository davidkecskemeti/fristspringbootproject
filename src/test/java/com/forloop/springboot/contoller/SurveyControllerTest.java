package com.forloop.springboot.contoller;

import com.forloop.springboot.Application;
import com.forloop.springboot.model.Question;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void testJsonAssert() {
        try {
            JSONAssert.assertEquals("{id:1}", "{id:1;name:Ranga}", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws JSONException {
        String url = "http://localhost:" + port + "/surveys/Survey1/questions/Question1";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        //Accept: applicaton/json
        String ourput = testRestTemplate.getForObject(url, String.class);
        //HttpEntity - headers
        System.out.println("PORT=" + port);
        System.out.println("Response:" + ourput);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("Response body:" + responseEntity.getBody());
//        assertTrue(responseEntity.getBody().contains("\"id\":\"Question1\""));
        String excepted = "{id:Question1}";
        JSONAssert.assertEquals(excepted, responseEntity.getBody(), false);
    }

    @Test
    public void addQuesiton() throws JSONException {
        String url = "http://localhost:" + port + "/surveys/Survey1/questions";

        TestRestTemplate testRestTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Question question = new Question("DOESNOTMATTER", "Largest Country in the World", "Russia", Arrays.asList("India", "Russia", "United States", "China"));

        HttpEntity entity = new HttpEntity<Question>(question, headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String actual = responseEntity.getHeaders().get(HttpHeaders.LOCATION).get(0);

        System.out.println(actual);
        assertTrue(actual.contains("/surveys/Survey1/questions/"));
    }

}