package com.LabDemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse {

	@JsonProperty("error")
	private ReturnModel returnModel;

	public ReturnModel getReturnModel() {
		return returnModel;
	}

	public void setReturnModel(ReturnModel returnModel) {
		this.returnModel = returnModel;
	}


}
