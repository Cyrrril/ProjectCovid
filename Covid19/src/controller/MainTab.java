package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import objects.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainTab {

    @FXML
    public WebView webViewData;

    @FXML
    public Label labelItineraire;

    @FXML
    public ListView<String> linkListView;

    @FXML
    private Label dateLabel;

    @FXML
    private TextField idRegion;

    @FXML
    private TextField idDpt;

    @FXML
    private TextField idCommune;

    @FXML
    private TextField idCp;

    @FXML
    private DatePicker idDate;

    @FXML
    private DatePicker idDateHisto;

    @FXML
    private RadioButton yesRadio;

    @FXML
    private RadioButton noRadio;

    @FXML
    private TextField idC;

    @FXML
    private TextField idDistance;

    @FXML
    private TextField idNbContamines;

    @FXML
    private TextField idNbDeces;

    @FXML
    private TextField idNbGueris;

    @FXML
    private TableView<Commune> idTable;

    @FXML
    private ListView<String> idListe;

    @FXML
    private TextField idCommuneDep;

    @FXML
    private TextField idCommuneArr;

    @FXML
    private TableColumn<Commune, String> idTableRegion;

    @FXML
    private TableColumn<Commune, String> idTableDepartement;

    @FXML
    private TableColumn<Commune, String> idTableNomCommune;

    @FXML
    private TableColumn<Commune, Integer> idTableCodePostal;

    @FXML
    private TableColumn<Commune, String>idTableDateConf;

    @FXML
    private TableColumn<Commune, String> idTableDateDeconf;

    @FXML
    private TableColumn<Commune, String> idTableConfinee;

    @FXML
    private Label lblContamines;

    @FXML
    private Label lblDeces;

    @FXML
    private Label lblGueris;

    @FXML
    public WebView webView;

    @FXML
    private ComboBox<Commune> idCombobox;

    private final List<Commune> listeCommunes = new ArrayList<Commune>();

    private final List<ComConf> listeComConf = new ArrayList<ComConf>();

    private final List<ComNonConf> listeComNonConf = new ArrayList<ComNonConf>();

    public MainTab() {
    }

    /* Partie Communes ---------------------------------------------------------------------------------------- */

    @FXML
    void ajouter(ActionEvent event) {
        try{
            if (yesRadio.isSelected()){
                ComConf cf = new ComConf(idRegion.getText(),idDpt.getText(), idCommune.getText(), Integer.parseInt(idCp.getText()), idDate.getValue());
                listeComConf.add(cf);
                idTable.getItems().add(cf);
                idCommune.clear();
                idCp.clear();
                idDpt.clear();
                idRegion.clear();
                idDate.getEditor().clear();
            } else if (noRadio.isSelected()) {
                ComNonConf cnf = new ComNonConf(idRegion.getText(), idDpt.getText(),idCommune.getText(), Integer.parseInt(idCp.getText()),idDate.getValue());
                listeComNonConf.add(cnf);
                idTable.getItems().add(cnf);
                idCommune.clear();
                idCp.clear();
                idDpt.clear();
                idRegion.clear();
                idDate.getEditor().clear();
            }
        }
        catch(Exception e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Echec de l'ajout de la commune !");
            errorAlert.setContentText("Vous n'avez pas saisi tous les champs !\n(Le code postal ne doit contenir que des entiers) ");
            errorAlert.showAndWait();
        }


        updateCommunes();
    }

    void updateCommunes() {
        listeCommunes.clear();
        for(ComNonConf cnf : listeComNonConf){
            listeCommunes.add((Commune)cnf);
        }
        for(ComConf cf : listeComConf){
            listeCommunes.add((Commune)cf);
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        if(idTable.getSelectionModel().getSelectedItem() == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Echec de la supression !");
            errorAlert.setContentText("Vous n'avez pas sélectionné de communes dans la liste ! ");
            errorAlert.showAndWait();
        }
        else{
            Commune c = idTable.getSelectionModel().getSelectedItem();
            idTable.getItems().remove(c);
            if (c instanceof ComConf) {
                listeComConf.remove(c);
                updateCommunes();
            } else if (c instanceof ComNonConf) {
                listeComNonConf.remove(c);
                updateCommunes();
            }
        }

    }

    @FXML
    void confiner(ActionEvent event) {
        if(idTable.getSelectionModel().getSelectedItem() == null || idTable.getSelectionModel().getSelectedItem() instanceof ComConf){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Echec du confinement !");
            errorAlert.setContentText("Vous n'avez pas sélectionné de communes dans la liste !\nOu vous avez sélectionné une commune déjà confiné ! ");
            errorAlert.showAndWait();
        }
        else {
            if (idTable.getSelectionModel().getSelectedItem() instanceof ComNonConf) {
                Commune c = idTable.getSelectionModel().getSelectedItem();
                for (Commune com : listeCommunes) {
                    if (com == c) {
                        ComConf cf = new ComConf(com.getNomRegion(), com.getNomDpt(), com.getNomCommune(), com.getCodePostal(), LocalDate.now());
                        idTable.getItems().remove(c);
                        idTable.getItems().add(cf);
                        listeComNonConf.remove(com);
                        listeComConf.add(cf);
                    }
                }
            }

            updateCommunes();
        }
    }


    @FXML
    void deconfiner(ActionEvent event) {
        if (idTable.getSelectionModel().getSelectedItem() == null || idTable.getSelectionModel().getSelectedItem() instanceof ComNonConf) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Echec du déconfinement !");
            errorAlert.setContentText("Vous n'avez pas sélectionné de communes dans la liste !\nOu vous avez sélectionné une commune déjà déconfiné ! ");
            errorAlert.showAndWait();
        } else {
            if (idTable.getSelectionModel().getSelectedItem() instanceof ComConf) {
                Commune c = idTable.getSelectionModel().getSelectedItem();
                for (Commune com : listeCommunes) {
                    if (com == c) {
                        ComNonConf cnf = new ComNonConf(com.getNomRegion(), com.getNomDpt(), com.getNomCommune(), com.getCodePostal(), LocalDate.now());
                        idTable.getItems().remove(c);
                        idTable.getItems().add(cnf);
                        listeComConf.remove(com);
                        listeComNonConf.add(cnf);
                    }
                }
            }

            updateCommunes();
        }
    }

    @FXML
    void historique(ActionEvent event) {
        if (idTable.getSelectionModel().getSelectedItem() == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Echec de l'ajout d'historique !");
            errorAlert.setContentText("Vous n'avez pas sélectionné de communes dans la liste ! ");
            errorAlert.showAndWait();
        } else {
            for (Commune com : listeCommunes) {
                Commune c = idTable.getSelectionModel().getSelectedItem();
                if (com == c) {
                    int index = idTable.getSelectionModel().getSelectedIndex();
                    Historique h = new Historique(Integer.parseInt(idNbContamines.getText()), Integer.parseInt(idNbDeces.getText()), Integer.parseInt(idNbGueris.getText()));
                    com.getListeHisto().add(h);
                    com.getListeDateHisto().add(h.getDate());
                    h.setDate(idDateHisto.getValue());
                    idNbContamines.clear();
                    idNbDeces.clear();
                    idNbGueris.clear();
                    idDateHisto.getEditor().clear();
                    idListe.getItems().add("Historique " + c.getNomCommune() + " le " + h.getDate());
                }
            }
        }
    }


    @FXML
    void detail(ActionEvent event) {
        if (idListe.getSelectionModel().getSelectedItem() == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Echec de l'affichage des détails d'un historique !");
            errorAlert.setContentText("Vous n'avez pas sélectionné de communes dans la liste ! ");
            errorAlert.showAndWait();
        } else {
            String s = idListe.getSelectionModel().getSelectedItem();
            for (Commune c : listeCommunes) {
                for (Historique h : c.getListeHisto()) {
                    if (s.contains(h.getDate().toString())) {
                        lblContamines.setText(String.valueOf(h.getNbContaminations()));
                        lblDeces.setText(String.valueOf(h.getNbDeces()));
                        lblGueris.setText(String.valueOf(h.getNbGuerison()));
                    }
                }
            }
        }
    }

    @FXML
    void lier(ActionEvent event) {
        if(idTable.getSelectionModel().getSelectedItem() == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Echec de l'ajout d'un chemin !");
            errorAlert.setContentText("Vous n'avez pas sélectionné de communes dans la liste ! ");
            errorAlert.showAndWait();
        }
        else{
            Commune c = idTable.getSelectionModel().getSelectedItem();
            Edge edge = null;

            ObservableList<Commune> tab = idTable.getItems();
            for (Commune com : tab) {
                if (com.getNomCommune().equals(idC.getText())) {
                    edge = c.lierCommune(com, Integer.parseInt(idDistance.getText()));
                }
            }

            assert edge != null;

            linkListView.getItems().add(c.getVertex().toString() + " --> " + edge.getTargetVertex().toString() + " (" + edge.getWeight() + "km)");

        }
    }

    /* Partie Itinéraire -------------------------------------------------------------------------------------------- */

    public Commune chercherCommune(String nomCom){
        ObservableList<Commune> tab = idTable.getItems();
        Commune res = null;
        for(Commune c : tab) {
            if (c.getNomCommune().equals(nomCom)) {
                res = c;
            }
        }
        return res;
    }

    @FXML
    public void chercher(ActionEvent event) {
        try{
            Commune cD = chercherCommune(idCommuneDep.getText());
            Commune cA = chercherCommune(idCommuneArr.getText());
            List<Vertex> itineraire = cD.shortestPath(cA);
            StringBuilder res = new StringBuilder();

            for (Vertex vertex : itineraire) {
                res.append(vertex.toString()).append(" --> ");
            }

            res.replace(res.length() - 5, res.length() - 1, "");

            labelItineraire.setText(res.toString());
        }
        catch (Exception e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Echec de la recherche du plus court chemin !");
            errorAlert.setContentText("Veuillez donner une commune de départ et d'arrivée ! ");
            errorAlert.showAndWait();
        }
    }

    /* Stats -------------------------------------------------------------------------------------------------------- */

    @FXML
    private PieChart idPieChart;

    @FXML
    private AreaChart<String, Integer> idAreaChart;

    private XYChart.Series<String,Integer> seriesContamines = new XYChart.Series<String, Integer>();
    private XYChart.Series<String,Integer> seriesDeces = new XYChart.Series<String, Integer>();
    private XYChart.Series<String,Integer> seriesGueris = new XYChart.Series<String, Integer>();

    @FXML
    public void afficher(ActionEvent event) {
        try {
            idPieChart.setData(FXCollections.observableArrayList(new PieChart.Data("Confinée ", listeComConf.size()), new PieChart.Data("Non Confinée ", listeComNonConf.size())));
            seriesContamines.getData().clear();
            seriesDeces.getData().clear();
            seriesGueris.getData().clear();
            Commune c = idCombobox.getSelectionModel().getSelectedItem();

            for (Historique h : c.getListeHisto()) {
                seriesContamines.getData().add(new XYChart.Data<String, Integer>(h.getDate().toString(), h.getNbContaminations()));
                seriesDeces.getData().add(new XYChart.Data<String, Integer>(h.getDate().toString(), h.getNbDeces()));
                seriesGueris.getData().add(new XYChart.Data<String, Integer>(h.getDate().toString(), h.getNbGuerison()));
            }

            idAreaChart.getData().addAll(seriesContamines, seriesDeces, seriesGueris);
            seriesContamines.setName("Nombre de contaminés");
            seriesDeces.setName("Nombre de décès");
            seriesGueris.setName("Nombre de guéris");
        }
        catch(Exception e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Echec de l'affichage des statistiques !");
            errorAlert.setContentText("Veuillez créer des communes pour afficher des statistiques! ");
            errorAlert.showAndWait();
        }
    }

    @FXML
    void clear(ActionEvent event){
        seriesContamines.getData().clear();
        seriesDeces.getData().clear();
        seriesGueris.getData().clear();
        idAreaChart.getData().clear();
    }


    /* Fonction Initialise ------------------------------------------------------------------------------------------ */
    public void initialize() {

        /* Initialise le tableau */

        idTableNomCommune.setCellValueFactory(new PropertyValueFactory<>("nomCommune"));
        idTableCodePostal.setCellValueFactory(new PropertyValueFactory<>("codePostal"));
        idTableConfinee.setCellValueFactory(new PropertyValueFactory<>("isConfine"));
        idTableDateConf.setCellValueFactory(new PropertyValueFactory<>("dateConfinement"));
        idTableDateDeconf.setCellValueFactory(new PropertyValueFactory<>("dateDeconfinement"));
        idTableRegion.setCellValueFactory(new PropertyValueFactory<>("nomRegion"));
        idTableDepartement.setCellValueFactory(new PropertyValueFactory<>("nomDpt"));

        /* Initialise les radios du formulaire */

        ToggleGroup confineRadio = new ToggleGroup();

        yesRadio.setToggleGroup(confineRadio);
        yesRadio.setUserData("yes");
        yesRadio.setSelected(true);

        noRadio.setToggleGroup(confineRadio);
        noRadio.setUserData("no");

        confineRadio.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            if (newToggle.getUserData().toString().equals("no")) {
                dateLabel.setText("Date de déconfinement");
            } else {
                dateLabel.setText("Date de confinement");
            }
        });

        /* Initialise la map et les stats sur la France */

        WebEngine engine = webView.getEngine();
        engine.load(getClass().getResource("/index.html").toString());

        WebEngine engineData = webViewData.getEngine();
        engineData.load(getClass().getResource("/data-france.html").toString());

        /* Initialise la liste des communes */

        ObservableList<Commune> comboCommunes = FXCollections.observableList(listeCommunes);
        idCombobox.setItems(comboCommunes);
    }

    public void generateCSV(Event event) throws IOException {
        FileWriter fw = new FileWriter("listeComConf.csv", false);
        for(ComConf cf : listeComConf){
            fw.write(String.valueOf(cf.getCodePostal()));
            fw.write("\n");
        }
        fw.close();
    }
}
