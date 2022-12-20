package hr.java.vjezbe.glavna.search;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.glavna.GlavnaDatoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;



public class PredmetSearchController {
    private static List<Predmet> predmetList = new ArrayList<>();


    @FXML
    private TextField nazivTextField;
    @FXML
    private TextField sifraTextField;
    @FXML
    private TextField ECTSTextField;
    @FXML
    private TextField nositeljTextField;


    @FXML
    private TableView<Predmet> PredmetTableView;
    @FXML
    private TableColumn<Predmet, String> nazivOfPredmet;
    @FXML
    private TableColumn<Predmet, String> sifraOfPredmet;
    @FXML
    private TableColumn<Predmet, String> ECTSofPredmet;
    @FXML
    private TableColumn<Predmet, String> nositeljOfPredmet;


    @FXML
    public void initialize(){
        System.out.println("\nKreiranje Predmeta...");
        predmetList = new ArrayList<>(
                GlavnaDatoteke.dohvatiPredmete(
                    GlavnaDatoteke.dohvatiProfesore(),
                    GlavnaDatoteke.dohvatiStudente()
                )
        );
        ObservableList<Predmet> PredmetObservableSet = FXCollections.observableList(predmetList);
        PredmetTableView.setItems(PredmetObservableSet);
        nazivOfPredmet.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNaziv()));
        sifraOfPredmet.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSifra()));
        ECTSofPredmet.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBrojEctsBodova().toString()));
        nositeljOfPredmet.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNositelj().ImeIPrezimeProfesora()));
    }

    @FXML
    protected void onSearchButtonClick() {
        String enteredNaziv= nazivTextField.getText();
        String enteredSifra = sifraTextField.getText();
        String enteredECTS = ECTSTextField.getText();
        String enteredNositelj = nositeljTextField.getText();
        List<Predmet> filteredList = predmetList;

        if(!enteredNaziv.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(s -> s.getNaziv().toLowerCase().contains(enteredNaziv.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        if(!enteredSifra.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(s -> s.getSifra().toLowerCase().contains(enteredSifra.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        if(!enteredECTS.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(s -> s.getBrojEctsBodova().toString().toLowerCase().contains(enteredECTS.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        if(!enteredNositelj.isEmpty()){
            filteredList = filteredList.stream()
                    .filter(s -> s.getNositelj().ImeIPrezimeProfesora().toLowerCase().contains(enteredNositelj.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }

        PredmetTableView.setItems(FXCollections.observableList(filteredList));

    }
}
