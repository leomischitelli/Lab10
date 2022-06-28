package it.polito.tdp.rivers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private Button btnSimula;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtFMed;

    @FXML
    private TextField txtK;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtStartDate;

    @FXML
    void doScegliFiume(ActionEvent event) {
    	River river = boxRiver.getValue();
    	List<Flow> flows = model.getInfoRiver(river);
    	
    	LocalDate first = flows.get(0).getDay();
    	LocalDate last = flows.get(flows.size() - 1).getDay();
    	
    	txtStartDate.setText(first.toString());
    	txtEndDate.setText(last.toString());
    	txtNumMeasurements.setText(Integer.toString(flows.size()));	
    	Double d = river.getFlowAvg();
    	
    	txtFMed.setText(Double.toString(river.getFlowAvg()));
    	

    }

    @FXML
    void doSimula(ActionEvent event) {
    	River river = boxRiver.getValue();
    	if(river == null) {
    		txtResult.setText("Nessun fiume selezionato!");
    		return;
    	}
    	try {
    		long k = Long.parseLong(txtK.getText());
    		if(k<0) {
        		txtResult.setText("Inserire un numero intero maggiore di 0!");
        		return;
        	}
    		model.simula(k, river);
    		txtResult.clear();
    		txtResult.appendText("Numero giorni in cui non è stata garantita erogazione minima: " + model.getFallimenti() + "\n");
    		txtResult.appendText("Capienza media: " + model.getAvgCapienza() + "\n");
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserire un numero (intero, maggiore di 0)");
    	}
    	

    }

    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";

    }



    
    public void setModel(Model model) {
    	this.model = model;
    	ObservableList<River> lista = FXCollections.observableArrayList(model.getAllRivers());
    	boxRiver.setItems(lista);
    }
}
