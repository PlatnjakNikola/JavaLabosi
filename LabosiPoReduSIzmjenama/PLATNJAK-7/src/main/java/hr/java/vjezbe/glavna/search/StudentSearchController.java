package hr.java.vjezbe.glavna.search;

import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.glavna.GlavnaDatoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class StudentSearchController implements OsobaFilter {
    private static List<Student> studentList = new ArrayList<>();

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField JMBAGTextField;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> nameOfStudent;
    @FXML
    private TableColumn<Student, String> LastNameOfStudent;
    @FXML
    private TableColumn<Student, String> JMBAGofStudent;
    @FXML
    private TableColumn<Student, String> dateOfBirth;


    @FXML
    public void initialize(){
        System.out.println("\nKreiranje Studenata...");
        studentList = new ArrayList<>(GlavnaDatoteke.dohvatiStudente());
        ObservableList<Student> studentObservableSet = FXCollections.observableList(studentList);
        studentTableView.setItems(studentObservableSet);

        nameOfStudent.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIme()));
        LastNameOfStudent.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPrezime()));
        JMBAGofStudent.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getJmbag()));

        dateOfBirth.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = formatter.format(cellData.getValue().getDatumRodjenja());
            return new SimpleStringProperty(formattedDate);
        });
    }
    @FXML
    protected void onSearchButtonClick() {
        String enteredName = nameTextField.getText();
        String enteredLastName = lastNameTextField.getText();
        String enteredJMBAG = JMBAGTextField.getText();
        String chosenDate = getDate(datePicker.getValue());
        List<Student> filteredList = studentList;

        filteredList = imeFilter(enteredName, filteredList);
        filteredList = prezimeFilter(enteredLastName, filteredList);

        if(!enteredJMBAG.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(s -> s.getJmbag().toLowerCase().contains(enteredJMBAG.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }

        if(chosenDate != null){
            filteredList= filteredList.stream().filter(s->s.getDatumRodjenja().toString().equals(chosenDate)).collect(Collectors.toList());
        }
        studentTableView.setItems(FXCollections.observableList(filteredList));

    }

    private String getDate(LocalDate localDate){
        if(localDate!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }
        return null;
    }

}
