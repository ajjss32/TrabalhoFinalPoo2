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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
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
    EditarEventoController editarEventoController = new EditarEventoController();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adicionarEventoChamado();
        tableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                Eventos eventoSelecionado = tableView.getSelectionModel().getSelectedItem();

                if (eventoSelecionado != null) {
                    int id = eventoSelecionado.getId();
                    String nomeEvento = eventoSelecionado.getTipo();
                    String endereco = eventoSelecionado.getEndereco();
                    String descricao = eventoSelecionado.getDescricao();
                    String status = eventoSelecionado.getStatus();
                    String nomeCliente = eventoSelecionado.getClienteBySolicitanteFk().getNome();
                    String nomeFuncionario = eventoSelecionado.getFuncionarioByResponsavelFk().getNome();

                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("editarStatus.fxml"));

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        editarEventoController.mostraInformacoes(nomeEvento,endereco,5,descricao,nomeCliente,nomeFuncionario);
                    } catch (IOException error) {
                        error.printStackTrace();
                    }


                }
            }
        });

    }
}
