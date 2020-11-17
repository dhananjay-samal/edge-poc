package com.hcl.edge.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.apache.aries.util.manifest.ManifestHeaderProcessor.NameValueCollection;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import com.hcl.edge.core.bean.ShellBean;
import com.hcl.edge.core.bean.ShellXmlProcessor;

@Component(service = ShellServiceProvider.class, immediate = true, configurationPolicy = ConfigurationPolicy.OPTIONAL)
@Designate(ocd = ShellServiceProvider.Config.class)
public class ShellServiceProvider {


private final Logger logger = LoggerFactory.getLogger(getClass());
@ObjectClassDefinition(name = "EDGE - UHF", description = "EDGE - UHF")
public @interface Config {
@AttributeDefinition(name = "UHF End Point", description = "UHF End Point", type = AttributeType.STRING)
String serviceEndpoint();

@AttributeDefinition(name = "partnerId", description = "partnerId", type = AttributeType.STRING)
String partnerId();

@AttributeDefinition(name = "headerId", description = "headerId", type = AttributeType.STRING)
String headerId();

@AttributeDefinition(name = "footerId", description = "footerId", type = AttributeType.STRING)
String footerId();

@AttributeDefinition(name = "userAgent", description = "userAgent", type = AttributeType.STRING)
String userAgent();

}

@Self
private SlingHttpServletRequest request;

	// Here caffiene chache object will create for  properties of maxsize 1 and maxtime 60 mins
//
//	private Cache<String, ShellBean> caffeineCache = Caffeine.newBuilder()
//			.expireAfterAccess(1, TimeUnit.MINUTES)
//			.maximumSize(1)
//			.build();

	private  static  String serviceEndpoint = "https://uhf.microsoft.com";
	private static   String partnerId = "MSEdge";
	private static   String headerId = "MSEdgeHeader";
	private static   String footerId = "MSEdgeFooter";
	private static   String userAgent = "Microsoft";
	private static  final boolean isUHFCacheEnabled = false;
	private static double cacheExpireTimeinHours = 24;
	//Http connections and data streams
	private  URL url;
	private  HttpURLConnection httpURLConnection = null;
	private  OutputStreamWriter outputStreamWriter = null;

	// private  static final ICookieConsentClient _cookieConsentClient = CookieConsentClientFactory.Create("microsoft");
	public static final String CookieConsentSiteDomain = "microsoft.com";

//	public Cache<String, ShellBean> getCaffeineCache() {
//		return caffeineCache;
//	}
//
//
//	public void setCaffeineCache(Cache<String, ShellBean> caffeineCache) {
//		this.caffeineCache = caffeineCache;
//	}

	@Activate
	protected final void activate(final Config config) {
		serviceEndpoint = config.serviceEndpoint();
		partnerId = config.partnerId();
		headerId = config.headerId();
		footerId = config.footerId();
		userAgent = config.userAgent();

	}


	/*  public static ConsentMarkup GetMarkup(String locale)
    {
        return _cookieConsentClient.GetConsentMarkup(locale);
    }*/


	public  ShellXmlProcessor Load(String locale, boolean _isConsentRequired)
	{
		return LoadUHF(locale,_isConsentRequired);
	}


	public  ShellXmlProcessor LoadUHF(String locale, boolean _isConsentRequired)
	{
		String xml = "";
		//open connection to the server
		String serviceUrl = serviceEndpoint +
				"/" + locale + "/shell/xml/" + partnerId +
				"?headerId=" + headerId +
				"&footerId=" + footerId + "&CookieComplianceEnabled=" + _isConsentRequired;
//		logger.info("serviceUrl :{}", serviceUrl);

		try
		{
			url = new URL(serviceUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("user-agent", userAgent);
			String encoding = httpURLConnection.getContentEncoding();

//			logger.info("httpUrlConnection encoding type is {}", encoding);

			encoding = encoding == null ? "UTF-8" : encoding;
			final Level level = Level.INFO;
			//            logger.info("Logger level is {} httpUrlConnection encoding type is {} and httpURLConnection is {}",level, encoding,httpURLConnection);
			try (InputStream inputStream = httpURLConnection.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))){
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line+"\n");
				}
				xml = sb.toString();  

			}catch(IOException ex) {
				logger.error("Getting Exception while reading XML from server : {}",ex.getMessage());
			}
			ShellXmlProcessor shellXmlProcessor =	ConvertXmlToModel(xml);
			return shellXmlProcessor;
		}
		catch (Exception ex)
		{
			logger.error("ShellServiceProvider.Load ex:{}", ex.toString());
			return null;
		}
	}

	private  ShellXmlProcessor ConvertXmlToModel(String xml) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(ShellXmlProcessor.class); 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ShellXmlProcessor shellModel = (ShellXmlProcessor) jaxbUnmarshaller.unmarshal(new StringReader(xml));
		return shellModel;
	}
	/*   private static HtmlString EnsureStringValue(XElement root, String elementName)
    {
        var element = root.Element(elementName);
        return new HtmlString(element != null ? (string)element : string.Empty);
    }*/

	/*  public static ConsentStatus IsConsentRequired(String locale)
    {
        String countryCode = GetUserCountryCode(HttpContext.Current.Request.ServerVariables);
        ConsentStatus consentStatus = ConsentStatus.Required;
        if (!string.IsNullOrEmpty(countryCode))
        {
            consentStatus = _cookieConsentClient.IsConsentRequiredForRegion(CookieConsentSiteDomain, countryCode);
        }
        else
        {
            consentStatus = ConsentStatus.Required;
        }
        return consentStatus;
    }*/


	public  String GetUserCountryCode(NameValueCollection serverVariables)
	{
		String countryCode = "";
		try
		{
			//            if (serverVariables != null && serverVariables.Count > 0)
			//            {
			String xAkamaiEdgescape = request.getHeader("HTTP_X-Akamai-Edgescape");
			logger.info("serverVariables.ToString():" + serverVariables.toString());
			String[] geoDetails = null;

			if (!xAkamaiEdgescape.isEmpty() && xAkamaiEdgescape !=null ) 
			{ 
				geoDetails = xAkamaiEdgescape.split(",");
				if(geoDetails.length > 0) 
				{ 
					for(int counter=0; counter < geoDetails.length;counter++)
					{
						String[] geoArray = geoDetails[counter].split("=");
						if (geoArray.length == 2) {
							if (geoArray[0].toLowerCase() == "country_code" && geoArray[1] !=null)
							{                                         
								return geoArray[1].trim();
							}
						}
					}
				}
			}
			//}   
		}
		catch (Exception ex)
		{
			//  Trace.TraceError(string.Format("GetUserCountryCode UHF exception:{0}", ex.Message));
			// throw;
		}
		return countryCode;
	}


}
