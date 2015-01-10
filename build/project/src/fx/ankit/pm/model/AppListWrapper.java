package fx.ankit.pm.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import fx.ankit.pm.model.bean.ApplicationDtl;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 * 
 * @author Ankit Rai
 */
@XmlRootElement(name = "applications")
public class AppListWrapper {

	 private List<ApplicationDtl>  appDetailList;

	@XmlElement(name = "appDetail")
	public List<ApplicationDtl> getAppDetailList() {
		return appDetailList;
	}

	public void setAppDetailList(List<ApplicationDtl> appDetailList) {
		this.appDetailList = appDetailList;
	}
}
