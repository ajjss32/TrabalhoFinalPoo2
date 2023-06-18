import entity.Eventos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class VerSolicitacaoController implements Initializable {
    @FXML
    private Label tipoEvento;
    @FXML
    private Label dataEvent;
    @FXML
    private Label endEvent;
    @FXML
    private Label Nconvidado;
    @FXML
    private Label DescEvent;
    @FXML
    private Label solicitanteEvent;
    @FXML
    private Label responsavelEvent;
    @FXML
    private Label statusEvent;
    private Eventos eventos;
    private TableView<Eventos> tabelaEventos;


    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    public void setTabelaEventos(TableView<Eventos> tabelaEventos) {
        this.tabelaEventos = tabelaEventos;
    }

    public void mostraInformacoes(){
        tipoEvento.setText(eventos.getTipo());
        dataEvent.setText(eventos.getData().toString());
        endEvent.setText(eventos.getEndereco());
        Nconvidado.setText(Integer.toString(eventos.getQtdpessoas()));
        DescEvent.setText(eventos.getEndereco());
        solicitanteEvent.setText(eventos.getClienteBySolicitanteFk().getNome());
        responsavelEvent.setText(eventos.getFuncionarioByResponsavelFk().getNome());
        statusEvent.setText(eventos.getStatus());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
