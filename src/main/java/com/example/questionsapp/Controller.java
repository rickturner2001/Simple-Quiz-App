package com.example.questionsapp;

import com.example.questionsapp.datamodel.Question;
import com.example.questionsapp.datamodel.Quiz;
import com.example.questionsapp.datamodel.QuizResult;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private final ArrayList<QuizResult> sessionResults = new ArrayList<>();
    private final List<QuizResult> quizItems = new ArrayList<>();
    @FXML
    private ListView<QuizResult> quizListView;
    @FXML
    private Slider quizQuestions;
    @FXML
    private ChoiceBox quizDifficulty;
    @FXML
    private ScrollPane questionsContainer;
    @FXML
    private Label selectedResults;
    private HashMap<Integer, String> userInputs = new HashMap<>();
    private ArrayList<String> correctAnswers = new ArrayList<>();

    public void generateQuizOnClick() throws IOException {

        VBox outerContainer = new VBox();

        String difficulty = quizDifficulty.getValue().toString().toLowerCase();
        int nQuestions = (int) quizQuestions.getValue();


        Quiz quiz = new Quiz(difficulty, nQuestions);

        ArrayList<Question> questionsData = quiz.getQuestionsData();

        for (int i = 0; i < questionsData.size(); i++) {
            Question question = questionsData.get(i);

            correctAnswers.add(question.getCorrectAnswer());

            VBox questionContainer = new VBox();
            questionContainer.setPadding(new Insets(20, 20, 20, 20));
            questionContainer.setSpacing(20);

            Label questionPrompt = new Label();

            questionPrompt.setAlignment(Pos.CENTER);
            questionPrompt.setText(question.getQuestion());

            ToggleGroup choices = new ToggleGroup();
            VBox choicesContainer = new VBox();

            for (String quest : question.getQuestions()) {
                RadioButton radio = new RadioButton(quest);
                radio.setToggleGroup(choices);
                int finalI = i;
                radio.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        userInputs.put(finalI, quest);
                    }
                });
                choicesContainer.getChildren().add(radio);
            }

            questionContainer.getChildren().add(questionPrompt);
            questionContainer.getChildren().add(choicesContainer);
            outerContainer.getChildren().add(questionContainer);
            questionsContainer.setContent(outerContainer);
        }
    }

    public void confirmQuiz() {
            int correct = 0;
            for (int i = 0; i < userInputs.values().size(); i++) {

                if (correctAnswers.get(i) == userInputs.values().toArray()[i]) {
                    correct++;
                }

            }

            quizItems.add(new QuizResult(correct, correctAnswers.size(), LocalDateTime.now().
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            quizListView.getItems().setAll(quizItems);
            correctAnswers = new ArrayList<>();
            userInputs = new HashMap<>();
            questionsContainer.setContent(new HBox());
        }


    public void initialize() {
        quizListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        quizListView.setOnMouseClicked((mouseEvent -> {
//            Get everything between quotes
            Pattern pattern = Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1");
            Matcher matcher = pattern.matcher(mouseEvent.getTarget().toString());
            String selected = "No results available";
            if (matcher.find()) {
//                Removing either " or '
                selected = matcher.group(0).substring(1, matcher.group(0).length() - 1);
            }

            String finalSelected = selected;
            quizItems.forEach((item) -> {
                if (Objects.equals(item.getQuizDate(), finalSelected)) {

                    selectedResults.setText(String.format("%d/%d", item.getCorrectAnswers(),
                            item.getTotalQuestions()));
                }
            });
        }));
    }
}