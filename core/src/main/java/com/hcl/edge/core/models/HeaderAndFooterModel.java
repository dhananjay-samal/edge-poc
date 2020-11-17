package com.hcl.edge.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.edge.core.bean.ShellBean;
import com.hcl.edge.core.bean.ShellXmlProcessor;
import com.hcl.edge.core.service.ShellServiceProvider;



/**
 * Sling model for BrowserDetailsModel.
 */

@Model(adaptables = SlingHttpServletRequest.class)
public class HeaderAndFooterModel {



	private final Logger logger = LoggerFactory.getLogger(getClass());
	private ShellServiceProvider shellServiceProvider;
	private ShellBean shellBean;


	public final ShellBean getShellBean() {
		return shellBean;
	}

	@Self
	private SlingHttpServletRequest request;

	/**
	 * Init Method of Model.
	 */

	@PostConstruct
	protected final void init() {
		shellServiceProvider = new ShellServiceProvider();
		shellBean = new ShellBean();
		try {

			//Now use hard code en for locale and later we will call getCountryCode() in service class
			String locale = request.getHeader("Accept-Language");
			ShellXmlProcessor shellXmlProcessor = shellServiceProvider.Load("en", false);
			shellBean.setCssIncludes(shellXmlProcessor.getCssIncludes());
			shellBean.setFooterHtml(shellXmlProcessor.getFooterHtml());
			shellBean.setHeaderHtml(shellXmlProcessor.getHeaderHtml());
			shellBean.setJavascriptIncludes(shellXmlProcessor.getJavascriptIncludes());
			shellBean.setJavascriptIncludes(shellXmlProcessor.getJavascriptIncludes());
//			logger.info("Inside the Model class shell model is populated :::::::::::{}",shellXmlProcessor);

//			Cache<String, ShellBean>   caffeineCache = shellServiceProvider.getCaffeineCache();
//			if(caffeineCache.getIfPresent(shellBean)!=null) {
//				//check time based eviction
//				logger.info("shellBean object is populated from cache {}",caffeineCache.getIfPresent(shellBean));
//			}else {
//				shellBean.setCssIncludes(shellXmlProcessor.getCssIncludes());
//				shellBean.setFooterHtml(shellXmlProcessor.getFooterHtml());
//				shellBean.setHeaderHtml(shellXmlProcessor.getHeaderHtml());
//				shellBean.setJavascriptIncludes(shellXmlProcessor.getJavascriptIncludes());
//				logger.info("shellBean object is put into cache {}",shellBean);
//				caffeineCache.put("shellBean", shellBean);
//			}

		} catch (final Exception e) {
			logger.error("Getting Exception while retriving browsername and version {}",e.toString());
		} 
	}

}
