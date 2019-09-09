package com.forloop.springboot.contoller;

import com.forloop.springboot.model.Question;
import com.forloop.springboot.service.SurveyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * GET - Retrieve details for resource => /surveys/{surveyId}/questions"
 * POST - Create a new resouce => /surveys/{surveyId}/questions"
 * PUT Update an existing resource => /surveys/{surveyId}/questions/{questionId}"
 * PATCH - Update a part of a resouce => patch a detail like the description
 * DELETE - Delete a resource => surveys/{surveyId}/questions/{questionId}"
 */
@RunWith(SpringRunner.class)
//Unit test vs Integration test if we unit test only we dont want to lunch the whole application
//AT integration test we launch the whole app

//For mock testing only the surveyng model we have tu launch only taht part of the applicaiton
@WebMvcTest(value = SurveyController.class)
public class SurveyControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    //Mock @Autowired
    @MockBean
    private SurveyService surveyService;

    @Test
    @WithMockUser(roles = "USER")
    public void testMethod() throws Exception {
        //Use this specific date

        //Except this response

        //When(surveyService.retreieveQuestion("Any","Any",)).ReturnSomeMockData
        //Make a call service
        //Assert

        Question mockQuestion = new Question("Question1",
                "Largest",
                "Russia",
                Arrays.asList("India", "Russia", "United States", "China"));

        Mockito.when(surveyService.retrieveQuestion(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockQuestion);

        // GET "/surveys/{surveyId}/questions/{questionId}"
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/surveys/Survey1/questions/Question1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String excepted = "{id:Question1,description:Largest,correctAnswer:Russia}";
        JSONAssert.assertEquals(excepted, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "user1",password = "secret1",roles = "USER")
    public void createSurveyQuestion() throws Exception {
        Question mockQuestion = new Question("1",
                "Smallest number",
                "1",
                Arrays.asList("1", "2", "3", "4"));

        String questionJson = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":[\"1\"2\"3\"4\"]}";
        //suerveyService.addQuestion to respond back with mockQuestion
        //Send questionJSON as body to POST url /suerveys/Survey1/questions

        Mockito.when(surveyService.addQuestion(Mockito.anyString(), Mockito.any(Question.class)))
                .thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/surveys/Survey1/questions")
                .accept(MediaType.APPLICATION_JSON)
                .content(questionJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertEquals("http://localhost/suerveys/Survey1/questions/1",response.getHeader(HttpHeaders.LOCATION));


    }
}
