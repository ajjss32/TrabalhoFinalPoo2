package Controllers;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AcompanharEventoController implements Initializable {

    @FXML
    private TableView<Eventos> tableView;

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
    @FXML
    private Button sair;
    ObservableList<Eventos> eventos = FXCollections.observableArrayList(DAO.listaEventos());

    public void setStatusEvento(String estilo) {
        statusEvento.setStyle(estilo);
    }

    public TableView<Eventos> getTableView() {
        return tableView;
    }

    public void adicionarEventoChamado() {
        nomeEvento.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        statusEvento.setCellValueFactory(new PropertyValueFactory<>("status"));
        idEvento.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeClienteEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClienteBySolicitanteFk().getNome()));
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
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/editarStatus.fxml"));
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
    @FXML
    public void sair(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) sair.getScene().getWindow();
            currentStage.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }


}
