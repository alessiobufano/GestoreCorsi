package it.polito.tdp.corsi;

import java.net.URL;
import java.util.*;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
import it.polito.tdp.corsi.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPeriodo;

    @FXML
    private TextField txtCorso;

    @FXML
    private Button btmCorsiPerPeriodo;

    @FXML
    private Button btmStudentiCorso;

    @FXML
    private Button btmNumeroStudenti;

    @FXML
    private Button btmDivisioneStudenti;

    @FXML
    private TextArea txtRisultato;

    @FXML
    void doCorsiPerPeriodo(ActionEvent event) {
    	
    	txtRisultato.clear();
    	String pdString = txtPeriodo.getText();
    	int pd;
    	
    	try {
    		pd = Integer.parseInt(pdString);
    	} catch(NumberFormatException e) {
    		txtRisultato.setText("Errore! Devi inserire un numero intero che sia 1 o 2\n");
    		return;
    	}
    	
    	if(pd!=1 && pd!=2)
    	{
    		txtRisultato.setText("Errore! Devi inserire un numero intero che sia 1 o 2\n");
    		return;
    	}
    	
    	List<Corso> corsi = this.model.getCorsiPerPeriodo(pd);
    	for(Corso c : corsi)
    		txtRisultato.appendText(c.toString()+"\n");
    }

    @FXML
    void doStampaNumeroStudenti(ActionEvent event) {

    	txtRisultato.clear();
    	String pdString = txtPeriodo.getText();
    	int pd;
    	
    	try {
    		pd = Integer.parseInt(pdString);
    	} catch(NumberFormatException e) {
    		txtRisultato.setText("Errore! Devi inserire un numero intero che sia 1 o 2\n");
    		return;
    	}
    	
    	if(pd!=1 && pd!=2)
    	{
    		txtRisultato.setText("Errore! Devi inserire un numero intero che sia 1 o 2\n");
    		return;
    	}
    	
    	Map<Corso, Integer> statistiche = this.model.getIscrittiPerPeriodo(pd);
    	for(Corso c : statistiche.keySet())
    		txtRisultato.appendText("Corso "+c.getNome()+", numero iscritti = "+statistiche.get(c)+"\n");
    }

    @FXML
    void doStampaStudentiCorso(ActionEvent event) {
    	
    	txtRisultato.clear();
    	String codins = txtCorso.getText();
    	
    	if(this.model.esisteCorso(codins)==false) {
    		txtRisultato.setText("Errore!! Il codice corso inserito è inesistente!");
    		return;
    	}
    	
    	List<Studente> studenti = this.model.getStudentiPerCorso(new Corso(codins, null, null, null));
    	
    	if(studenti.size()==0) {
    		txtRisultato.setText("Il corso "+codins+" non ha studenti iscritti\n");
    		return;
    	}
    	
    	for(Studente s : studenti)
    		txtRisultato.appendText(s.toString()+"\n");
    }
    
    @FXML
    void doStampaDivisione(ActionEvent event) {
    	
    	//OUTPUT del tipo Gestionale 12, Informatica 28, ...
    	
    	txtRisultato.clear();
    	String codins = txtCorso.getText();
    	
    	if(this.model.esisteCorso(codins)==false) {
    		txtRisultato.setText("Errore!! Il codice corso inserito è inesistente!");
    		return;
    	}
    	
    	//Map<String, Integer> statistiche = this.model.getDivisioneStudentiPerCorso(new Corso(codins, null, null, null));  //soluzione 1
    	
    	Map<String, Integer> statistiche = this.model.getDivisioneStudentiPerCorsoDAO(new Corso(codins, null, null, null));  //soluzione 2
    	
    	if(statistiche.size()==0) {
    		txtRisultato.setText("Il corso "+codins+" non ha studenti iscritti\n");
    		return;
    	}
    	else
    		txtRisultato.setText("Il corso "+codins+" ha\n");
    	
    	for(String s : statistiche.keySet())
    		txtRisultato.appendText(statistiche.get(s)+" studenti di "+s+"\n");
    }


    @FXML
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmCorsiPerPeriodo != null : "fx:id=\"btmCorsiPerPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmStudentiCorso != null : "fx:id=\"btmStudentiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmNumeroStudenti != null : "fx:id=\"btmNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmDivisioneStudenti != null : "fx:id=\"btmDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}    
}
