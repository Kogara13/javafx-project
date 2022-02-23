

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NewClass extends Application{
    
    //Text used in scenes. Need to be global variables to work
    static Text result = new Text("");
    static Text status = new Text("");
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        // =========================== Scene Setup =============================
        VBox Scene1VBox = new VBox();
        
        HBox Scene2HBox = new HBox();
        VBox Scene2VBox = new VBox();
        
        VBox Scene3VBox = new VBox();
        HBox Scene3idBox = new HBox();
        HBox Scene3nameBox = new HBox();
        HBox Scene3deptBox = new HBox();
        
        
        Scene1VBox.setAlignment(Pos.CENTER);
        
        Scene2HBox.setAlignment(Pos.CENTER);
        Scene2VBox.setAlignment(Pos.CENTER);
        
        Scene3VBox.setAlignment(Pos.CENTER);
        Scene3idBox.setAlignment(Pos.CENTER);
        Scene3nameBox.setAlignment(Pos.CENTER);
        Scene3deptBox.setAlignment(Pos.CENTER);
        
        Scene scene1 = new Scene(Scene1VBox, 300, 300);
        Scene scene2 = new Scene(Scene2VBox, 300, 300);
        Scene scene3 = new Scene(Scene3VBox, 700, 300);
        // =====================================================================
        
        
        //==========================Scene 1 Properties==========================
        Label scene1Label = new Label("Choose one Option");
        Button searchButton = new Button("Get Instructor Information");
        searchButton.setOnAction(e -> primaryStage.setScene(scene2));
        
        Button addButton = new Button("Insert a new instructor");
        addButton.setOnAction(e -> primaryStage.setScene(scene3));
        
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());
        
        Scene1VBox.getChildren().addAll(scene1Label, searchButton, addButton, exitButton);
        //======================================================================
        
        

        //==========================Scene 2 Properties==========================
        Label scene2Label = new Label("Enter the instructor ID");
        
        TextField searchInstructor = new TextField ();
        searchInstructor.setPromptText("Instructor ID");
        
        Button search = new Button("Search");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    String id = searchInstructor.getText();
                    instructorSearch(id);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Button scene2Back = new Button("Main Menu");
        scene2Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                //Clear Search Scene
                searchInstructor.clear();
                result.setText("");
                
                primaryStage.setScene(scene1);
            }
        });
        
        Scene2HBox.getChildren().addAll(searchInstructor, search);
        Scene2VBox.getChildren().addAll(scene2Label, Scene2HBox, result, scene2Back);
        //======================================================================
        
        
        //==========================Scene 1 Properties==========================
        Label scene3Label = new Label("Enter the following information:");
        
        TextField enterId = new TextField(); enterId.setPromptText("Instructor ID");
        TextField enterName = new TextField(); enterName.setPromptText("Instructor Name");
        TextField enterDept = new TextField(); enterDept.setPromptText("Affiliated Department Name");
        
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String[] info_input = new String[3];
                info_input[0] = enterId.getText();
                info_input[1] = enterName.getText();
                info_input[2] = enterDept.getText();
                try {
                    addInstructor(info_input);
                } catch (IOException ex) {
                    Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        Button scene3Back = new Button("Main Menu");
        scene3Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Clear Add Scene
                enterId.clear();
                enterName.clear();
                enterDept.clear();
                status.setText("");
                
                primaryStage.setScene(scene1);
            }
        });
       
        Scene3VBox.getChildren().addAll(scene3Label, enterId, enterName, enterDept, submit, status, scene3Back);
       //=======================================================================
       
       
       //=========================Setting Primary Stage=========================
        primaryStage.setTitle("test");
        primaryStage.setScene(scene1);
        primaryStage.show();
        //======================================================================
    }
    
    //===============Function to Search for Instructor in Scene 2===============
    public static void instructorSearch(String id) throws FileNotFoundException {
            File instructorFile = new File("E:\\JavaFXProjext\\src\\instructor.txt");
            File departmentFile = new File("E:\\JavaFXProjext\\src\\department.txt");
            Scanner department;
            boolean exists;
        try (Scanner instructor = new Scanner(instructorFile)) {
            department = new Scanner(departmentFile);
            exists = false;
            String id_data = "";
            String resultText = "";
            while(instructor.hasNextLine()){
                String[] instructorData = instructor.nextLine().split(",");
                if(id.matches(instructorData[0])){
                    exists = true;
                    id_data = instructorData[2];
                    resultText = "Name: " + instructorData[1] + "\nDepartment: " + instructorData[2];
                    break;
                    
                }
            }
            while(department.hasNextLine()){
                String[] departmentData = department.nextLine().split(",");
                if(id_data.matches(departmentData[0])){
                    resultText = resultText + "\nDepartment Location: " + departmentData[1];
                    break;
                }
            }
            result.setText(resultText);
        }
            department.close();
            if (!exists){
                result.setText("The ID does not appear in the file");
            }
        }
    //==========================================================================
    
    
    //=================Function to add Instructor in Scene 3====================
    public static void addInstructor(String[] data) throws FileNotFoundException, IOException{
        File instructorFile = new File("E:\\JavaFXProjext\\src\\instructor.txt");
        File departmentFile = new File("E:\\JavaFXProjext\\src\\department.txt");
        Scanner department;
        try (Scanner instructor = new Scanner(instructorFile)) {
            department = new Scanner(departmentFile);
            FileWriter instructorWrite = new FileWriter(instructorFile, true);
            PrintWriter output = new PrintWriter(instructorWrite);
            //Two while loops to check if we can add
            boolean id_check = false;
            boolean dept_check = false;
            String error = "";
            //Cannot check the ID and Department by comparing the arrays directly
            String dept = data[2];
            String id = data[0];
            while(instructor.hasNextLine()){
                String[] instructorData = instructor.nextLine().split(",");
                if (id.matches(instructorData[0])){
                    id_check = true;
                    break;
                }
            }   
            if(id_check){
                error = error + "\nError: The ID already exists in the file\n";
            }
            
            while(department.hasNextLine()){
                String[] departmentData = department.nextLine().split(",");
                if (dept.matches(departmentData[0])){
                    dept_check = true;
                    break;
                }
            }
            if(!dept_check){
                error = error + "\nError: The department does not exist and hence the instructor record cannot be added to the file\n";
            }
            
            if (!id_check && dept_check){
                output.print(data[0] + "," + data[1] + "," + data[2] + "\n");
                output.close();
                status.setText("Success");
            } else{
                status.setText(error);
            }
        }
        department.close();
    }
    //==========================================================================
    
    public static void main(String[] args){
        launch(args);
    }
}