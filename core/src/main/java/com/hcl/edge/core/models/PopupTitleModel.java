package com.hcl.edge.core.models;


import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

	


@Model(adaptables=Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
	public class PopupTitleModel {
	
		
			private String popuptext;
			
			@Self
			private SlingHttpServletRequest request;
			
			@Inject
			private ResourceResolver resourceResolver;
			
			public static final Logger log= LoggerFactory.getLogger(PopupTitleModel.class);
  
			@PostConstruct
			public void init () {
				
				String acceptedHeader = request.getHeader("Accept-Language");
				log.info("acceptedHeader in edge popup  is : "+acceptedHeader);
//				String acceptedLanguage = acceptedHeader.split(",")[0].split("-")[0];
				String headerArr[] = acceptedHeader.split(",");
				String acceptedLanguageArray[]= {headerArr[0]};
				String acceptedLanguage =Arrays.stream(acceptedLanguageArray).filter(a->a.contains("-")).findAny().orElse(acceptedLanguageArray[0]).toLowerCase();
				
				log.info("acceptedLanguage in edge popup is : "+acceptedHeader);
				Resource res=resourceResolver.getResource("/content/edgepopup/"+acceptedLanguage+"/page/jcr:content/par/edge_popup");
				ValueMap properties=ResourceUtil.getValueMap(res);
				popuptext=(String) properties.get("popuptext");
				log.info("pupup error"+popuptext);
			}

			public String getPopuptext() {
				return popuptext;
			}

			public void setPopuptext(String popuptext) {
				this.popuptext = popuptext;
			}
			
}