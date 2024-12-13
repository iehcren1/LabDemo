package com.LabDemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForeignExchangeRate {

	@JsonProperty("Date")
	private String date;

	@JsonProperty("USD/NTD")
	private String usdRate;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsdRate() {
		return usdRate;
	}

	public void setUsdRate(String usdRate) {
		this.usdRate = usdRate;
	}
	
}
