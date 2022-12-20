package hr.java.vjezbe.glavna.search;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
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


public class IspitSearchController {
    private static List<Ispit> ispitList = new ArrayList<>();

    @FXML
    private TextField predmetTextField;
    @FXML
    private TextField studentTextField;
    @FXML
    private TextField ocjenaTextField;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Ispit> ispitTableView;
    @FXML
    private TableColumn<Ispit, String> predmetOfIspit;
    @FXML
    private TableColumn<Ispit, String> studentOfIspit;
    @FXML
    private TableColumn<Ispit, String> ocjenaOfIspit;
    @FXML
    private TableColumn<Ispit, String> dateOfBirth;


    @FXML
    public void initialize(){
        System.out.println("\nKreiranje Ispita...");
        List<Predmet>predmeti = GlavnaDatoteke.dohvatiPredmete(GlavnaDatoteke.dohvatiProfesore(), GlavnaDatoteke.dohvatiStudente());
        ispitList = new ArrayList<>(GlavnaDatoteke.dohvatiIspite(predmeti, GlavnaDatoteke.dohvatiStudente()));
        ObservableList<Ispit> IspitObservableSet = FXCollections.observableList(ispitList);
        ispitTableView.setItems(IspitObservableSet);

        predmetOfIspit.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPredmetKojiSePolaze().getNaziv()));
        studentOfIspit.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentKojiPolazeIspit().ImeIPrezimeStudenta()));
        ocjenaOfIspit.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getOcjena())));

        dateOfBirth.setCellValueFactory(cellData -> {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
            String formattedDate = dateTimeFormatter.format(cellData.getValue().getDatumIVrijeme());
            return new SimpleStringProperty(formattedDate);
        });
    }
    @FXML
    protected void onSearchButtonClick() {
        String enteredPredmet = predmetTextField.getText();
        String enteredStudent = studentTextField.getText();
        String enteredOcjena = ocjenaTextField.getText();
        String chosenDate = getDate(datePicker.getValue());
        List<Ispit> filteredList = ispitList;


        if(!enteredPredmet.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(s -> s.getPredmetKojiSePolaze().getNaziv().toLowerCase().contains(enteredPredmet.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        if(!enteredStudent.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(s -> s.getStudentKojiPolazeIspit().ImeIPrezimeStudenta().toLowerCase().contains(enteredStudent.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        if(!enteredOcjena.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(s -> String.valueOf(s.getOcjena().getOcjena()).equals(enteredOcjena.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }

        if(chosenDate != null){
            filteredList= filteredList.stream().filter(s->s.getDatumIVrijeme().toString().contains(chosenDate)).collect(Collectors.toList());
        }
        ispitTableView.setItems(FXCollections.observableList(filteredList));

    }

    private String getDate(LocalDate localDate){
        if(localDate!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }
        return null;
    }

}
