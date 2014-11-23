package com.mobileweb.aspects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("globalConfig")
public class GlobalConfig {
	@Value("${confirmRegistUrl}")
	private String confirmRegistUrl;
	@Value("${isProd}")
	private boolean isProd;

	public String getConfirmRegistUrl() {
		return confirmRegistUrl;
	}

	public void setConfirmRegistUrl(String confirmRegistUrl) {
		this.confirmRegistUrl = confirmRegistUrl;
	}

	public boolean isProd() {
		return isProd;
	}

	public void setProd(boolean isProd) {
		this.isProd = isProd;
	}

}
