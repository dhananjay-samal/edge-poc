package com.hcl.edge.core.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.edge.core.bean.BrowserDetails;
import com.hcl.edge.core.bean.EdgeVersions;
import com.hcl.edge.core.service.EdgeInsiderAPIService;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;



/**
 * Sling model for BrowserDetailsModel.
 */

@Model(adaptables = SlingHttpServletRequest.class)
public class BrowserDetailsModel {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private BrowserDetails browserDetails;


	@Self
	private SlingHttpServletRequest request;

	EdgeInsiderAPIService _edgeinsiderapiService =null;


	/**
	 * Init Method of Model.
	 */

	@PostConstruct
	protected final void init() {
		browserDetails = new BrowserDetails();
		_edgeinsiderapiService = new EdgeInsiderAPIService();
		try {

			String  userAgent  =   request.getHeader("User-Agent");

			logger.info("user agent is  : "+userAgent);
			String browserName = "";
			String browserVersion ="";
			String browserType="";
			String browserMajorVersion="";
			String browserMinorVersion="";
			if (userAgent.contains("MSIE"))
			{
				String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0];
				browserName=substring.split(" ")[0].replace("MSIE", "IE");
				String version  =  substring.split(" ")[1];
				String[] verArray = version.split("\\.");
				browserVersion =verArray[0]+"."+verArray[1];
				String finalString = browserName+"/"+browserVersion;
				String[] MajorAndMinorArray = finalString.split("\\.");
				browserType = MajorAndMinorArray[0];
				browserType=CompareEdgeVersionNumbers(userAgent,browserType);
				browserMajorVersion = MajorAndMinorArray[0].split("/")[1];
				browserMinorVersion=MajorAndMinorArray[1];
				logger.info("Browser name and version :{} {}",browserName,browserVersion );
			} else if (userAgent.contains("Safari") && userAgent.contains("Version"))
			{
				browserName=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0];
				String version = (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
				String[] verArray = version.split("\\.");
				browserVersion =verArray[0]+"."+verArray[1];
				String finalString = browserName+"/"+browserVersion;
				String[] MajorAndMinorArray = finalString.split("\\.");
				browserType = MajorAndMinorArray[0];
				browserType=CompareEdgeVersionNumbers(userAgent,browserType);
				browserMajorVersion = MajorAndMinorArray[0].split("/")[1];
				browserMinorVersion=MajorAndMinorArray[1];
				logger.info("Browser name and version :{} {}",browserName,browserVersion );
			} else if ( userAgent.contains("OPR") || userAgent.contains("Opera"))
			{
				if(userAgent.contains("Opera")) {
					browserName=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0];
					String version = (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
					String[] verArray = version.split("\\.");
					browserVersion =verArray[0]+"."+verArray[1];
					String finalString = browserName+"/"+browserVersion;
					String[] MajorAndMinorArray = finalString.split("\\.");
					browserType = MajorAndMinorArray[0];
					browserType=CompareEdgeVersionNumbers(userAgent,browserType);
					browserMajorVersion = MajorAndMinorArray[0].split("/")[1];
					browserMinorVersion=MajorAndMinorArray[1];
					logger.info("Browser name and version :{} {}",browserName,browserVersion );
				}
				else if(userAgent.contains("OPR")) {
					browserName=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
					logger.info("Browser name  :{} ",browserName );
				}
			} else if (userAgent.contains("Chrome"))
			{
				String substring=userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0];
				browserName=substring.split("/")[0];
				String version =substring.split("/")[1];
				String[] verArray = version.split("\\.");
				browserVersion =verArray[0]+"."+verArray[1];
				String finalString = browserName+"/"+browserVersion;
				String[] MajorAndMinorArray = finalString.split("\\.");
				browserType = MajorAndMinorArray[0];
				browserType=CompareEdgeVersionNumbers(userAgent,browserType);
				browserMajorVersion = MajorAndMinorArray[0].split("/")[1];
				browserMinorVersion=MajorAndMinorArray[1];

				logger.info("Browser name and version :{} {}",browserName,browserVersion );
			} else if ((userAgent.indexOf("Mozilla/5.0") > -1)||(userAgent.indexOf("Mozilla/7.0") > -1) || (userAgent.indexOf("Netscape6") != -1)  || (userAgent.indexOf("Mozilla/4.7") != -1) || (userAgent.indexOf("Mozilla/4.78") != -1) || (userAgent.indexOf("Mozilla/4.08") != -1) || (userAgent.indexOf("Mozilla/3") != -1) )
			{
				String substring=userAgent.substring(userAgent.indexOf("Mozilla")).split(" ")[0];
				browserName=substring.split("/")[0];
				String version =substring.split("/")[1];
				String[] verArray = version.split("\\.");
				browserVersion =verArray[0]+"."+verArray[1];
				String finalString = browserName+"/"+browserVersion;
				String[] MajorAndMinorArray = finalString.split("\\.");
				browserType = MajorAndMinorArray[0];
				browserType=CompareEdgeVersionNumbers(userAgent,browserType);
				browserMajorVersion = MajorAndMinorArray[0].split("/")[1];
				browserMinorVersion=MajorAndMinorArray[1];
				logger.info("Browser name and version :{} {}",browserName,browserVersion );
			} else if (userAgent.contains("Firefox"))
			{
				String substring=userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0];
				browserName=substring.split("/")[0];
				String version =substring.split("/")[1];
				String[] verArray = version.split("\\.");
				browserVersion =verArray[0]+"."+verArray[1];
				String finalString = browserName+"/"+browserVersion;
				String[] MajorAndMinorArray = finalString.split("\\.");
				browserType = MajorAndMinorArray[0];
				browserType=CompareEdgeVersionNumbers(userAgent,browserType);
				browserMajorVersion = MajorAndMinorArray[0].split("/")[1];
				browserMinorVersion=MajorAndMinorArray[1];
				logger.info("Browser name and version :{} {}",browserName,browserVersion );
			} else if(userAgent.contains("Rv"))
			{
				browserName="IE-" + userAgent.substring(userAgent.indexOf("Rv") + 3, userAgent.indexOf(")"));
				logger.info("Browser name and version :{} ",browserName );
			}

			browserDetails.setName(browserName);  
			browserDetails.setVersion(browserVersion);  
			browserDetails.setType(browserType);
			browserDetails.setMajorVersion(browserMajorVersion);
			browserDetails.setMinorVersion(browserMinorVersion);

		} catch (final Exception e) {
			logger.error("Getting Exception while retriving browsername and version {}",e.toString());
		} 
	}



	public  String CompareEdgeVersionNumbers(String userAgent, String browsertype)
	{
		try
		{
			Pattern pattern = Pattern.compile("Edg/", Pattern.CASE_INSENSITIVE);
			Matcher edgeExpreession = pattern.matcher(userAgent);
			if (!userAgent.isEmpty() && userAgent!=null)
			{
				if (edgeExpreession.find())
				{
					logger.info("edgeExpreession is find");
					int startindex = userAgent.indexOf("Edg/");
					EdgeVersions edgeVersions  = _edgeinsiderapiService.getBuildNumbers();
					logger.info("edgeVersions is : "+edgeVersions);
					logger.info("userAgent is  :"+userAgent);
					//String buildNumber = userAgent.substring(startindex + 4, (userAgent.length() - (startindex + 4)));
					String buildNumber =userAgent.substring(startindex + 4,userAgent.length());
					logger.info("buildNumber is : "+buildNumber);
					if ((edgeVersions!=null ) && (buildNumber!=null && !buildNumber.isEmpty()))
					{
						//                    	JSONObject jsonObj = new JSONObject(apibuildnumbers);
						//                        EdgeVersions jsobject = JsonConvert.DeserializeObject<EdgeVersions>(apibuildnumbers);
						//                        if (String.Compare(buildNumber, jsobject.stable) <= 0)
						//                        {
						//                            return "Anaheim";
						//                        }
						logger.info("buildNumber  and edgeVersions are ot empty : " +buildNumber+"   and  "+ edgeVersions.getStable());
						if (buildNumber.compareTo(edgeVersions.getStable()) <= 0)
						{
							logger.info("buildNumber  and edgeVersions : "+edgeVersions +"  ::  "+buildNumber);
							return "Anaheim";
						}
					}
				}
			}
		}
		catch (Exception ex)
		{
			logger.error("CompareEdgeVersionNumbers exception:{0}", ex.getMessage());
		}
		return browsertype;
	}


	public final BrowserDetails getBrowserDetails() {
		return browserDetails;
	}
}