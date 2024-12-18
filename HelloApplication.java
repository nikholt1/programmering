import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.io.*;
import java.nio.file.*;
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
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.InputStreamReader;
import java.io.FileWriter;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.io.*;
import java.util.Timer;
import java.util.Properties;
import javafx.scene.layout.Pane;
import java.lang.Process;
import java.lang.ProcessBuilder;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;


public class GUI2 extends Application {


    public static final String RESET = "\033[0m";
    public static final String GREEN = "\033[32m";
    public static final String PURPLE = "\033[35m";
    public static final String ORANGE = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String RED = "\033[31m";

    private Stage primaryStage;
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        // set stage always on top, to overlap gnome terminals in methods
        stage.setAlwaysOnTop(true);
        System.out.println(BLUE + "[SYSTEM] stage always on top: true" + RESET);
        stage.requestFocus();
        // Initialize the VBox and other UI elements
        VBox root = new VBox();
        System.out.println(BLUE + "[SYSTEM] initializing main scene" + RESET);
        initializeMainScene(stage, root, new Text("INIT"));

        // Set the Scene and show the Stage
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.setTitle("DOBBY GUI");
        stage.setFullScreen(true);
        System.out.println(BLUE + "[SYSTEM] stage set to full screen: true" + RESET);

        stage.show();
    }

    private void initializeMainScene(Stage stage, VBox root, Text newContent) {
        root.getChildren().clear();

        // Create buttons for the main scene
        Button button1 = new Button("TRAINER");
        Button button2 = new Button("MAC LOGGING");
        Button button3 = new Button("PACKET PER SECOND");
        Button button4 = new Button("SETTINGS");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(button1, button2, button3, button4);
        button1.setMinHeight(300);
        button2.setMinHeight(300);
        button3.setMinHeight(300);
        button4.setMinHeight(300);

        button1.setStyle("-fx-font-size: 30px;");
        button2.setStyle("-fx-font-size: 30px;");
        button3.setStyle("-fx-font-size: 30px;");
        button4.setStyle("-fx-font-size: 30px;");

        // Make buttons grow and take available space
        HBox.setHgrow(button1, Priority.ALWAYS);
        HBox.setHgrow(button2, Priority.ALWAYS);
        HBox.setHgrow(button3, Priority.ALWAYS);
        HBox.setHgrow(button4, Priority.ALWAYS);
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        button3.setMaxWidth(Double.MAX_VALUE);
        button4.setMaxWidth(Double.MAX_VALUE);        

        // Set the layout and alignment for the buttons

        root.getChildren().clear();
        root.getChildren().add(buttonBox);
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.setPrefHeight(800);
        root.setStyle("-fx-background-color: #808080;");


        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:
                    button1.fire();
                    break;
                case DIGIT2:
                    button2.fire();
                    break;
                case DIGIT3:
                    button3.fire();
                    break;
                default:
                    break;

            }
        });

        // Set the actions for the buttons
        button1.setOnAction(event -> changeSceneTrainer(stage, root, new Text("SELECTED")));
        button2.setOnAction(event -> changeSceneMACLOG(stage, root, new Text("MACLOG")));
        button3.setOnAction(event -> changeScenePPS(stage, root, new Text("PACKET PER SECOND")));
        button4.setOnAction(event -> changeSceneSettings(stage, root, new Text("SETTINGS")));
    }

    private void changeSceneSettings(Stage stage, VBox root, Text newContent) {
        root.getChildren().clear();

        // Add the new content
        root.getChildren().add(newContent);


        // add splitpane
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.5);
        
        
        String configFilePath = "/home/User1/Desktop/DOBBYprgrm/DOBBYconfig.conf";
        System.out.println(ORANGE + "found file path: /home/User1/Desktop/DOBBYprgrm/DOBBYconfig.conf" + RESET); 


        // Create new buttons for the trainer scene
        Button button1 = new Button("CONFIG");
        Button button2 = new Button("DELETE DATABASE");
        Button button3 = new Button("WRITE TO USB");
        Button button4 = new Button("BACK");
        VBox buttonBox = new VBox();
        buttonBox.getChildren().addAll(button1, button2, button3, button4);


        // Add the buttons to the root
        
        Pane contentPane = new Pane();
        contentPane.setStyle("-fx-background-color: #FFFFFF;");
        
        splitPane.getItems().addAll(buttonBox, contentPane);
        buttonBox.setMinWidth(100);
        buttonBox.setMaxWidth(200);
        
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        root.getChildren().add(splitPane);
        

        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:
                    button1.fire();
                    break;
                case DIGIT2:
                    button2.fire();
                    break;
                case DIGIT3:
                    button3.fire();
                    break;
                default:
                    break;

            }
        });


        // Set actions for the new buttons
        button1.setOnAction(event -> {
            contentPane.getChildren().clear();
            String key = "interface";
                 
            String interfaceName = readConfigFile(configFilePath, key, update);
            
            Text InterFaceName = new Text("DOBBYConfig.conf interface: " + interfaceName);
            TextArea newInter = new TextArea();
            newInter.setPromptText("Input new Interface");
            newInter.setPrefHeight(40);
            Button writeButton = new Button("Write to Conf");
            
            writeButton.setOnAction(buttonEvent -> {
                String key2 = "interface";
                newValue = newInter.getText().trim();
                System.out.println(ORANGE + "[SYSTEM METHOD] newValue to parse to config file = " + newValue + RESET);
                writeConfigFile(configFilePath, key2);                
            
            });
            
            key = "target_mac";     
            String targetMacName = readConfigFile(configFilePath, key, update);            
            
            Text TargMacName = new Text("DOBBYConfig.conf target_mac: " + targetMacName);
            TextArea newMac = new TextArea();
            newMac.setPrefHeight(40);
            newMac.setPromptText("Input new target_mac");
            Button writeButton2 = new Button("Write to Conf");   
                     
            writeButton2.setOnAction(buttonEvent -> {
                String key2 = "target_mac";
                newValue = newMac.getText().trim();
                System.out.println(ORANGE + "[SYSTEM METHOD] newValue to parse to config file = " + newValue + RESET);
                writeConfigFile(configFilePath, key2);                
            
            });
            System.out.println(ORANGE + "[SYSTEM METHOD] newValue written to config file " + configFilePath + " successfully " + RESET);
            
            
            key = "target_mac2";     
            String targetMacName2 = readConfigFile(configFilePath, key, update);            
            
            Text TargMacName2 = new Text("DOBBYConfig.conf target_mac2: " + targetMacName2);
            TextArea newMac2 = new TextArea();
            newMac2.setPrefHeight(40);
            newMac2.setPromptText("Input new target_mac");
            Button writeButton3 = new Button("Write to Conf");   
                     
            writeButton3.setOnAction(buttonEvent -> {
                String key2 = "target_mac2";
                newValue = newMac2.getText().trim();
                System.out.println(ORANGE + "[SYSTEM METHOD] newValue to parse to config file = " + newValue + RESET);
                writeConfigFile(configFilePath, key2);                
            
            });
            System.out.println(ORANGE + "[SYSTEM METHOD] newValue written to config file " + configFilePath + " successfully " + RESET);
                        
            
            
            
            VBox ConfBox = new VBox();
            
            ConfBox.getChildren().addAll(InterFaceName, newInter, writeButton, TargMacName, newMac, writeButton2, TargMacName2, newMac2, writeButton3);
            

            
            
            contentPane.getChildren().addAll(ConfBox);

        
        
        });  

        button2.setOnAction(event -> {
        
            System.out.println("Button2 pressed");
            contentPane.getChildren().clear();
            Label dataBaseSession = new Label("Session DATABASES");
            String directory = "/home/User1/Desktop/DOBBYprgrm/DOBBYData";
            File folder = new File(directory);
            if (!folder.exists()) {
                System.out.print(ORANGE + "[SYSTEM METHOD] Directory does not exist: " + directory + RESET);
            } else if (!folder.isDirectory()) {
                System.out.println(ORANGE + "[SYSTEM METHOD] Path is not a directory: " + directory + RESET);
            } else {
                System.out.println(ORANGE + "[SYSTEM METHOD] Directory is valid" + RESET);
            }
            ListView<String> fileListView = new ListView<>();
            listFilesInFolder(directory, fileListView);
            Button deleteSessionFiles = new Button("Delete all session files");
            Button deleteDataFiles = new Button("Delete all time Database");
            VBox MacDataBase = new VBox();
            
            MacDataBase.getChildren().addAll(dataBaseSession, fileListView, deleteSessionFiles, deleteDataFiles);
            
            contentPane.getChildren().addAll(MacDataBase);
        
        });
        button4.setOnAction(event -> initializeMainScene(stage, root, new Text("BACK")));        
        
        
        
        }



    
    
    private void changeSceneTrainer(Stage stage, VBox root, Text newContent) {
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


        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:
                    button1.fire();
                    break;
                case DIGIT2:
                    button2.fire();
                    break;
                case DIGIT3:
                    button3.fire();
                    break;
                default:
                    break;

            }
        });


        // Set actions for the new buttons
        button1.setOnAction(event -> changeSceneTRAIN(stage, root, new Text("TRAINING")));  // Keeping the scene the same
        button2.setOnAction(event -> changeSceneTRACK(stage, root, new Text("TRACKING")));
        button3.setOnAction(event -> initializeMainScene(stage, root, new Text("MAIN")));
        // Go back to the main scene
    }

    private void changeSceneMACLOG(Stage stage, VBox root, Text newContent) {
        stage.setAlwaysOnTop(true);
        stage.requestFocus();


        // Clear the existing content
        root.getChildren().clear();
        
        
        TextArea terminalOutput = new TextArea();
        terminalOutput.setEditable(false);
        terminalOutput.setPrefHeight(3);
        terminalOutput.setStyle("-fx-font-size: 14px;");
        terminalOutput.setWrapText(true);
        



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
        String filePath = "/home/User1/Desktop/DOBBYprgrm/DOBBYData/DataBase.csv";

        // Initial population of ListView
        List<String> csvData = readCSV(filePath);
        ObservableList<String> items = FXCollections.observableArrayList(csvData);
        listView.setItems(items);

        // Add the ListView and buttons to the layout
        VBox contentBox = new VBox(10, listView, buttonBox);
        contentBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(terminalOutput, contentBox);
        root.setStyle("-fx-background-color: #808080;");
        
        if (gnomeTerminalPid == null) {
                terminalOutput.setText("Not Running");
                terminalOutput.setStyle("-fx-control-inner-background: darkred; -fx-text-fill: white;");
        }
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



        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:
                    button1.fire();
                    break;
                case DIGIT2:
                    button2.fire();
                    break;
                case DIGIT3:
                    button3.fire();
                    break;
                default:
                    break;

            }
        });


        // Set actions for the new buttons
        button1.setOnAction(event -> {
            stage.requestFocus();
            System.out.println(ORANGE + "[SYSTEM METHOD] stage requested focus to overlay gnome-terminal" + RESET);
            String command = "gnome-terminal -- sudo python /home/User1/Desktop/DOBBYprgrm/DOBBYMacAndLog.py &";
            gnomeTerminalPid = startGnomeTerminalAndGetPid(stage, command);
            System.out.println("Terminal started");
            if (gnomeTerminalPid != null) {
                System.out.println("[gnome-terminal] " + gnomeTerminalPid + "alive");
                terminalOutput.setText("Running");
                terminalOutput.setStyle("-fx-control-inner-background: green; -fx-text-fill: white;");
            }

        });  // Keeping the scene the same
        button2.setOnAction(event -> {
            if (gnomeTerminalPid != null) {
                KillGnomeTerminal(gnomeTerminalPid);
                gnomeTerminalPid = null;
            }
            if (gnomeTerminalPid == null) {
                terminalOutput.setText("Not Running");
                terminalOutput.setStyle("-fx-control-inner-background: darkred; -fx-text-fill: white;");
            }            
            
        });
        button3.setOnAction(event -> {
            timer.cancel();
            initializeMainScene(stage, root, new Text("MAIN"));

        });

        // Go back to the main scene
    }

    private void changeScenePPS(Stage stage, VBox root, Text newContent) {


        stage.setAlwaysOnTop(true);
        stage.requestFocus();

        // Clear the existing content
        root.getChildren().clear();
        
        String configFilePath = "/home/User1/Desktop/DOBBYprgrm/DOBBYconfig.conf";
        String key = "interface";
        String interfaceName = readConfigFile(configFilePath, key, update);
        
        TextArea terminalOutputtwo = new TextArea();
        terminalOutputtwo.setEditable(false);
        terminalOutputtwo.setPrefHeight(3);
        terminalOutputtwo.setStyle("-fx-font-size: 14px;");
        terminalOutputtwo.setWrapText(true);
        
        TextArea terminalOutput = new TextArea();
        terminalOutput.setEditable(false);
        terminalOutput.setPrefHeight(0);
        terminalOutput.setStyle("-fx-font-size: 14px;");
        terminalOutput.setWrapText(true);

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






        // Add the buttons to the root
        VBox contentBox = new VBox(10, buttonBox);
        contentBox.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(terminalOutputtwo,terminalOutput, buttonBox);
        root.setStyle("-fx-background-color: #808080;");
        

        terminalOutputtwo.setText("Not Running");
        terminalOutputtwo.setStyle("-fx-control-inner-background: darkred; -fx-text-fill: white;");
        
        
        terminalOutput.appendText("Press start to start scan");
        
        terminalOutput.setPrefHeight(stage.getHeight() * 0.2); 




        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:
                    button1.fire();
                    break;
                case DIGIT2:
                    button2.fire();
                    break;
                case DIGIT3:
                    button3.fire();
                    break;
                default:
                    break;

            }
        });

        // Set actions for the buttons
        button1.setOnAction(event -> {
            String command = "sudo tcpdump -i " + interfaceName;
                //gnomeTerminalPid = startGnomeTerminalAndGetPid(stage, command);
                //System.out.println("Terminal started with interface " + interfaceName + " Tracking Mac: " + processedItem);
            terminalOutputtwo.setText("Running");
            terminalOutputtwo.setStyle("-fx-control-inner-background: green; -fx-text-fill: white;");
            startCommand(command, terminalOutput);
                
            primaryStage.requestFocus();
            System.out.println("PPS requested focus");
            

            //String command = "gnome-terminal -- sudo python /home/User1/Desktop/DOBBYprgrm/DOBBYPerPacketsSecond.py &";
            //gnomeTerminalPid = startGnomeTerminalAndGetPid(stage, command);
            //System.out.println("Terminal started");
            //primaryStage.requestFocus();
            //System.out.println("PPS requested focus");

        });  // Keeping the scene the same
        button2.setOnAction(event -> {
            stopCommand(terminalOutput);

            terminalOutputtwo.setText("Not Running");
            terminalOutputtwo.setStyle("-fx-control-inner-background: darkred; -fx-text-fill: white;");
                       
        });
        button3.setOnAction(event -> {
            initializeMainScene(stage, root, new Text("MAIN"));

        });
        // Go back to the main scene
    }

    private String processedItem = "";
    private void changeSceneTRACK(Stage stage, VBox root, Text newContent) {
        // Clear the existing content
        root.getChildren().clear();
        
        String configFilePath = "/home/User1/Desktop/DOBBYprgrm/DOBBYconfig.conf";
        String key = "interface"; 
        String interfaceName = readConfigFile(configFilePath, key, update);

        
        
        final StringProperty selectedItemProperty = new SimpleStringProperty();
        Label selectedLabel = new Label("Selected: None");
        selectedLabel.setStyle("-fx-text-fill: white;");
        
        TextArea terminalOutputtwo = new TextArea();
        terminalOutputtwo.setEditable(false);
        terminalOutputtwo.setPrefHeight(3);
        terminalOutputtwo.setStyle("-fx-font-size: 14px;");
        terminalOutputtwo.setWrapText(true);
        
        
        TextArea terminalOutput = new TextArea();
        terminalOutput.setEditable(false);
        terminalOutput.setPrefHeight(0);
        terminalOutput.setStyle("-fx-font-size: 14px;");
        terminalOutput.setWrapText(true);
        
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
        String filePath = "/home/User1/Desktop/DOBBYprgrm/DOBBYData/DataBase.csv";
        List<String> csvData = readCSV(filePath);
        System.out.println(csvData);
        ObservableList<String> items = FXCollections.observableArrayList(csvData);
        listView.setItems(items);
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {

                String[] parts = newSelection.split(",", 2);
                processedItem = parts.length > 1 ? parts[1].trim() : "";

                selectedItemProperty.set(processedItem);
                selectedLabel.setText("Selected: " + processedItem);


                //String filePathWrite = "/home/User1/Desktop/DOBBYprgrm/TRACK.csv";
                //CSVWriter(filePathWrite, processedItem);




            } else {
                selectedItemProperty.set(null);
                selectedLabel.setText("Selected: None");

            }

        });
        
        // Add the ListView and buttons to the root layout
        VBox contentBox = new VBox(10, listView, buttonBox);
        contentBox.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(terminalOutputtwo, terminalOutput, listView, selectedLabel, buttonBox);
        root.setStyle("-fx-background-color: #808080;");

        terminalOutputtwo.setText("Not Running");
        terminalOutputtwo.setStyle("-fx-control-inner-background: darkred; -fx-text-fill: white;");
        
        terminalOutput.appendText("Select target mac and press start");
        
        terminalOutput.setPrefHeight(stage.getHeight() * 0.2); 

        

        

        //write to csv file




        

        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:
                    button1.fire();
                    break;
                case DIGIT2:
                    button2.fire();
                    break;
                case DIGIT3:
                    button3.fire();
                    break;
                default:
                    break;

            }
        });




        

        // Set actions for the new buttons
        button1.setOnAction(event -> {
            String selectedItem = selectedItemProperty.get();
            if (selectedItem != null && !selectedItem.isEmpty()) {
                System.out.println("Selected Item: " + selectedItem);
                String command = "sudo tcpdump -i " + interfaceName + " ether host " + processedItem;
                //gnomeTerminalPid = startGnomeTerminalAndGetPid(stage, command);
                //System.out.println("Terminal started with interface " + interfaceName + " Tracking Mac: " + processedItem);
                terminalOutputtwo.setText("Running");
                terminalOutputtwo.setStyle("-fx-control-inner-background: green; -fx-text-fill: white;");
                startCommand(command, terminalOutput);
                
                primaryStage.requestFocus();
                System.out.println("PPS requested focus");
                captureTerminalOutput(gnomeTerminalPid, terminalOutput);


            }


        });  // Keeping the scene the same
        button2.setOnAction(event -> {
            //if (gnomeTerminalPid != null) {
            //    KillGnomeTerminal(gnomeTerminalPid);
            //    gnomeTerminalPid = null;
            //}
            stopCommand(terminalOutput);
            terminalOutputtwo.setText("Not running");
            terminalOutputtwo.setStyle("-fx-control-inner-background: darkred; -fx-text-fill: white;");
            
        });
        button3.setOnAction(event -> changeSceneTrainer(stage, root, new Text("BACK")));
        // Go back to the main scene
    }



    private void changeSceneTRAIN(Stage stage, VBox root, Text newContent) {
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

        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:
                    button1.fire();
                    break;
                case DIGIT2:
                    button2.fire();
                    break;
                case DIGIT3:
                    button3.fire();
                    break;
                default:
                    break;

            }
        });


        // Set actions for the new buttons
        button1.setOnAction(event -> changeSceneTRAIN(stage, root, newContent));  // Keeping the scene the same
        button2.setOnAction(event -> changeSceneTRAIN(stage, root, newContent));
        button3.setOnAction(event -> changeSceneTrainer(stage, root, new Text("BACK")));
        // Go back to the main scene
    }

    private void CSVWriter(String filePathWrite, String processedItem) {
        try (FileWriter writer = new FileWriter(filePathWrite)) {
            writer.write(processedItem);
            System.out.println(RED + "[SYSTEM WORKER METHOD] Data written to " + filePathWrite + RESET);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(RED + "[SYSTEM WORKER METHOD] ERROR writing to file" + RESET);
        }


    }

    private List<String> readCSV(String filepath) {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println(RED + "[SYSTEM WORKER METHOD] ERROR reading file: " + e.getMessage() + RESET);
        }
        System.out.println(RED + "[SYSTEM WORKER METHOD] Data read from file: " + data + RESET); // Debugging
        return data;
    }

    //    private Process pythonProcess;
    private Long gnomeTerminalPid;

    private Long startGnomeTerminalAndGetPid(Stage stage, String command) {
        try {
            // Start the gnome-terminal
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.start();
            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Use 'ps' to get the PID of the most recent gnome-terminal process
            Process psProcess = new ProcessBuilder("bash", "-c", "ps -eo pid,comm | grep gnome-terminal | tail -1 | awk '{print $1}'").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(psProcess.getInputStream()));

            String pidString = reader.readLine();
            if (pidString != null && !pidString.isEmpty()) {
                System.out.println(RED + "[SYSTEM WORKER METHOD] Started gnome-terminal with PID: " + pidString + RESET);
                return Long.parseLong(pidString.trim()); // Return the PID as a Long
            } else {
                System.out.println(RED + "[SYSTEM WORKER METHOD] ERROR Failed to retrieve gnome-terminal PID." + RESET);
                return null; // No PID found, return null
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Exception occurred, return null
        }
    }
    private void KillGnomeTerminal(long pid) {
        try {
            String Killcommand = "sudo kill -9 " + pid;
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", Killcommand);
            processBuilder.start();
            System.out.println(RED + "[SYSTEM WORKER METHOD] killed gnome-terminal with PID " + pid + RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private Process runPythonScript(String command) {
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
//            Process process = processBuilder.start();
//            return process;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    private String interfaceName;
    private String trainerName;
    private boolean update = false;
    private String newValue;
    
    public String readConfigFile(String configFilePath, String key, boolean update) {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {
            String line;
            boolean inTrainerSection = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.equals("[trainer]")) {
                    inTrainerSection = true;        
                }
                
                if(inTrainerSection && line.startsWith(key)) {
                    String [] parts = line.split("=", 2);
                    if (parts.length > 1) {
                        interfaceName = parts[1].trim();
                        System.out.println(RED + "[SYSTEM WORKER METHOD] Found interface: " + interfaceName + RESET);
                    }
                    break;
                
                }
                
                
            
            }
            if (interfaceName == null) {
                System.out.println(RED + "[SYSTEM WORKER METHOD] No interface found in [trainer] section" + RESET);
            
            
            
            }      
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        return interfaceName;
    }
    
    
    private void writeConfigFile(String configFilePath, String key2) {
        try {
            Path path = Paths.get(configFilePath);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                boolean keyFound = false;
                
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    if (line.startsWith(key2)) {
                        lines.set(i, key2 + " = " + newValue);
                        keyFound = true;
                        break;
                    
                    }
                
                }
                if (keyFound) {
                    Files.write(path, lines);
                    System.out.println(RED + "[SYSTEM WORKER METHOD] Config file updated with key: " + key2 + " and new value: " + newValue + RESET);
                    
                
                } else {
                    System.out.println(RED + "[SYSTEM WORKER METHOD] ERROR " + key2 + " not found in config file under path " + configFilePath + RESET);
                
                }
            
            } else {
                System.out.println(RED + "[SYSTEM WORKER METHOD] config file not found: " + configFilePath);
            
            }
        
        } catch (IOException e) {
            System.out.println(RED + "[SYSTEM WORKER METHOD] ERROR: " + e.toString() + " in writeConfigFile method" + RESET);
        
        }
    
    
    }
    
    
    private void listFilesInFolder(String directory, ListView<String> fileListView) {
        File folder = new File(directory);        
        
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return !name.startsWith("MacAndLog");
                }
                
        });
                
            fileListView.getItems().clear();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    fileListView.getItems().add(file.getName());
                } 
            } else {
                fileListView.getItems().add("No files in Directory");
                
            } 
            
            
        } else {
            fileListView.getItems().add("ERROR: directory " + directory + " does not exist");
            
        }    
    
    
    
    
    
    }
    
    
    
    
    private void captureTerminalOutput(long gnomeTerminalPid, TextArea terminalOutput) {
    
        try {
            String pidStr = String.valueOf(gnomeTerminalPid);
            
            Process findTtyProcess = new ProcessBuilder("bash", "-c", "sudo", "ps", "--ppid", pidStr, "-o", "tty=", "|", "grep", "-v", "'?'", "|", "head", "-n", "1").start();
            System.out.println(findTtyProcess);
            BufferedReader ttyReader = new BufferedReader(new InputStreamReader(findTtyProcess.getInputStream()));
            String tty = ttyReader.readLine();
            
            if (tty == null || tty.trim().isEmpty()) {
                Platform.runLater(() -> terminalOutput.appendText("ERROR coud not find valid tty for PID " + gnomeTerminalPid + "\n"));
                Platform.runLater(() -> terminalOutput.appendText("TTY trimmed for debug: " + tty.trim()));
            
            }
            
            String ptyPath = "/dev/" + tty.trim();
            String command = "cat " + ptyPath;
            
            
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String finalLine = line;
                Platform.runLater(() -> terminalOutput.appendText(finalLine + "\n"));

            }
        
        
        } catch (Exception e) {
            Platform.runLater(() -> terminalOutput.appendText("Error for debug: " + e.getMessage() + "\n"));

        }
    
    
    }
    
    private void runCommand(String commandKill) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandKill.split(" "));
            processBuilder.start();
            System.out.println("Killed All Gnome Terminals");

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    private Process commandProcess; 
    private void startCommand(String command, TextArea terminalOutput) {
        new Thread(() -> {
            try {    
                    ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
                    processBuilder.redirectErrorStream(true);
                    commandProcess = processBuilder.start();

                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(commandProcess.getInputStream())); 
                    String line;
                    
                    while ((line = reader.readLine()) != null) {
                        String finalLine = line;
                        Platform.runLater(() -> terminalOutput.appendText(finalLine + "\n"));
                    }
                    
            } catch (Exception e) {
                Platform.runLater(() -> terminalOutput.appendText(" Error: " + e.getMessage() + "\n"));
            
            }
        }).start();
    
    
    }
    
    
    private void stopCommand(TextArea terminalOutput) {

        if (commandProcess != null && commandProcess.isAlive()) {
            commandProcess.destroy();
            Platform.runLater(() -> terminalOutput.appendText("command Stopped \n"));
            commandProcess = null;
        } else {
            Platform.runLater(() -> terminalOutput.appendText("No command to stop \n"));
        }    
  
    }

    public static void main(String[] args) {
        launch(args);
    }
}
