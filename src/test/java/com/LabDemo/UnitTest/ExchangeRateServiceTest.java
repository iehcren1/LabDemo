package com.LabDemo.UnitTest;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.LabDemo.dto.CurrencyInfo;
import com.LabDemo.dto.ExchangeRateRequest;
import com.LabDemo.dto.ForeignExchangeRate;
import com.LabDemo.service.ExchangeRateService;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
public class ExchangeRateServiceTest {

	@Autowired
    private ExchangeRateService exchangeRateService;
	
	@Test
	// 測試是否可取到外部資料
	public void getDailyForeignExRateDataTest() throws JsonProcessingException {
		List<ForeignExchangeRate> records = exchangeRateService.getDailyForeignExRateData();
		Assertions.assertNotNull(records);
		Assertions.assertTrue(records.size() > 0);
	}
	
	@Test
	// 測試是否可取出匯率資料
	public void getExchangeRateTest() throws ParseException {
		ExchangeRateRequest requestParam = new ExchangeRateRequest();
		requestParam.setCurrency("USD");
		requestParam.setStartDate("2024/12/10");
		requestParam.setEndDate("2024/12/12");
		
		List<CurrencyInfo> currencys = exchangeRateService.getExchangeRate(requestParam);
		Assertions.assertNotNull(currencys);
		Assertions.assertTrue(currencys.size() > 0);
	}
}
