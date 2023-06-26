package Controllers;

import RecuperacaoDados.DAO;
import br.com.caelum.stella.validation.CPFValidator;
import entity.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.sun.javafx.css.StyleClassSet.getStyleClass;

public class CadastroController implements Initializable {
    @FXML
    private Label cadastroMessageLabel;

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
        cpfTextField.setOnMouseClicked(event -> {
            cpfTextField.setText("");
            cpfTextField.setStyle("-fx-border-color: none;-fx-text-fill: black;");
        });
        if(!verificarCpf(cpfTextField.getText())){
            exibirMensagemInvalidaCPF("Informe um CPF válido!");
        } else if(DAO.verificarCpfCadastrado(cpfTextField.getText())) {
            exibirMensagemInvalidaCPF("CPF já cadastrado!");
        }
        emailTextField.setOnMouseClicked(event -> {
            emailTextField.setText("");
            emailTextField.setStyle("-fx-border-color: none;-fx-text-fill: black;");
        });

        if(!verificarEmail(emailTextField.getText())){
            exibirMensagemInvalidaEmail("Informe um e-mail válido!");
        } else if(DAO.verificarEmailCadastrado(emailTextField.getText())) {
            exibirMensagemInvalidaEmail("E-mail já cadastrado!");
        }

        if(verificarCpf(cpfTextField.getText()) && verificarEmail(emailTextField.getText()) && !DAO.verificarCpfCadastrado(cpfTextField.getText()) && !DAO.verificarEmailCadastrado(emailTextField.getText())){
            if (!(emailTextField.getText().isBlank() && senhaPassField.getText().isBlank() && cpfTextField.getText().isBlank() && nomeTextField.getText().isBlank() && enderecoTextField.getText().isBlank())){
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String senhaCriptografada = encoder.encode(senhaPassField.getText());

                Cliente cliente = new Cliente(cpfTextField.getText(),nomeTextField.getText(),enderecoTextField.getText(),emailTextField.getText(),senhaCriptografada);
                DAO.salvarDados(cliente);

                String cpfCliente = cpfTextField.getText();

                emailTextField.clear();
                senhaPassField.clear();
                cpfTextField.clear();
                nomeTextField.clear();
                enderecoTextField.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cadastro Cliente");
                alert.setHeaderText(null);
                alert.setContentText(cliente.getNome()+", seu cadastro foi realizado com sucesso!");
                alert.setGraphic(new ImageView(this.getClass().getResource("/images/sucessLogo.png").toString()));
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/styles/Alert.css").toExternalForm());
                dialogPane.getStyleClass().add("alert-message");
                alert.showAndWait();



                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/criarEvento.fxml"));
                    Parent root = loader.load();
                    CriarEventoController controller = loader.getController();
                    controller.setCpfCliente(cpfCliente);
                    controller.mostrarCPF();

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
    public void exibirMensagemInvalidaCPF(String mensagem) {
        cpfTextField.setText(mensagem);
        cpfTextField.setStyle("-fx-border-color: red;-fx-text-fill: red;");

    }
    public void exibirMensagemInvalidaEmail(String mensagem) {
            emailTextField.setText(mensagem);
            emailTextField.setStyle("-fx-border-color: red;-fx-text-fill: red;");

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
