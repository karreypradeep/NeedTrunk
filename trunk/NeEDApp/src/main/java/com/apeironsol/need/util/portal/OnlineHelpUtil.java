/**
 * 
 */
package com.apeironsol.need.util.portal;

import java.util.ResourceBundle;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

/**
 * @author sunny
 * 
 */
@Named
@Scope(value = "session")
public class OnlineHelpUtil {

	private String			helpKey;

	private String			helpContent;

	private ResourceBundle	resourceBundle;

	public String getHelpKey() {
		return this.helpKey;
	}

	public void setHelpKey(final String helpKey) {
		this.helpKey = helpKey;
	}

	public ResourceBundle getResourceBundle() {
		if (this.resourceBundle == null) {
			this.resourceBundle = ViewUtil.getResourceBundle("helpProperties");
		}
		return this.resourceBundle;
	}

	public String getHelpContent() {
		this.helpContent = "";
		if (this.helpKey != null && this.helpKey.trim().length() > 0 && getResourceBundle() != null) {
			try {
				this.helpContent = getResourceBundle().getString(this.helpKey);
			} catch (java.util.MissingResourceException mre) {
			}
		}
		return this.helpContent;
	}

	public void setHelpContent(final String helpContent) {
		this.helpContent = helpContent;
	}

}
