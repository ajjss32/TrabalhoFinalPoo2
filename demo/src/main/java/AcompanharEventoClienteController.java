import RecuperacaoDados.DAO;
import entity.Eventos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AcompanharEventoClienteController implements Initializable {

    @FXML
    private TableView<Eventos> tableView;

    @FXML
    private TableColumn<Eventos, String> dataEvento;


    @FXML
    private TableColumn<Eventos, String> nomeEvento;

    @FXML
    private TableColumn<Eventos, String> nomeResponsavelEvento;

    @FXML
    private TableColumn<Eventos, String> statusEvento;

    private String cpfCliente;

    ObservableList<Eventos> eventos = FXCollections.observableArrayList();

    public TableView<Eventos> getTableView() {
        return tableView;
    }

    public void associaClientesEvento(){
        for (Eventos evento: DAO.listaEventos()){
            if (evento.getClienteBySolicitanteFk().getCpf().equals(cpfCliente)){
                eventos.add(evento);
            }
        }
    }

    @FXML
    public void criarEvento(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("criarEvento.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) tableView.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void acompanharEvento(ActionEvent event) {
    }

    public void adicionarEventoChamado() {
        associaClientesEvento();
        nomeEvento.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        statusEvento.setCellValueFactory(new PropertyValueFactory<>("status"));
        dataEvento.setCellValueFactory(new PropertyValueFactory<>("data"));
        nomeResponsavelEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFuncionarioByResponsavelFk().getNome()));
        tableView.setItems(eventos);

        tableView.setRowFactory(tv -> new TableRow<Eventos>() {
            @Override
            protected void updateItem(Eventos item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    getStyleClass().setAll("table-cell-filled");

                } else {
                    getStyleClass().setAll("cellDefault ");
                }
            }
        });
    }

    @FXML
    public void sair(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adicionarEventoChamado();
        tableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                Eventos eventoSelecionado = tableView.getSelectionModel().getSelectedItem();

                if (eventoSelecionado != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("verSolicitacao.fxml"));
                        Parent root = loader.load();
                        VerSolicitacaoController verSolicitacaoController = loader.getController();
                        verSolicitacaoController.setTabelaEventos(tableView);
                        verSolicitacaoController.setEventos(eventoSelecionado);
                        verSolicitacaoController.mostraInformacoes();
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

        statusEvento.setCellFactory(column -> new TableCell<Eventos, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    String status = getTableView().getItems().get(getIndex()).getStatus();

                    if ("Pendente".equals(status)) {
                        setStyle("-fx-background-color: #FF967A;");
                    } else if ("Finalizado".equals(status)) {
                        setStyle("-fx-background-color: #C8FFCD;");
                    } else if ("Em andamento".equals(status)) {
                        setStyle("-fx-background-color: #FEDF97;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    public void setCpfCliente(String cpf) {
        this.cpfCliente = cpf;
    }
}
