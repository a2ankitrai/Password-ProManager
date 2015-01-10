package fx.ankit.pm.view;

import org.controlsfx.dialog.Dialogs;

import fx.ankit.pm.MainApp;
import fx.ankit.pm.model.bean.ApplicationDtl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppOverviewController {
	
	@FXML
    private TableView<ApplicationDtl> applicationsTable;
    @FXML
    private TableColumn<ApplicationDtl, String> appNameColumn;
    @FXML
    private TableColumn<ApplicationDtl, String> userNameColumn;

    @FXML
    private Label appNameLabel;
    @FXML
    private Label usernameNameLabel;
    @FXML
    private Label emailIDLabel;
    @FXML
    private Label passwordLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
	public AppOverviewController() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the appDetail table with the two columns.
    	appNameColumn.setCellValueFactory(cellData -> cellData.getValue().applicationNameProperty());
    	userNameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
    	
    	// Clear App details.
    	showApplicationDetails(null);
    	
    	// Listen for selection changes and show the person details when changed.
    	applicationsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showApplicationDetails(newValue));
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        applicationsTable.setItems(mainApp.getAppData());
    }
    
    
    /**
     * Fills all text fields to show details about the appDetail.
     * If the specified appDetail is null, all text fields are cleared.
     * 
     * @param appDetail the appDetail or null
     */
    private void showApplicationDetails(ApplicationDtl appDetail) {
        if (appDetail != null) {
            // Fill the labels with info from the appDetail object.
            appNameLabel.setText(appDetail.getApplicationName());
            usernameNameLabel.setText(appDetail.getUsername());
            emailIDLabel.setText(appDetail.getEmailID());
            passwordLabel.setText(appDetail.getPassword());
             
        } else {
            // appDetail is null, remove all the text.
        	 appNameLabel.setText("");
             usernameNameLabel.setText("");
             emailIDLabel.setText("");
             passwordLabel.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @SuppressWarnings("deprecation")
	@FXML
    private void handleDeleteApp() {
    	
        /*int selectedIndex = applicationsTable.getSelectionModel().getSelectedIndex();
        applicationsTable.getItems().remove(selectedIndex);*/
        
        int selectedIndex = applicationsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	applicationsTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Dialogs.create()
                .title("No Selection")
                .masthead("No App Selected")
                .message("Please select a Application in the table.")
                .showWarning();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new Application.
     */
    @FXML
    private void handleNewPerson() {
        ApplicationDtl tempApp = new ApplicationDtl();
        boolean okClicked = mainApp.showAppEditDialog(tempApp,"Add");
        if (okClicked) {
            mainApp.getAppData().add(tempApp);
        }
    }
    
    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @SuppressWarnings("deprecation")
	@FXML
    private void handleEditPerson() {
    	ApplicationDtl selectedApp = applicationsTable.getSelectionModel().getSelectedItem();
        if (selectedApp != null) {
            boolean okClicked = mainApp.showAppEditDialog(selectedApp,"Edit");
            if (okClicked) {
            	showApplicationDetails(selectedApp);
            }

        } else {
            // Nothing selected.
            Dialogs.create()
                .title("No Selection")
                .masthead("No Application Selected")
                .message("Please select a Application in the table.")
                .showWarning();
        }
    }
     
}
