package fx.ankit.pm.view;

import org.controlsfx.dialog.Dialogs;

import fx.ankit.pm.model.bean.ApplicationDtl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AppEditController {
	
	@FXML
    private TextField applicationNameField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField emailIDField;
    @FXML
    private TextField passwordField;
  
    private Stage dialogStage;
    private ApplicationDtl appDetail;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setApplicationDtl(ApplicationDtl appDetail) {
        this.appDetail = appDetail;
        
        applicationNameField.setText(appDetail.getApplicationName());
        userNameField.setText(appDetail.getUsername());
        emailIDField.setText(appDetail.getEmailID());
        passwordField.setText(appDetail.getPassword());
        
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	
        	appDetail.setApplicationName(applicationNameField.getText());
            appDetail.setUsername(userNameField.getText());
            appDetail.setEmailID(emailIDField.getText());
            appDetail.setPassword(passwordField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    @SuppressWarnings("deprecation")
	private boolean isInputValid() {
        String errorMessage = "";
        String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        
        if (applicationNameField.getText() == null || applicationNameField.getText().trim().length() == 0) {
            errorMessage += "Application Name is mandatory!\n"; 
        }
        if (userNameField.getText() == null || userNameField.getText().trim().length() == 0) {
            errorMessage += "User Name is mandatory!\n"; 
        }
        
        if (emailIDField.getText() == null || emailIDField.getText().trim().length() == 0) {
            errorMessage += "Email ID is mandatory!\n"; 
        }
        else if(!emailIDField.getText().matches(emailreg)){
        	 errorMessage += "Entered Email ID is invalid!\n"; 
        }
        
        if (passwordField.getText() == null || passwordField.getText().trim().length() == 0) {
            errorMessage += "Password is mandatory!\n"; 
        }
 
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                .title("Invalid Fields")
                .masthead("Please correct invalid fields")
                .message(errorMessage)
                .showError();
            return false;
        }
    }
}
