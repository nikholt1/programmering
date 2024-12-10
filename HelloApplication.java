package com.example.guifx;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import java.util.Timer;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        // Initialize the VBox and other UI elements
        VBox root = new VBox();
        initializeMainScene(root, new Text("INIT"));

        // Set the Scene and show the Stage
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.setTitle("DOBBY GUI");
        stage.setFullScreen(true);
        stage.show();
    }

    private void initializeMainScene(VBox root, Text newContent) {
        root.getChildren().clear();

        // Create buttons for the main scene
        Button button1 = new Button("TRAINER");
        Button button2 = new Button("MAC LOGGING");
        Button button3 = new Button("PACKET PER SECOND");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(button1, button2, button3);
        button1.setMinHeight(300);
        button2.setMinHeight(300);
        button3.setMinHeight(300);

        button1.setStyle("-fx-font-size: 30px;");
        button2.setStyle("-fx-font-size: 30px;");
        button3.setStyle("-fx-font-size: 30px;");

        // Make buttons grow and take available space
        HBox.setHgrow(button1, Priority.ALWAYS);
        HBox.setHgrow(button2, Priority.ALWAYS);
        HBox.setHgrow(button3, Priority.ALWAYS);
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        button3.setMaxWidth(Double.MAX_VALUE);

        // Set the layout and alignment for the buttons

        root.getChildren().clear();
        root.getChildren().add(buttonBox);
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.setPrefHeight(800);
        root.setStyle("-fx-background-color: #808080;");

        // Set the actions for the buttons
        button1.setOnAction(event -> changeSceneTrainer(root, new Text("SELECTED")));
        button2.setOnAction(event -> changeSceneMACLOG(root, new Text("MACLOG")));
        button3.setOnAction(event -> changeScenePPS(root, new Text("PACKET PER SECOND")));
    }

    private void changeSceneTrainer(VBox root, Text newContent) {
        // Clear the existing content
        root.getChildren().clear();

        // Add the new content
        root.getChildren().add(newContent);

        // Create new buttons for the trainer scene
        Button button1 = new Button("TRAINER");
        Button button2 = new Button("TRACKING");
        Button button3 = new Button("BACK");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(button1, button2, button3);
        button1.setMinHeight(300);
        button2.setMinHeight(300);
        button3.setMinHeight(300);
        button1.setStyle("-fx-font-size: 30px;");
        button2.setStyle("-fx-font-size: 30px;");
        button3.setStyle("-fx-font-size: 30px;");
        HBox.setHgrow(button1, Priority.ALWAYS);
        HBox.setHgrow(button2, Priority.ALWAYS);
        HBox.setHgrow(button3, Priority.ALWAYS);
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        button3.setMaxWidth(Double.MAX_VALUE);

        // Add the buttons to the root
        root.getChildren().add(buttonBox);
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.setPrefHeight(800);
        root.setStyle("-fx-background-color: #808080;");

        // Set actions for the new buttons
        button1.setOnAction(event -> changeSceneTRAIN(root, new Text("TRAINING")));  // Keeping the scene the same
        button2.setOnAction(event -> changeSceneTRACK(root, new Text("TRACKING")));
        button3.setOnAction(event -> initializeMainScene(root, new Text("MAIN")));
  // Go back to the main scene
    }

    private void changeSceneMACLOG(VBox root, Text newContent) {
        // Clear the existing content
        root.getChildren().clear();


        Button button1 = new Button("START");
        Button button2 = new Button("STOP");
        Button button3 = new Button("BACK");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(button1, button2, button3);
        button1.setMinHeight(300);
        button2.setMinHeight(300);
        button3.setMinHeight(300);
        button1.setStyle("-fx-font-size: 30px;");
        button2.setStyle("-fx-font-size: 30px;");
        button3.setStyle("-fx-font-size: 30px;");
        HBox.setHgrow(button1, Priority.ALWAYS);
        HBox.setHgrow(button2, Priority.ALWAYS);
        HBox.setHgrow(button3, Priority.ALWAYS);
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        button3.setMaxWidth(Double.MAX_VALUE);

        //Creating ListVew to display CSV
        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(400);
        String filePath = "C:\\Users\\Niklas Holt Läu\\Documents\\GUIFX\\src\\test2.csv";

        // Initial population of ListView
        List<String> csvData = readCSV(filePath);
        ObservableList<String> items = FXCollections.observableArrayList(csvData);
        listView.setItems(items);

        // Add the ListView and buttons to the layout
        VBox contentBox = new VBox(10, listView, buttonBox);
        contentBox.setAlignment(Pos.CENTER);

        root.getChildren().add(contentBox);
        root.setStyle("-fx-background-color: #808080;");

        Timer timer = new Timer(true); // Daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    List<String> updatedData = readCSV(filePath);
                    listView.setItems(FXCollections.observableArrayList(updatedData)); // Refresh the ListView
                    System.out.println("Data refreshed: " + updatedData); // Debug log
                });
            }
        }, 0, 1000); // Initial delay = 0 ms, refresh every 1000 ms (1 seconds)


        // Set actions for the new buttons
        button1.setOnAction(event -> {
            String command = "gnome-terminal -e 'sudo python DOBBYMacAndLog.py'";
            runPythonScript(command);
        });  // Keeping the scene the same
        button2.setOnAction(event -> changeSceneMACLOG(root, newContent));
        button3.setOnAction(event -> {
            timer.cancel();
            initializeMainScene(root, new Text("BACK"));
        });
        // Go back to the main scene
    }

    private void changeScenePPS(VBox root, Text newContent) {
        // Clear the existing content
        root.getChildren().clear();

        // Add the new content
        root.getChildren().add(newContent);

        // Create new buttons for the PPS scene
        Button button1 = new Button("START");
        Button button2 = new Button("STOP");
        Button button3 = new Button("BACK");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(button1, button2, button3);
        button1.setMinHeight(300);
        button2.setMinHeight(300);
        button3.setMinHeight(300);
        button1.setStyle("-fx-font-size: 30px;");
        button2.setStyle("-fx-font-size: 30px;");
        button3.setStyle("-fx-font-size: 30px;");
        HBox.setHgrow(button1, Priority.ALWAYS);
        HBox.setHgrow(button2, Priority.ALWAYS);
        HBox.setHgrow(button3, Priority.ALWAYS);
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        button3.setMaxWidth(Double.MAX_VALUE);


        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(400);
        String filePath = "C:\\Users\\Niklas Holt Läu\\Documents\\GUIFX\\src\\test2.csv";

        // Initial population of ListView
        List<String> csvData = readCSV(filePath);
        ObservableList<String> items = FXCollections.observableArrayList(csvData);
        listView.setItems(items);
        
        
        // Add the buttons to the root
        root.getChildren().add(buttonBox);
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.setPrefHeight(800);
        root.setStyle("-fx-background-color: #808080;");

        Timer timer = new Timer(true); // Daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    List<String> updatedData = readCSV(filePath);
                    listView.setItems(FXCollections.observableArrayList(updatedData)); // Refresh the ListView
                    System.out.println("Data refreshed: " + updatedData); // Debug log
                });
            }
        }, 0, 1000);


        // Set actions for the buttons
        button1.setOnAction(event -> {
            String command = "gnome-terminal -- sudo python /path/to/DOBBYPerPacketsSecond.py";
            runPythonScript(command);
        });  // Keeping the scene the same
        button2.setOnAction(event -> changeScenePPS(root, newContent));
        button3.setOnAction(event -> initializeMainScene(root, new Text("MAIN")));
  // Go back to the main scene
    }


    private void changeSceneTRACK(VBox root, Text newContent) {
        // Clear the existing content
        root.getChildren().clear();


        Button button1 = new Button("START");
        Button button2 = new Button("STOP");
        Button button3 = new Button("BACK");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(button1, button2, button3);
        button1.setMinHeight(300);
        button2.setMinHeight(300);
        button3.setMinHeight(300);
        button1.setStyle("-fx-font-size: 30px;");
        button2.setStyle("-fx-font-size: 30px;");
        button3.setStyle("-fx-font-size: 30px;");
        HBox.setHgrow(button1, Priority.ALWAYS);
        HBox.setHgrow(button2, Priority.ALWAYS);
        HBox.setHgrow(button3, Priority.ALWAYS);
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        button3.setMaxWidth(Double.MAX_VALUE);

        //Creating ListVew to display CSV
        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(400);
        String filePath = "C:\\Users\\Niklas Holt Läu\\Documents\\GUIFX\\src\\test.csv";
        List<String> csvData = readCSV(filePath);
        System.out.println(csvData);
        ObservableList<String> items = FXCollections.observableArrayList(csvData);
        listView.setItems(items);

        // Add the ListView and buttons to the root layout
        VBox contentBox = new VBox(10, listView, buttonBox);
        contentBox.setAlignment(Pos.CENTER);


        root.getChildren().add(contentBox);
        root.setStyle("-fx-background-color: #808080;");

        // Set actions for the new buttons
        button1.setOnAction(event -> changeSceneTRACK(root, newContent));  // Keeping the scene the same
        button2.setOnAction(event -> changeSceneTRACK(root, newContent));
        button3.setOnAction(event -> changeSceneTrainer(root, new Text("BACK")));
        // Go back to the main scene
    }



    private void changeSceneTRAIN(VBox root, Text newContent) {
        // Clear the existing content
        root.getChildren().clear();

        // Add the new content
        root.getChildren().add(newContent);

        // Create new buttons for the trainer scene
        Button button1 = new Button("START");
        Button button2 = new Button("STOP");
        Button button3 = new Button("BACK");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(button1, button2, button3);
        button1.setMinHeight(300);
        button2.setMinHeight(300);
        button3.setMinHeight(300);
        button1.setStyle("-fx-font-size: 30px;");
        button2.setStyle("-fx-font-size: 30px;");
        button3.setStyle("-fx-font-size: 30px;");
        HBox.setHgrow(button1, Priority.ALWAYS);
        HBox.setHgrow(button2, Priority.ALWAYS);
        HBox.setHgrow(button3, Priority.ALWAYS);
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        button3.setMaxWidth(Double.MAX_VALUE);

        // Add the buttons to the root
        root.getChildren().add(buttonBox);
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.setPrefHeight(800);
        root.setStyle("-fx-background-color: #f4f4f4;");

        // Set actions for the new buttons
        button1.setOnAction(event -> changeSceneTRAIN(root, newContent));  // Keeping the scene the same
        button2.setOnAction(event -> changeSceneTRAIN(root, newContent));
        button3.setOnAction(event -> changeSceneTrainer(root, new Text("BACK")));
        // Go back to the main scene
    }


    private List<String> readCSV(String filepath) {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println("Data read from file: " + data); // Debugging
        return data;
    }


    private void runPythonScript(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(""));
            processBuilder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
