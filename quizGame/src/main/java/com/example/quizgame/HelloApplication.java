package com.example.quizgame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//video details
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class HelloApplication extends Application {
    private int currentQuestionIndex = 0;

    private int correctAnswersCount = 0;
            //media variables
    private StackPane root;
    private MediaPlayer mediaPlayer;
    private Timeline videoTimeline;


    private static final String[] questions = {
            "What is the capital city of Lesotho?",
            "What is the highest point in Lesotho?",
            "What is the official language of Lesotho?",
            "Which famous landmark is located in Lesotho?",
            "What is the traditional Basotho blanket called?"
    };

    private static final String[][] options = {
            {"Maseru", "Johannesburg", "Cape Town", "Pretoria"},
            {"Mount Everest", "Kilimanjaro", "Thabana Ntlenyana", "Matterhorn"},
            {"English", "Sesotho", "French", "Zulu"},
            {"Victoria Falls", "Sani Pass", "Table Mountain", "Sahara Desert"},
            {"Motlatsi", "Seanamarena", "Mokorotlo", "Tsepiso"}
    };

    private static final String[] imagePaths = {
            "maseru.jpg",
            "thabana_ntlenyana.jpg",
            "lesotho_flag.jpg",
            "sani_pass.jpg",
            "basotho_blanket.jpg"
    };

    private Label questionLabel;
    private ImageView imageView;
    private VBox optionsBox;
    private Button nextButton;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root"); // Apply a class named "root" to the root container


        questionLabel = new Label();
        root.getChildren().add(questionLabel);

        imageView = new ImageView();
        root.getChildren().add(imageView);

        optionsBox = new VBox(5);
        root.getChildren().add(optionsBox);

        nextButton = new Button("Next");
        nextButton.setOnAction(e -> nextQuestion());
        root.getChildren().add(nextButton);
        nextButton.getStyleClass().add("button"); // Apply a class named "button" to nextButton
        nextButton.setId("nextButton"); // Set the ID of nextButton to "nextButton"


        Scene scene = new Scene(root, 400, 500);
        scene.getStylesheets().add(getClass().getResource("/com/example/quizgame/styles.css").toExternalForm());
        primaryStage.setTitle("Lesotho Trivia Game");
        primaryStage.setScene(scene);
        primaryStage.show();


        // Media details
        StackPane mediaRoot = new StackPane();
        mediaRoot.setPadding(new Insets(20));

        String videoPath = getClass().getResource("/video.mp4").toExternalForm();
        Media media = new Media(videoPath);

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        // Create MediaView to display the video
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(400);
        mediaView.setFitHeight(600);
        mediaRoot.getChildren().add(mediaView);

        Scene mediaScene = new Scene(mediaRoot, 400, 500);
        Stage mediaStage = new Stage();
        mediaStage.setScene(mediaScene);
        mediaStage.setTitle("Video Player");
        mediaStage.show();
        // Set up timeline to stop video after 10 seconds
        videoTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            mediaStage.close(); // Close the video stage
            mediaPlayer.stop(); // Stop the media player
            displayQuestion(); // Display the questions
        }));
        videoTimeline.setCycleCount(1); // Only run once
        videoTimeline.play();
        /*
        StackPane mediaRoot = new StackPane();
        mediaRoot.setPadding(new Insets(20));

        String videoPath = getClass().getResource("/video.mp4").toExternalForm();
        Media media = new Media(videoPath);

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        // Create MediaView to display the video
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(800);
        mediaView.setFitHeight(600);
        mediaRoot.getChildren().add(mediaView);

        Scene mediaScene = new Scene(mediaRoot, 800, 600);
        Stage mediaStage = new Stage();
        mediaStage.setScene(mediaScene);
        mediaStage.setTitle("Video Player");
        mediaStage.show();

        // Set up timeline to stop video after 10 seconds
        videoTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            mediaStage.close(); // Close the video stage
            mediaPlayer.stop(); // Stop the media player
            displayQuestion(); // Display the questions
        }));
        videoTimeline.setCycleCount(1); // Only run once
        videoTimeline.play();
         */

        // Event handler to switch to displaying questions after video finishes
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaStage.close(); // Close the video stage
            displayQuestion(); // Display the questions
        });
    }


    private void displayQuestion() {
        // Display the question
        questionLabel.setText(questions[currentQuestionIndex]);

        // Display the image
        Image image = new Image(getClass().getResourceAsStream("/" + imagePaths[currentQuestionIndex]));
        imageView.setImage(image);

        // Display the options
        optionsBox.getChildren().clear();
        for (String option : options[currentQuestionIndex]) {
            Button optionButton = new Button(option);
            optionButton.setOnAction(e -> handleAnswer(optionButton.getText()));
            optionsBox.getChildren().add(optionButton);
        }
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            displayQuestion();
        } else {
            endGame();
        }
    }

    private void handleAnswer(String selectedOption) {
        // Compare selected option with all options
        for (String option : options[currentQuestionIndex]) {
            if (selectedOption.equals(option)) {
                correctAnswersCount++;
                break; // No need to continue checking once a correct answer is found
            }
        }

        // Automatically move to the next question
        nextQuestion();
    }

    private void endGame() {
        // Display the final score
        Label scoreLabel = new Label("Your final score: " +correctAnswersCount+ "/" + questions.length);
        optionsBox.getChildren().clear();
        optionsBox.getChildren().add(scoreLabel);
        nextButton.setDisable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
