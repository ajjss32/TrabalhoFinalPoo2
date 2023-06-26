import RecuperacaoDados.DAO;
import br.com.caelum.stella.validation.CPFValidator;
import entity.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {
    @FXML
    private Label cadastroMessageLabel;
    @FXML
    private Label cpfMessageLabel;
    @FXML
    private Label emailMessageLabel;
    @FXML
    private TextField cpfTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField enderecoTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField senhaPassField;

    @FXML
    public void cadastrar(ActionEvent e) {

        if(!verificarCpf(cpfTextField.toString())){
            cpfMessageLabel.setText("Informe um CPF válido!");
        } else cpfMessageLabel.setText("");

        if(!verificarEmail(emailTextField.toString())){
            emailMessageLabel.setText("Informe um e-mail válido!");
        } else emailMessageLabel.setText("");

        if(verificarCpf(cpfTextField.toString()) && verificarEmail(emailTextField.toString())){
            if (!(emailTextField.getText().isBlank() && senhaPassField.getText().isBlank() && cpfTextField.getText().isBlank() && nomeTextField.getText().isBlank() && enderecoTextField.getText().isBlank())){
                Cliente cliente = new Cliente(cpfTextField.getText(),nomeTextField.getText(),enderecoTextField.getText(),emailTextField.getText(),senhaPassField.toString());
                DAO.salvarDados(cliente);

                emailTextField.clear();
                senhaPassField.clear();
                cpfTextField.clear();
                nomeTextField.clear();
                enderecoTextField.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cadastro Cliente");
                alert.setHeaderText(null);
                alert.setContentText("Cliente Cadastrado Com Sucesso!");
                alert.showAndWait();

                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("criarEvento.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    Stage currentStage = (Stage) cadastroMessageLabel.getScene().getWindow();
                    currentStage.close();
                } catch (IOException error) {
                    error.printStackTrace();
                }
            }
            else {
                cadastroMessageLabel.setText("Por favor,preencha todos os campos!");
            }
        }
    }

    private static boolean verificarCpf(String cpf) {
        try{
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean verificarEmail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
