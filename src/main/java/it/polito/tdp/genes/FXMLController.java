/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event){
    	try {
    		double s = Double.parseDouble(txtSoglia.getText());
    		if(s <= model.getMin() || s >= model.getMax()) {
    			txtResult.appendText("Il valore è esterno all'intervallo ("+model.getMin()+","+model.getMax()+")\n");
    		}else {
    			model.contaArchi(s);
    			txtResult.appendText("Il numero di valori maggiori"
    					+ " e inferiori della soglia sono: "+model.getMagS()+" e "+	model.getMinS());
    		}
    	}catch(NumberFormatException e)
    	{
    	txtResult.appendText("Inserire un valore corretto numerico \n");
    	}  
    }

    @FXML
    void doRicerca(ActionEvent event) {
    	try {
    		double s = Double.parseDouble(txtSoglia.getText());
    		if(s <= model.getMin() || s >= model.getMax()) {
    			txtResult.appendText("Il valore è esterno all'intervallo ("+model.getMin()+","+model.getMax()+")\n");
    		}else {
    			List<Integer> cromosomi = model.calcolaPercorsoMassimo(s);
    		}
    	}catch(NumberFormatException e)
    	{
    	txtResult.appendText("Inserire un valore corretto numerico \n");
    	}  
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		this.model.creaGrafo();
		
	}
}
