import ObserverPD.EventManeger;
import entity.Eventos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class EditarEventoController implements Initializable {
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
    private ChoiceBox<String> StatusEvent;
    private String[] opcoesStatus = {"Pendente","Finalizado","Em andamento"};
    @FXML
    private Button atualizarBotao;
    private Eventos eventos;
    private TableView<Eventos> tabelaEventos;


    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    public void setTabelaEventos(TableView<Eventos> tabelaEventos) {
        this.tabelaEventos = tabelaEventos;
    }

    public void mostraInformacoes(){
        this.tipoEvento.setText(eventos.getTipo());
        dataEvent.setText(eventos.getData().toString());
        endEvent.setText(eventos.getEndereco());
        Nconvidado.setText(Integer.toString(eventos.getQtdpessoas()));
        DescEvent.setText(eventos.getEndereco());
        solicitanteEvent.setText(eventos.getClienteBySolicitanteFk().getNome());
        responsavelEvent.setText(eventos.getFuncionarioByResponsavelFk().getNome());
    }

    public void atualizarStatus(ActionEvent e){

            EventManeger eventManeger = new EventManeger();
            eventManeger.atualizarStatusEvento(eventos.getId(),StatusEvent.getValue());
            tabelaEventos.refresh();

            Stage stage = (Stage) StatusEvent.getScene().getWindow();
            stage.close();

            Controller controller = new Controller();
            controller.acompanharEvento(e);

            Stage currentStage = (Stage) tabelaEventos.getScene().getWindow();
            currentStage.close();
        }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        StatusEvent.getItems().addAll(opcoesStatus);
    }
}
