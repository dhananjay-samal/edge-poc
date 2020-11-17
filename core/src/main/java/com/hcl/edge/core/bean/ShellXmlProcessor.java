package com.hcl.edge.core.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "shell")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ShellXmlProcessor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4624145160401291923L;

	private String cssIncludes;
	private String javascriptIncludes ;
	private String headerHtml;
	private String footerHtml;

	public String getJavascriptIncludes() {
		return javascriptIncludes;
	}

	public void setJavascriptIncludes(String javascriptIncludes) {
		this.javascriptIncludes = javascriptIncludes;
	}

	public String getCssIncludes() {
		return cssIncludes;
	}

	public void setCssIncludes(String cssIncludes) {
		this.cssIncludes = cssIncludes;
	}


	public String getHeaderHtml() {
		return headerHtml;
	}

	public void setHeaderHtml(String headerHtml) {
		this.headerHtml = headerHtml;
	}

	public String getFooterHtml() {
		return footerHtml;
	}

	public void setFooterHtml(String footerHtml) {
		this.footerHtml = footerHtml;
	}

	@Override
	public String toString() {

		return "CssIncludes : "+cssIncludes+" JavaScriptIncludes: "+javascriptIncludes+" HeaderHtml: "+headerHtml+"  FooterHtml : "+footerHtml;
	}
}
