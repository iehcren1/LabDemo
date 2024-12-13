package com.LabDemo.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRateResponse extends BaseResponse {

	@JsonProperty("currency")
	private List<CurrencyInfo> currencyList = new ArrayList<CurrencyInfo>();

	public List<CurrencyInfo> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<CurrencyInfo> currencyList) {
		this.currencyList = currencyList;
	}

}
