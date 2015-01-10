package fx.ankit.pm.view;

import java.io.File;

import org.controlsfx.dialog.Dialogs;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import fx.ankit.pm.MainApp;

/**
 * The controller for the base layout. The base layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Ankit Rai
 */
public class BaseLayoutController {

	// Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        mainApp.getAppData().clear();
        mainApp.setAppFilePath(null);
    }
    
    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadAppDataFromFile(file);
        }
    }
    
    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File appFile = mainApp.getAppFilePath();
        if (appFile != null) {
            mainApp.saveAppDataToFile(appFile);
        } else {
            handleSaveAs();
        }
    }
    
    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveAppDataToFile(file);
        }
    }
    
    /**
     * Opens an about dialog.
     */
    @SuppressWarnings("deprecation")
	@FXML
    private void handleAbout() {
        Dialogs.create()
            .title("Password Pro Manager")
            .masthead("About")
            .message("Author: Ankit Rai\nWebsite: http://www.ankitrai.in")
            .showInformation();
    }
    
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
