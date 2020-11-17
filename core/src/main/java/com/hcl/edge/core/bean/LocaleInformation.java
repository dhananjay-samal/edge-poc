package com.hcl.edge.core.bean;

public class LocaleInformation {


private String userLocale;
private String acceptedLanguage;

public String getAcceptedLanguage() {
	return acceptedLanguage;
}

public void setAcceptedLanguage(String acceptedLanguage) {
	this.acceptedLanguage = acceptedLanguage;
}

public String getUserLocale() {
	return userLocale;
}

public void setUserLocale(String userLocale) {
	this.userLocale = userLocale;
}




}
