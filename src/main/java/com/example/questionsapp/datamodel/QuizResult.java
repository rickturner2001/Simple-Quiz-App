package com.example.questionsapp.datamodel;

public class QuizResult {

    private int correctAnswers;
    private int totalQuestions;
    private String quizDate;

    public QuizResult(int correctAnswers, int totalQuestions, String quizDate){
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.quizDate = quizDate;
    }

    @Override
    public String toString() {
        return quizDate;
    }
//    Setters and Getters

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public String getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(String quizDate) {
        this.quizDate = quizDate;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
