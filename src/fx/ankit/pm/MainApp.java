package fx.ankit.pm;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;

import fx.ankit.pm.model.AppListWrapper;
import fx.ankit.pm.model.bean.ApplicationDtl;
import fx.ankit.pm.view.AppEditController;
import fx.ankit.pm.view.AppOverviewController;
import fx.ankit.pm.view.BaseLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * The data as an observable list of Applications.
     */
    private ObservableList<ApplicationDtl> appData = FXCollections.observableArrayList();

	/**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
    
    	appData.add(new ApplicationDtl("DMC Bank","Username1","Vergil@gmail.com","password1"));
    	appData.add(new ApplicationDtl("GitHub","Username2","santa@outlook.com","password2"));
    	appData.add(new ApplicationDtl("FaceBook","Username3","Dante@evan.com","password3"));
    	appData.add(new ApplicationDtl("LinkedIn","Username4","kinkyshots@vodka.com","password5"));
    	appData.add(new ApplicationDtl("Twitter","Username5","deathcode@killer.com","password5"));
    	appData.add(new ApplicationDtl("Omegle","Username6","Mandrikohio@looper.xxx","password6"));
      }
    
    /**
     * Returns the data as an observable list of Applications. 
     * @return
     */
    public ObservableList<ApplicationDtl> getAppData() {
        return appData;
    }
    
	@Override
	public void start(Stage primaryStage) {
		 this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("Password Manager - Ankit");

	        this.primaryStage.getIcons().add(new Image("file:resources/images/App-password-icon.png"));
	        initRootLayout();	
	        showAppOverview();
	}
	
	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fx/ankit/pm/view/BaseLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            
            // Give the controller access to the main app.
            BaseLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Try to load last opened person file.
        File file = getAppFilePath();
        if (file != null) {
            loadAppDataFromFile(file);
        }
    }

    /**
     * Shows the Application overview inside the root layout.
     */
    public void showAppOverview() {
    	 try {
    	        // Load person overview.
    	        FXMLLoader loader = new FXMLLoader();
    	        loader.setLocation(MainApp.class.getResource("/fx/ankit/pm/view/AppsOverview.fxml"));
    	        AnchorPane appOverview = (AnchorPane) loader.load();

    	        // Set person overview into the center of root layout.
    	        rootLayout.setCenter(appOverview);

    	        // Give the controller access to the main app.
    	        AppOverviewController controller = loader.getController();
    	        controller.setMainApp(this);

    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	
    	/*   try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/AppsOverview.fxml"));
            AnchorPane appOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(appOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    
    /**
     * Opens a dialog to edit details for the specified app. If the user
     * clicks OK, the changes are saved into the provided application object and true
     * is returned.
     * 
     * @param  the application object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showAppEditDialog(ApplicationDtl appDetail,String type) 
    {
        try 
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fx/ankit/pm/view/AppEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("file:resources/images/App-password-icon.png"));
            dialogStage.setTitle(type+" Application Details");
            //dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AppEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setApplicationDtl(appDetail);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the application file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getAppFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setAppFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("Password Manager-Ankit - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("Password Manager-Ankit ");
        }
    }
    
    /**
     * Loads apps data from the specified file. The current app data will
     * be replaced.
     * 
     * @param file
     */
    @SuppressWarnings("deprecation")
	public void loadAppDataFromFile(File file) 
    {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AppListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            AppListWrapper wrapper = (AppListWrapper) um.unmarshal(file);

            appData.clear();
            appData.addAll(wrapper.getAppDetailList());

            // Save the file path to the registry.
            setAppFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Dialogs.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + file.getPath())
                    .showException(e);
        }
    }

    /**
     * Saves the current person data to the specified file.
     * 
     * @param file
     */
    @SuppressWarnings("deprecation")
	public void saveAppDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AppListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            AppListWrapper wrapper = new AppListWrapper();
            wrapper.setAppDetailList(appData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setAppFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}
