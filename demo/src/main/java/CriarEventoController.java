
import Builder.EventoBuilder;
import RecuperacaoDados.DAO;
import entity.Eventos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class CriarEventoController implements Initializable {

    @FXML
    private Label cpfLabel;

    @FXML
    private TextField tipoField;

    @FXML
    private TextField dataField;

    @FXML
    private TextField enderecoField;

    @FXML
    private TextField qtdPessoasField;

    @FXML
    private TextArea descricaoArea;

    @FXML
    private Button concluirButton;
    @FXML
    private Button sair;
    private String cpfCliente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void criarEvento(ActionEvent event) {

    }
    public void mostrarCPF(){
        cpfLabel.setText(cpfCliente);
    }
    @FXML
    public void concluir(ActionEvent event){
        String cpf = cpfCliente;
        String tipo = tipoField.getText();
        String data = dataField.getText();
        String endereco = enderecoField.getText();
        String descricao = descricaoArea.getText();
        int quantidadePessoas = Integer.parseInt(qtdPessoasField.getText());

        Eventos evento = new EventoBuilder()
                .setSolicitante(cpf)
                .setTipo(tipo)
                .setData(Date.valueOf(data))
                .setEndereco(endereco)
                .setDescricao(descricao)
                .setQtdPessoas(quantidadePessoas)
                .builder();

        DAO.salvarDados(evento);

        tipoField.clear();
        dataField.clear();
        enderecoField.clear();
        descricaoArea.clear();
        qtdPessoasField.clear();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Evento Criado");
        alert.setHeaderText(null);
        alert.setContentText("O evento foi criado com sucesso!");
        alert.showAndWait();
    }

    @FXML
    public void acompanharEvento(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("acompanharEventoCliente.fxml"));
            root = loader.load();
            AcompanharEventoClienteController acompanharEventoController = loader.getController();
            acompanharEventoController.setCpfCliente(cpfCliente);
            acompanharEventoController.adicionarEventoChamado();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) cpfLabel.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sair(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
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

    public void setCpfCliente(String cpf) {
        this.cpfCliente = cpf;
    }
}
