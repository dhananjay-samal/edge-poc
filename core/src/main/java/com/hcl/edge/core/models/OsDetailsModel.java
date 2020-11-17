package com.hcl.edge.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.edge.core.bean.OsDetails;



/**
 * Sling model for Card Model.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class OsDetailsModel {

	private OsDetails osDetails;

	private boolean isDevice = false;
	private String OS = "";
	private final Logger logger = LoggerFactory.getLogger(getClass());


	@Self
	private SlingHttpServletRequest request;

	@PostConstruct
	protected final void init() {
		osDetails = new OsDetails();
		try {
			String  userAgent  =   request.getHeader("User-Agent");
			
			logger.info("user agent in osDetailsModel  : "+userAgent);

			if ((!userAgent.isEmpty()&& userAgent!=null) && (userAgent.indexOf("Linux") != -1 || userAgent.indexOf("Android") != -1  || userAgent.indexOf("Mac") !=-1))
			{
				isDevice = true;
				logger.info("isDevice for true  :"+isDevice);
			}


			if(!isDevice)

			{
				logger.info("userAgent inside  : "+userAgent);
				logger.info("!isDevice  :"+isDevice);
				if (userAgent.indexOf("Macintosh") >= 0 )
				{
					OS = "Macintosh";
					osDetails.setOsName("macOS");
					osDetails.setOsId("macos");
					osDetails.setClassName("macos");
					
					logger.info("OS in  Macintosh : "+OS);

				} else if(userAgent.indexOf("Windows NT 10.0") >= 0)
				{
					OS = "Windows 10";
					osDetails.setOsId("win10");
					osDetails.setOsName("Windows 10");
					osDetails.setClassName("win10");
					logger.info("OS in  Windows NT 10.0 : "+OS);

				} else if(userAgent.indexOf("Windows NT 6.3") >= 0)
				{
					OS = "Windows 8.1";
					osDetails.setOsId("win8dot1");
					osDetails.setOsName("Windows 8.1");
					osDetails.setClassName("Windows 8.1");
					logger.info("OS in  Windows NT 6.3 : "+OS);
				} else if(userAgent.indexOf("Windows NT 6.2") >= 0)
				{
					OS = "Windows 8";
					osDetails.setOsId("win8");
					osDetails.setOsName("Windows 8");
					osDetails.setClassName("win8");
					logger.info("OS in  \"Windows NT 6.2 : "+OS);
				} else if(userAgent.indexOf("Windows NT 6.1") >= 0)
				{
					OS = "Windows 7";
					osDetails.setOsId("win7");
					osDetails.setOsName("Windows 7");
					osDetails.setClassName("win7");
					logger.info("OS in  Windows NT 6.1 : "+OS);
				} else if(userAgent.indexOf("Windows NT 5.1") >= 0)
				{
					OS = "Windows XP";
					osDetails.setOsId("winxp");
					osDetails.setOsName("Windows XP");
					osDetails.setClassName("winxp");
					logger.info("OS in  Windows NT 5.1 : "+OS);

				} else if(userAgent.indexOf("Linux") >= 0 && userAgent.indexOf("Android")< 0)
				{
					OS = "Linux";
					osDetails.setOsId("linux");
					osDetails.setOsName("Linux");
					osDetails.setClassName("linux");
					logger.info("OS in  Linux : "+OS);
				} else if(userAgent.indexOf("CrOS") >= 0)
				{
					OS = "CrOS";
					osDetails.setOsId("CrOS");
					osDetails.setOsName("ChromeOS");
					osDetails.setClassName("CrOS");
					logger.info("OS in  CrOS : "+OS);
				} 

			}  else {
				logger.info("isDevice  : "+isDevice);

				if ((!userAgent.isEmpty()&& userAgent!=null) && userAgent.contains("Mac"))

				{
					osDetails.setOsId("ios");
					osDetails.setOsName("iOS");
					osDetails.setClassName("ios_mob");
					logger.info("OS in  Mac : "+OS);
					//AppleWebKit , 

				}

				else if ((!userAgent.isEmpty()&& userAgent!=null) && (userAgent.contains("Windows Phone") && userAgent.contains("Android")) || userAgent.contains("Windows Phone"))

				{
					osDetails.setOsId("windows");
					osDetails.setOsName("Windows");
					osDetails.setClassName("windows_mob");
					logger.info("OS in  Windows Phone : "+OS);
				}

				else if ((!userAgent.isEmpty()&& userAgent!=null) && userAgent.contains("Android") || (userAgent.contains("Android") && userAgent.contains("Linux")))

				{
					osDetails.setOsId("android");
					osDetails.setOsName("Android");
					osDetails.setClassName("android_mob");  
					logger.info("OS in  Android : "+OS);
				}

				//Trace.TraceInformation("Targetting.cs in device condition id=" + id + " name=" + name + " className" + className);

			}
		}catch(Exception e) {
			osDetails.setOsName("Os name is not undefined  "+"Exception: "+e.getMessage());
		}

	}

	public final OsDetails getOsDetails() {
		return osDetails;
	}

}
