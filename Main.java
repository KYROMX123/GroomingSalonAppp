import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;


public class GroomingApp extends Application {

    private ObservableList<AppointmentList> list = FXCollections.observableArryList();

    public void start(Stage stage) {

        TextField ownerField = new TextField();
        ownerField.setPromptText("Owner Name");

        TextField dogField = new TextField();
        DogField.setPromptText("Dog Name");

        TextField breedField = new TextField();
        breedField.setPromptText("Breed Name");

        TextField serviceTypeField = new TextField();
        serviceTypeField.setPromptText("Service Type");

        TableView tableView = new TableView();

        TableColumn<Appointment, String> ownerCol = new TableColumn<>("Owner");
        ownerCol.setCellValueFactory(new PropertyValueFactory<>("ownerName"));

        TableColumn<Appointment, String> dogCol = new TableColumn<>("Dog");
        dogCol.setCellValueFactory(new PropertyValueFactory<>("dogName"));

        TableColumn<Appointment, String> breedCol = new TableColumn<>("Breed");
        breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));

        TableColumn<Appointment, String> serviceCol = new TableColumn<>("Service");
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));

        table.getColumns().addAll(ownerCol, dogCol, breedCol, serviceCol);
        table.setItems(list);

        Button addBtn = new Button("Προσθήκη");
        Button saveBtn = new Button("Αποθήκευση CSV");
        Button loadBtn = new Button("Φόρτωση CSV");

        addBtn.setOnAction(e -> {
            Appointment ap = new Appointment(
                    ownerField.getText(),
                    dogField.getText(),
                    breedField.getText(),
                    serviceField.getText()
            );
            list.add(ap);

            ownerField.clear();
            dogField.clear();
            breedField.clear();
            serviceField.clear();
        });


        loadBtn.setOnAction(e -> {
            list.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader("appointments.csv"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    Appointment ap = new Appointment(data[0], data[1], data[2], data[3]);
                    list.add(ap);
                }
                System.out.println("Loaded!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        HBox form = new HBox(10, ownerField, dogField, breedField, serviceField);
        HBox buttons = new HBox(10, addBtn, saveBtn, loadBtn);
        VBox root = new VBox(10, form, buttons, table);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Happy Paws Grooming");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}