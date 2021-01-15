package br.com.ricardoianni.inovacaoapp.utils;

import javax.servlet.http.HttpServletRequest;

import br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller.ControllerHelper;

public class NavigatorUtils {
	
	private static String urlToBack;

	public static void setUrlToBack(HttpServletRequest request, 
							 String alternativeUrl) {
		String url = ControllerHelper.getPreviousPageByRequest(request).orElse(alternativeUrl);
		
		urlToBack = url;
	}
	
	public static String getUrlToBack() {
		return urlToBack;
	}

}