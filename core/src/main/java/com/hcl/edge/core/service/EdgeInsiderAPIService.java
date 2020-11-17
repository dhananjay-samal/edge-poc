package com.hcl.edge.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.edge.core.bean.EdgeVersions;

public class EdgeInsiderAPIService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private  URL url;
	private  HttpURLConnection httpURLConnection = null;
	String apiEndpoint = "https://www.microsoftedgeinsider.com/api/versions";
	EdgeVersions edgeVersions =null;

	public EdgeVersions getBuildNumbers() {
		logger.info("getBuildNumbers is called ");
		try {

			url = new URL(apiEndpoint);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			try(InputStream inputStream = httpURLConnection.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line+"\n");
				}
				logger.info("read data from URL :  {}",sb.toString());
				ObjectMapper mapper = new ObjectMapper ();
				edgeVersions = mapper.readValue(sb.toString(), EdgeVersions.class);
				logger.info("read data from api end point is :  {}","canary is : "+edgeVersions.getCanary()+" dev is  : "+edgeVersions.getDev()+" beta is  : "+edgeVersions.getBeta()+" stable is  : "+edgeVersions.getStable());
			}

		} catch (MalformedURLException ex) {  

			logger.error("Edge Insider API service is down for MalformedURLException", ex.getMessage());
		} catch (IOException e1) {
			logger.error("Edge Insider API service is down for IOException", e1.getMessage());
		}catch(Exception e2) {
			logger.error("Edge Insider API service is down for Exception", e2.getMessage());
		}
		return edgeVersions;

	}
}
