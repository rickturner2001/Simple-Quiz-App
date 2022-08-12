package com.example.questionsapp.datamodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.*;


public class Quiz {
    private String difficulty;
    private int nQuestions;
    private URL url;



    public Quiz(String difficulty, int nQuestions) throws MalformedURLException {
        this.nQuestions = nQuestions;
        this.difficulty = difficulty;
        this.url = new URL(String.format("https://the-trivia-api.com/api/questions?categories=sport_and_leisure,society_and_culture,science,music,history,geography,general_knowledge,food_and_drink,film_and_tv,arts_and_literature&limit=%d&region=US&difficulty=%s", this.nQuestions, this.difficulty));
    }

    private StringBuffer doRequest() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();

        BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        StringBuffer output = new StringBuffer();

        while((input = response.readLine()) != null){
            output.append(input);
        }
        response.close();
        connection.disconnect();
        return output;

    }

    public ArrayList<Question> getQuestionsData() throws IOException {

        ArrayList<Question> questions = new ArrayList<>();

        Quiz quiz = new Quiz(this.difficulty, this.nQuestions   );
        String quizData = quiz.doRequest().toString();
        JSONArray arr = new JSONArray(quizData);
        for(int i=0; i< arr.length();i++){
            JSONArray incorrectAnswers = JSONToArrayConvert(arr.getJSONObject(i), "incorrectAnswers");
            JSONArray tags = JSONToArrayConvert(arr.getJSONObject(i), "tags");
            String correctAnswer = arr.getJSONObject(i).getString("correctAnswer");
            String question = arr.getJSONObject(i).getString("question");


            questions.add(new Question(question, incorrectAnswers, correctAnswer, tags));
        }
        return questions;
    }


    private static JSONArray JSONToArrayConvert(JSONObject json, String key){
        return new JSONArray(json.getJSONArray(key));

    }


    public int getNQuestions() {
        return nQuestions;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public URL getUrl() {
        return url;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setNQuestions(int nQuestions) {
        this.nQuestions = nQuestions;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}

