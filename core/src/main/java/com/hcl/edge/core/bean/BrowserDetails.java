package com.hcl.edge.core.bean;

public class BrowserDetails {

    private String name;
    private String version;
    private String type;
    private String majorVersion;
    private String minorVersion;
    

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}

	public String getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(String minorVersion) {
		this.minorVersion = minorVersion;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    
}
