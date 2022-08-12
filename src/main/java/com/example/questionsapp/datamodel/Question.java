package com.example.questionsapp.datamodel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
    private JSONArray incorrectAnswers;
    private String correctAnswer;
    private JSONArray tags;
    private String question;

    private ArrayList<String> questions;

    public Question(String question, JSONArray incorrectAnswers, String correctAnswer,
                    JSONArray tags) {
        this.question = question;
        this.incorrectAnswers = incorrectAnswers;
        this.correctAnswer = correctAnswer;
        this.tags = tags;
        this.questions = new ArrayList<>();
        incorrectAnswers.forEach((answer) -> {
            questions.add(answer.toString());
        });
        questions.add(correctAnswer);
        Collections.shuffle(questions);
    }

//    Setters and Getters

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public JSONArray getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(JSONArray incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public JSONArray getTags() {
        return tags;
    }

    public void setTags(JSONArray tags) {
        this.tags = tags;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
