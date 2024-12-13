package com.LabDemo.service;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.LabDemo.entity.ExchangeRate;
import com.LabDemo.repository.ExchangeRateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.LabDemo.dto.CurrencyInfo;
import com.LabDemo.dto.ExchangeRateRequest;
import com.LabDemo.dto.ForeignExchangeRate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExchangeRateService {
	private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

	@Value("${daily.foreign.exchange.rates.url}")
	private String dailyForeignExchangeRatesUrl;

	private final String CURRENCY_USD = "USD";
	
	protected DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

	protected DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	
	@Autowired
	private ExtApiService extApiService;

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	public void getDailyForeignExRates() throws URISyntaxException, JsonProcessingException, ParseException {

		List<ForeignExchangeRate> records = this.getDailyForeignExRateData();

		if (!CollectionUtils.isEmpty(records)) {
			for (ForeignExchangeRate rate : records) {
				
				ExchangeRate exchangeRate = exchangeRateRepository.findByCurrencyAndRateDate(CURRENCY_USD, LocalDate.parse(rate.getDate(), dtf));
				logger.info("====exchangeRate:{}", exchangeRate);
				if (exchangeRate == null) {
					ExchangeRate newExchangeRate = new ExchangeRate();
					newExchangeRate.setCurrency(CURRENCY_USD);
					newExchangeRate.setRateDate(LocalDate.parse(rate.getDate(), dtf));
					newExchangeRate.setRate(rate.getUsdRate());
					Timestamp createDate = new Timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
					newExchangeRate.setCreateDate(createDate);
					exchangeRateRepository.save(newExchangeRate);
				}

			}
		}
	}

	public List<ForeignExchangeRate> getDailyForeignExRateData() throws JsonProcessingException {
		logger.info("====呼叫外部API取得資料====");
		// 帶入外部API的URL
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(dailyForeignExchangeRatesUrl);

		// 呼叫外部API
		ResponseEntity<String> response = extApiService.doSendExtApiGetResponseData(builder);

		// Mapping String to Java Object
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<ForeignExchangeRate> foreignExchangeRates = objectMapper.readValue(response.getBody(), new TypeReference<List<ForeignExchangeRate>>(){});

		return foreignExchangeRates;
	}
	
	public List<CurrencyInfo> getExchangeRate(ExchangeRateRequest requestParam) throws ParseException {
		List<CurrencyInfo> currencys = new ArrayList<CurrencyInfo>();
		
		LocalDate startDate = LocalDate.parse(requestParam.getStartDate(), dtf1);
		LocalDate endDate = LocalDate.parse(requestParam.getEndDate(), dtf1);
		
		List<ExchangeRate> exchangeRates = exchangeRateRepository.findByCurrencyAndBetweenDates(requestParam.getCurrency(), startDate, endDate);
		
		if (!CollectionUtils.isEmpty(exchangeRates)) {
			for (ExchangeRate exRate :exchangeRates) {
				CurrencyInfo currency = new CurrencyInfo();
				currency.setDate(exRate.getRateDate().format(dtf));
				currency.setUsd(exRate.getRate());
				currencys.add(currency);
			}
		}
			
		return currencys;
	}

}
