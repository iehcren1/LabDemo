package com.LabDemo.schedulingTasks;

import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.ParseException;

import com.LabDemo.service.ExchangeRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	private ExchangeRateService exchangeRateService;

//	@Scheduled(cron="${schedule.update.daily.foreign.exchange.rates.cron.Expression}")	// 每天18:00執行1次
	@Scheduled(fixedRate = 600000)	// For Test 每10分鐘執行1次
	public void updateDailyForeignExRates() throws URISyntaxException, JsonProcessingException, ParseException {
		logger.info("===== 執行取得外匯資料Start ====");
		exchangeRateService.getDailyForeignExRates();
		logger.info("===== 執行取得外匯資料End ====");
	}
	
}
