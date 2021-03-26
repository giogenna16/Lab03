/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class FXMLController {
	
	private Model model;
	
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboBoxSelect;

    @FXML
    private TextArea txtInserisci;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtResult;

    @FXML
    private Label lblErrors;

    @FXML
    private Button btnClear;

    @FXML
    private Label lblTime;
    
    @FXML
    private ComboBox<String> comboBoxTipoRicerca;

    @FXML
    void handleClear(ActionEvent event) {
    	
    	this.model.reset();
    	this.txtInserisci.clear();
    	this.txtResult.clear();
    	this.comboBoxSelect.setDisable(false);
    	this.btnSpellCheck.setDisable(false);
    	this.comboBoxTipoRicerca.setDisable(false);
    	this.txtInserisci.setEditable(true);
    	this.btnClear.setDisable(true);
    	this.lblErrors.setTextFill(Color.BLACK);
    	this.lblErrors.setText("Number of errors in the text:...");
    	this.lblTime.setText("Time for the spell check:...");

    }
    
    @FXML
    void handleSpellCheck(ActionEvent event) {
    	String inputText= this.txtInserisci.getText().toLowerCase();
    	String language= this.comboBoxSelect.getValue();
    	String searchType= this.comboBoxTipoRicerca.getValue();
    	if(language==null) {
    		this.txtResult.setText("Select a language!");
    		
    	}else {
    		if(inputText== null) {
    	
    		this.txtResult.setText("Enter at least one word!");
    	}if(searchType==null){
    		this.txtResult.setText("Select a search type!");
    	}
    		else {
    	
        this.comboBoxTipoRicerca.setDisable(true);
    	this.comboBoxSelect.setDisable(true);
    	this.btnSpellCheck.setDisable(true);
    	this.txtInserisci.setEditable(false);
    	this.btnClear.setDisable(false);
    	
    	int nErr=-1;
    	List<String> inputTextList= new ArrayList<>();
    	this.model.loadDictionary(language);
    	
    	inputText= inputText.replaceAll("['.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", " ");
    	StringTokenizer st = new StringTokenizer(inputText, " ");
	
    	while(st.hasMoreTokens()) {
    		inputTextList.add(st.nextToken());
    		
    	}
    	
    	if(searchType.equals("Dichotomic search"))
    	     this.model.spellCheckTextDichotomic(inputTextList);
    	if(searchType.equals("Linear search"))
    		this.model.spellCheckTextLinear(inputTextList);
    	if(searchType.equals("List.contains()"))
    		this.model.spellCheckText(inputTextList);
    	
    	this.txtResult.setText(this.model.paroleSbagliate());
    	nErr=this.model.numeroErrori();
    	
    	if(nErr==0) {
    		this.lblErrors.setTextFill(Color.GREEN);
    		this.lblErrors.setText("The text does not contain errors");
    	}
    	if(nErr==1) {
    		this.lblErrors.setTextFill(Color.RED);
    		this.lblErrors.setText("The text contains "+ nErr+" error");
    	}
    	if(nErr>1) {
    		this.lblErrors.setTextFill(Color.RED);
    		this.lblErrors.setText("The text contains "+ nErr+" errors");
    	}
    	
    	
    	this.lblTime.setText("Spell check completed in "+(this.model.getTempo()/1E9)+" s");
    	
    	}
    	}
    }

    public void setModel(Model model) {
    	this.model=model;
    	this.comboBoxSelect.getItems().addAll("English", "Italiano");
    	this.comboBoxTipoRicerca.getItems().addAll("List.contains()", "Linear search", "Dichotomic search");
    }

    @FXML
    void initialize() {
        assert comboBoxSelect != null : "fx:id=\"comboBoxSelect\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInserisci != null : "fx:id=\"txtInserisci\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"handleSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}



