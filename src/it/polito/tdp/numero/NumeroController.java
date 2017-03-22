/**
 * Sample Skeleton for 'Numero.fxml' Controller Class
 */

package it.polito.tdp.numero;

import java.net.URL;
//all'interno del controller abbiamo implementato due metodi
//dentro i metodi del controller c'è la logica ma mantengo l'interfaccia utente in uno stato pulito
//tuttavia c'è lo stato del gioco
//non poter testare la logica del gioco se non giocando( mi infastidisce)
//se non posso inserire due volte lo stesso numero, vado a cambiare le regole
//voglio una classe esterna al controller che abbia due o tre metodi per giocare. se voglio vedere se il gioco funziona
//faccio il main. separo la gestione dell'interfaccia utente con la logica del gioco
//tiro fuori la logica della partita con lo stato del gioco
//separo l'interfaccia dai dati: chiamo la suddetta classe "modello"
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class NumeroController {
	private int difficolta ;//scelto dalla tendina
	private int segreto;//da indovinare
	private int prova ; //tentativo utente
	private int tentativi ; //gia fatti
	private int maxTentativi;
	
	private boolean inGame;
	// mi tiene traccia dello stato del gioco
	

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxDifficolta"
    private ComboBox<Integer> boxDifficolta; // Value injected by FXMLLoader
//combobox deve contenere interi.
    //avro una lista di interi
    @FXML // fx:id="btnStartStop"
    private Button btnStartStop; // Value injected by FXMLLoader
    @FXML // fx:id="btnStartStop"
    private Button btnProva; // Value injected by FXMLLoader

    @FXML // fx:id="txtProva"
    private TextField txtProva; // Value injected by FXMLLoader

    @FXML // fx:id="lblVintoPerso"
    private Label lblVintoPerso; // Value injected by FXMLLoader

    @FXML // fx:id="lblTentativi"
    private Label lblTentativi; // Value injected by FXMLLoader

    @FXML // fx:id="gbTentativi"
    private ProgressBar gbTentativi; // Value injected by FXMLLoader
//ho vari frammenti di codice che vanno a modificare l'interfaccia utente: queste 
    //sono in corrispondenza del ingame
    //prendo tutti questi frammenti di codice e li metto a fattor comune
 /**
  * Modifica lo stato della partita da attiva a non attiva e viceversa
  * aggiornando di conseguenza tutti gli elementi dell'interfaccia utente( label, choise box, bottoni ecc..)
  * 
  * 
  * @param state(@code true) per iniziare la partita (@code false) per terminarla
  */
	private void changeGameState(boolean state)
    {
    	   //ho creato un metodo che cambia lo stato del gioco e internamente ha tutto cio da aggiornare
    inGame=state;
    //se non stavo giocando prima ora voglio giocare perciò cambia l'interfaccia
    if(inGame==false){
    
	btnStartStop.setText("gioca");
	txtProva.setDisable(true); //cambio l'interfaccia
	btnProva.setDisable(true); //cambio l'interfaccia	
	boxDifficolta.setDisable(false);//abilito
	//comboboxDifficoltà.setValue();
    } else {
    	//String.format permette di formattare la stringa
    	lblTentativi.setText(String.format("Tentativi :%d/%d", tentativi, maxTentativi));
    	// quando il gioco inizia il bottone startStop deve diventare abbandona
    	gbTentativi.setProgress(0.0);
    	lblVintoPerso.setText("");
    	//cancello con la stringa vuota cio che eventualemtne c'è
    	//cancello: hai vinto
    	boxDifficolta.setDisable(true);
    	btnStartStop.setText("Abbandona");
    	
    	txtProva.setDisable(false);//rendo attivo
    	txtProva.setText("");
    	//inserisco 3 e quando rinizio si azzera automaticamente
    btnProva.setDisable(false);
    }
    
    }
    @FXML
    void doProva(ActionEvent event) {
    	//deve vedere il numero inserito dall'utente
    	//il numero inserito dall'utente è giusto perciò la partita
    	//finisce perche ho vinto. senno seleziono il numero sbagliato solo che ho perso
    	//perche era l'ultimo tentativo
    	//senno se ho messo un numero e ho ancora tentativi posso dire numero che ho insierito
    	//era troppo alto o basso
    	try{
    		//try catch perchè mi aspetto di inserire un intero
    	prova= Integer.parseInt(txtProva.getText());
    	//inserisco un numero e converto la stringa in intero
    	//getText non ritorna mai null, non ho mai problemi di nullPointerException
    	} catch(NumberFormatException e){
    		//se non riesco a convertire il numero in un intero scrivo un messaggio di formato errato
    	//solitamente try catch va semore messo nel caso in cui si trattano inserimenti che potrebbero 
    		//generare valori nulli
    		lblTentativi.setText("Inserisci un numero");
    	return ;
    	}
    	tentativi ++;
    	lblTentativi.setText(String.format("Tentativi %d/%d", tentativi, maxTentativi));
    	gbTentativi.setProgress((double) tentativi/maxTentativi);
    	
    	if(prova==segreto){
    		lblVintoPerso.setText("Hai vinto");
    		changeGameState(false);
    		//hai vinto
    	}else if(tentativi==maxTentativi){
    		//hai perso: esaurito i tentativi
    		lblVintoPerso.setText("Hai esaurito i tentativi il numero era " +segreto);
    		changeGameState(false);
    	}
    	else if(prova <segreto){
    		//troppo basso
    		lblVintoPerso.setText("Troppo basso");
    	}
    	else {//(prova>segreto)
    		lblVintoPerso.setText("Troppo alto");
    	}
    	}

    @FXML
    void doStartStop(ActionEvent event) {
    	//il bottone serve quando inizia e quando finisce percio
    	// il mio controller deve sapere quando inizio e quando finisco
    	// definisco un boolean 
    	if(inGame)
    		// abbandona
    	
    		 {
    		lblVintoPerso.setText("Hai abbandonato");
    	lblTentativi.setText("il numero era "+ segreto);
    	boxDifficolta.setDisable(false);
    	btnStartStop.setText("inizia");
    	//devo disabilitare il bottone prova e la txtProva
    	txtProva.setDisable(true);
    	btnProva.setDisable(true);
    		inGame= false;
    		}
    	
    	else {
    		//inizia
    		if(boxDifficolta.getValue()==null)
    			//boxDifficolta.getValue() restituisce il valore selezionato
    		{
    			lblTentativi.setText("Seleziona un valore");
    			return;
    		}
    		difficolta=boxDifficolta.getValue();
    	//mi dice la difficolta che è stata scelta dall'utente: fra i vari numeri difficolta preddisposti da me
    	//scelgo un num casuale
    	segreto= (int)(Math.random()*difficolta) +1; //estrae un numero casuale tra uno e 1000
    	tentativi=0;
    	// fino adesso non ho fatto nessun tentativo
    	maxTentativi = (int)(Math.log(difficolta)/Math.log(2.0)+1);
    	//maxTentativi math.log(difficolta/ math.log(2.0)
    	//equinvale a fare logaritmo in base due di difficoltà
    changeGameState(true);
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	//viene chiamato automaticamente appena il controller viene creato.
    	//prima che l'utente interagisca con l'interfaccia
        assert boxDifficolta != null : "fx:id=\"boxDifficolta\" was not injected: check your FXML file 'Numero.fxml'.";
        assert btnStartStop != null : "fx:id=\"btnStartStop\" was not injected: check your FXML file 'Numero.fxml'.";
        assert btnProva != null : "fx:id=\"btnProva\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtProva != null : "fx:id=\"txtProva\" was not injected: check your FXML file 'Numero.fxml'.";
        assert lblVintoPerso != null : "fx:id=\"lblVintoPerso\" was not injected: check your FXML file 'Numero.fxml'.";
        assert lblTentativi != null : "fx:id=\"lblTentativi\" was not injected: check your FXML file 'Numero.fxml'.";
        assert gbTentativi != null : "fx:id=\"gbTentativi\" was not injected: check your FXML file 'Numero.fxml'.";
        boxDifficolta.getItems().addAll(10,100,1000);//puo registrare il livello difficolta
        //e i numero di tentastivi per quel livello di difficolta( è un espansione possibile
        // mi restituisce una lista di elementi. all'interno di addall aggiungo 10/100/1000
        //getitems fa le operazioni della lista.
        inGame= false; //predispongo il gioco in modo che non sia ancora partito

    }
}
