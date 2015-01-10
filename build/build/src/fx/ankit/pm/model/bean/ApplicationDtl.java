package fx.ankit.pm.model.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class ApplicationDtl {

		private final StringProperty applicationName;
	    private final StringProperty username;
	    private final StringProperty emailID;
	    private final StringProperty password;
		
	    public ApplicationDtl() {
			this(null,null,null,null);
		}

		public ApplicationDtl(String applicationName,String username, String emailID,String password) {
			super();
			this.applicationName = new SimpleStringProperty(applicationName);
			this.username = new SimpleStringProperty(username);
			this.emailID = new SimpleStringProperty(emailID);
			this.password = new SimpleStringProperty(password);
		}

		public String getApplicationName() {
			return applicationName.get();
		}
		
		public void setApplicationName(String applicationName) {
			this.applicationName.set(applicationName);
		}

		public StringProperty applicationNameProperty() {
		        return applicationName;
		}   
		
		public String getUsername() {
			return username.get();
		}

		public void setUsername(String username) {
			this.username.set(username);
		}
		
		public StringProperty usernameProperty() {
	        return username;
		} 	 
		
		public String getEmailID() {
			return emailID.get();
		}

		public void setEmailID(String emailID) {
			this.emailID.set(emailID);
		}
		
		public StringProperty emailIDProperty() {
	        return emailID;
		} 
		
		public String getPassword() {
			return password.get();
		}

		public void setPassword(String password) {
			this.password.set(password);
		}
	    
		public StringProperty passwordProperty() {
	        return password;
		} 
	    
}
