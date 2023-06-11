import RecuperacaoDados.DAO;
import entity.Cliente;
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
            loginMessageLabel.setText("Cliente não cadastrado! ");
        }

    }

    public void validateLogin(){
        Cliente cliente = DAO.login(emailTextField.getText(), SenhaField.getText());
        loginMessageLabel.setText("Oi "+cliente.getNome());

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("cadastro.fxml"));
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
