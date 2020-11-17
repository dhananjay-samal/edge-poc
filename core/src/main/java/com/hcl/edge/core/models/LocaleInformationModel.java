package com.hcl.edge.core.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.edge.core.bean.LocaleInformation;

@Model(adaptables = SlingHttpServletRequest.class)
public class LocaleInformationModel {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private LocaleInformation localeInformation;


	public final LocaleInformation getLocaleInformation() {
		return localeInformation;
	}

	@Self
	private SlingHttpServletRequest request;

	/**
	 * Init Method of Model.
	 */

	@PostConstruct
	protected final void init() {
		localeInformation = new LocaleInformation();
		try {
			String acceptedHeader = request.getHeader("Accept-Language");
			logger.info("acceptedHeader is : "+acceptedHeader);
			String acceptedLanguage = acceptedHeader.split(",")[0].split("-")[0];
			localeInformation.setAcceptedLanguage(acceptedLanguage);
			logger.info("resolveLocale(request).getLanguage() is :"+resolveLocale(request));
			localeInformation.setUserLocale(resolveLocale(request));

		} catch (final Exception e) {
			logger.error("Getting the Error "+e.getMessage());
		} 
	}
	public String resolveLocale(SlingHttpServletRequest request) {
		Locale defaultLocale = Locale.getDefault();
		logger.info("defaultLocale       is :"+defaultLocale.getCountry());
		//		String acceptedLanguage = request.getHeader("Accept-Language");
		String akamaiLocale = getCountryCode();
		logger.info("akamaiLocale      is :"+akamaiLocale);
		if (defaultLocale != null && (akamaiLocale == null || akamaiLocale.isEmpty())) {
			return defaultLocale.getCountry();
		}
//		if(akamaiLocale != null || !akamaiLocale.isEmpty()) {
//			return akamaiLocale;
//		}
//		Locale requestLocale = request.getLocale();
//		logger.info("requestLocale      is :"+requestLocale.getCountry());
		//List<Locale> supportedLocales = Collections.list(request.getLocales());
		Locale[] availableLocales = Locale.getAvailableLocales();
		List<Locale> supportedLocales = Arrays.asList(availableLocales);
		List<String> locales = new ArrayList<String>();
		for(Locale l:supportedLocales) {
			locales.add(l.getCountry());
		logger.info("Indise the resolveLocale method , availableLocales are ::::::::::::::::::::::::::"+l.getCountry());
		
		}
		
		if (!locales.isEmpty() || locales.contains(akamaiLocale)) {
			return akamaiLocale;
		}
//		Locale supportedLocale = findSupportedLocale(request, supportedLocales);
//		if (supportedLocale != null) {
//			return supportedLocale.getCountry();
//		}
		return (akamaiLocale != null && !akamaiLocale.isEmpty() ? akamaiLocale : defaultLocale.getCountry());
	}

	private Locale findSupportedLocale(SlingHttpServletRequest request, List<Locale> supportedLocales) {

		Enumeration<Locale> requestLocales = request.getLocales();
		Locale languageMatch = null;

		while (requestLocales.hasMoreElements()) {
			Locale locale = requestLocales.nextElement();
			if (supportedLocales.contains(locale)) {
				if (languageMatch == null || languageMatch.getLanguage().equals(locale.getLanguage())) {

					return locale;
				}
			}
			else if (languageMatch == null) {

				for (Locale candidate : supportedLocales) {
					if ((candidate!=null  && candidate.getCountry().length()>0) &&
							candidate.getLanguage().equals(locale.getLanguage())) {
						languageMatch = candidate;
						break;
					}
				}
			}
		}
		return languageMatch;
	}


	public  String getCountryCode()
	{
		String countryCode = "";
		try
		{
			//             if (serverVariables.Count > 0)
			//             {
			String xAkamaiEdgescape = request.getHeader("HTTP_X-Akamai-Edgescape");
			logger.info("xAkamaiEdgescape is :"+xAkamaiEdgescape);
			String[] geoDetails = null;
			if ( xAkamaiEdgescape !=null && !xAkamaiEdgescape.isEmpty()  ) 
			{ 
				geoDetails = xAkamaiEdgescape.split(",");
				if(geoDetails.length > 0) 
				{ 
					for(int counter=0; counter < geoDetails.length;counter++)
					{
						logger.info("geoDetails  is :"+geoDetails[counter]);
						String[] geoArray = geoDetails[counter].split("=");
						if (geoArray.length == 2) {
							if (geoArray[0].toLowerCase() == "country_code" && geoArray[1] !=null)
							{        logger.info("geoArray  is :"+geoArray[1]);                                 
								return geoArray[1].trim();
							}
						}
					}
				}
			}
			//}   
		}
		catch(Exception ex) {
			  logger.error("getUserLocale  exception {} ", ex.getMessage());
		}
		return countryCode;
	}


}