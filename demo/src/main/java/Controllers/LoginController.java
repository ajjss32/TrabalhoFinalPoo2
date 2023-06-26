package Controllers;

import RecuperacaoDados.DAO;
import Proxy.TelaEscolhida;
import Proxy.TelaInicial;
import entity.Cliente;
import entity.Pessoa;
import jakarta.persistence.NoResultException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button cadastroButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField SenhaField;
    private TelaInicial telaEscolhida;

    public LoginController() {
        telaEscolhida = new TelaEscolhida();
    }

    @FXML
    public void loginButton(ActionEvent e) {
        try {
            if (!(emailTextField.getText().isBlank() && SenhaField.getText().isBlank())){
                loginMessageLabel.setText("oi");
                validateLogin();
            }
            else {
                loginMessageLabel.setText("Por favor,preencha todos os campos!");
            }
        }catch (NoResultException erro){
            loginMessageLabel.setText("Credenciais inválidas!");
        }

    }

    public void validateLogin(){
        Pessoa pessoa = DAO.login(emailTextField.getText(), SenhaField.getText());
        loginMessageLabel.setText("Oi "+pessoa.getNome());
        TelaEscolhida proxy = (TelaEscolhida) telaEscolhida;
        proxy.setPessoa(pessoa);
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader((getClass().getResource(telaEscolhida.tela())));
            root = loader.load();

            if(pessoa instanceof Cliente){
                AcompanharEventoClienteController acompanharEventoController = loader.getController();

                // Define o CPF do cliente no controlador
                acompanharEventoController.setCpfCliente(pessoa.getCpf());
                acompanharEventoController.adicionarEventoChamado();

            }

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) loginMessageLabel.getScene().getWindow();
            currentStage.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void cadastrar(){
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/views/cadastro.fxml")));
            root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) loginMessageLabel.getScene().getWindow();
            currentStage.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
