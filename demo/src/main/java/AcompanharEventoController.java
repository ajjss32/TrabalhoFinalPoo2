import RecuperacaoDados.DAO;
import entity.Eventos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AcompanharEventoController implements Initializable {

    @FXML
    private TableView<Eventos> tableView;

    @FXML
    private TableColumn<Eventos, Integer> convidadosEvento;

    @FXML
    private TableColumn<Eventos, String> dataEvento;

    @FXML
    private TableColumn<Eventos, String>descricacaoEvento;

    @FXML
    private TableColumn<Eventos, String> enderecoEvento;

    @FXML
    private TableColumn<Eventos, Integer>idEvento;

    @FXML
    private TableColumn<Eventos, String> nomeClienteEvento;

    @FXML
    private TableColumn<Eventos, String> nomeEvento;

    @FXML
    private TableColumn<Eventos, String> nomeResponsavelEvento;
    @FXML
    private TableColumn<Eventos, String> statusEvento;
    ObservableList<Eventos> eventos = FXCollections.observableArrayList(DAO.listaEventos());

    public void setStatusEvento(String estilo) {
        statusEvento.setStyle(estilo);
    }

    public TableView<Eventos> getTableView() {
        return tableView;
    }

    public void adicionarEventoChamado() {
        nomeEvento.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        descricacaoEvento.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        convidadosEvento.setCellValueFactory(new PropertyValueFactory<>("qtdpessoas"));
        statusEvento.setCellValueFactory(new PropertyValueFactory<>("status"));
        dataEvento.setCellValueFactory(new PropertyValueFactory<>("data"));
        enderecoEvento.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        idEvento.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeClienteEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClienteBySolicitanteFk().getNome()));
        nomeResponsavelEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFuncionarioByResponsavelFk().getNome()));
        tableView.setItems(eventos);
    }
    public void atualizarTabela(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adicionarEventoChamado();
        tableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                Eventos eventoSelecionado = tableView.getSelectionModel().getSelectedItem();

                if (eventoSelecionado != null) {

                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("editarStatus.fxml"));
                            Parent root = loader.load();

                            EditarEventoController editarEventoController = loader.getController();
                            editarEventoController.setTabelaEventos(tableView);
                            editarEventoController.setEventos(eventoSelecionado);
                            editarEventoController.mostraInformacoes();

                            tableView.refresh();
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                    } catch (IOException error) {
                        error.printStackTrace();
                    }


                }
            }
        });

    }


}
