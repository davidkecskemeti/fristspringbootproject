package com.forloop.springboot.contoller;

import com.forloop.springboot.model.Question;
import com.forloop.springboot.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * GET - Retrieve details for resource => /surveys/{surveyId}/questions"
 * POST - Create a new resouce => /surveys/{surveyId}/questions"
 * PUT Update an existing resource => /surveys/{surveyId}/questions/{questionId}"
 * PATCH - Update a part of a resouce => patch a detail like the description
 * DELETE - Delete a resource => surveys/{surveyId}/questions/{questionId}"
 */
@RestController
public class SurveyContoller {

    @Autowired
    private SurveyService surveyService;

    // GET "/surveys/{surveyId}/questions"
    @GetMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveQuestionsForSurvey(@PathVariable String surveyId) {
        return surveyService.retrieveQuestions(surveyId);
    }

    // GET "/surveys/{surveyId}/questions/{questionId}"
    @GetMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveDetailsForQuestions(@PathVariable String surveyId, @PathVariable String questionId) {
        return surveyService.retrieveQuestion(surveyId, questionId);
    }

    // POST - Create a new resouce => /surveys/{surveyId}/questions"
    @PostMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<Void> addQuestionsToSurvey(@PathVariable String surveyId, @RequestBody Question newQuestion) {
        /**
         *
         * id	"Question100"
         * description	"Some desc"
         * correctAnswer	"corect answe"
         * options
         * 0	"option1"
         * 1	"Russia"
         * 2	"United States"
         * 3	"China"
         */
        Question question = surveyService.addQuestion(surveyId, newQuestion);

        if(question==null)return ResponseEntity.noContent().build();

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(question.getId())
                .toUri();
       return ResponseEntity.created(uri).build();
    }
}
