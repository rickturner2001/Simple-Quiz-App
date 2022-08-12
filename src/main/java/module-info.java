module com.example.questionsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.questionsapp to javafx.fxml;
    exports com.example.questionsapp;
}