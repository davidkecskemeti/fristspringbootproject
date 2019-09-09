package com.forloop.springboot.contoller;

import com.forloop.springboot.Application;
import com.forloop.springboot.model.Question;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerOrganisedTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void before() {
        headers.add("Authorization", createHttpAuthenticationHeaderValue("user1", "secret1"));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRetrieveSurveyQuestion() throws JSONException {

        HttpEntity entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(createURLWithPort("/surveys/Survey1/questions/Question1"),
                HttpMethod.GET, entity,
                String.class);
        System.out.println(responseEntity.getBody());
        String excepted = "{id:Question1}";
        JSONAssert.assertEquals(excepted, responseEntity.getBody(), false);
    }

    @Test
    public void addQuesiton() throws JSONException {

        Question question = new Question("DOESNOTMATTER",
                "Largest Country in the World",
                "Russia",
                Arrays.asList("India", "Russia", "United States", "China")
        );

        HttpEntity entity = new HttpEntity<>(question, headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(createURLWithPort("/surveys/Survey1/questions"),
                HttpMethod.POST, entity,
                String.class);

        String actual = responseEntity.getHeaders().get(HttpHeaders.LOCATION).get(0);
        assertTrue(actual.contains("/surveys/Survey1/questions/"));
    }

    private String createURLWithPort(String retrieveAllQuestions) {
        return "http://localhost:" + port + retrieveAllQuestions;
    }

    private String createHttpAuthenticationHeaderValue(String userId, String password) {
        //Basic authentication
//        userId, password, Basic auth
//        Authorization, Basic , Base64Encoding(userId+":"+password)
        String auth = userId + ":" + password;
        byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
        String headerValue = "Basic " + new String(encodedAuth);
        System.out.println(headerValue);
        return headerValue;
    }

}