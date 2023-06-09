import entity.Eventos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class EditarEventoController implements Initializable {
    @FXML
    private Label tipoEvento;
    @FXML
    private Label dataEvent;
    @FXML
    private Label endEvent;
    @FXML
    private Label NConvidado;
    @FXML
    private Label DescEvente;
    @FXML
    private Label solicitanteEvent;
    @FXML
    private Label resposavelEvent;
    @FXML
    private TextField StatusEvent;

    public void mostraInformacoes(String tipoEvento,String end,int qtd,String descricao,String cliente,String responsavel){

        this.tipoEvento.setText(tipoEvento);
        //dataEvent.setText(data);
        endEvent.setText(end);
        NConvidado.setText(Integer.toString(qtd));
        DescEvente.setText(descricao);
        solicitanteEvent.setText(cliente);
        resposavelEvent.setText(responsavel);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
