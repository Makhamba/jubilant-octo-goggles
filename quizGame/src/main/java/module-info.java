module com.example.quizgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.quizgame to javafx.fxml;
    exports com.example.quizgame;
}