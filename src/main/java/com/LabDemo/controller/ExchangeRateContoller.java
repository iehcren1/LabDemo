package com.LabDemo.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.LabDemo.dto.CurrencyInfo;
import com.LabDemo.dto.ReturnModel;
import com.LabDemo.dto.ExchangeRateRequest;
import com.LabDemo.dto.ExchangeRateResponse;
import com.LabDemo.service.ExchangeRateService;
import com.LabDemo.utils.DateTimeUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class ExchangeRateContoller {

	private static final Logger logger = LoggerFactory.getLogger(ExchangeRateContoller.class);
	
	protected DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	@Autowired
    private ExchangeRateService exchangeRateService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/exchangeRate/find")
	public ResponseEntity<?> findByCurrency(@RequestBody ExchangeRateRequest requestParam) throws ParseException {
		ExchangeRateResponse response = new ExchangeRateResponse();
		ReturnModel returnModel = new ReturnModel();
		
		// 判斷日期格式是否相符
		if (!DateTimeUtils.isLegalDate(requestParam.getStartDate(), "yyyy/MM/dd") || !DateTimeUtils.isLegalDate(requestParam.getEndDate(), "yyyy/MM/dd")) {
			returnModel.setCode("E001");
			returnModel.setMessage("日期格式不符");
			response.setReturnModel(returnModel);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} 
		
		LocalDate startDate = LocalDate.parse(requestParam.getStartDate(), dtf);
		LocalDate endDate = LocalDate.parse(requestParam.getEndDate(), dtf);
		logger.info("====startDate:{}, endDate:{}", startDate, endDate);
		
		// 判斷日期起始值是否正確與日期區間是否小於一年前與大於當日日期減1天
		if (startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now().minusYears(1)) || endDate.isAfter(LocalDate.now().minusDays(1))) {
			returnModel.setCode("E001");
			returnModel.setMessage("日期區間不符");
			response.setReturnModel(returnModel);
		} else {
			List<CurrencyInfo> currencys = exchangeRateService.getExchangeRate(requestParam);
			logger.info("====currencys size:{}", currencys.size());
			
			if (!CollectionUtils.isEmpty(currencys)) {
				response.setCurrencyList(currencys);
			
				returnModel.setCode("0000");
				returnModel.setMessage("Success");
				response.setReturnModel(returnModel);
			} 
		}
		
		response.setReturnModel(returnModel);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
