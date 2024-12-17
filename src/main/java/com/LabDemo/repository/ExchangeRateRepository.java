package com.LabDemo.repository;

import com.LabDemo.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {

	/**
	 * 依照幣別取出最大匯率日期
	 * @param currency
	 * @return
	 */
	@Query("select max(ex.rateDate) from ExchangeRate ex")
	LocalDate findMaxRateDateByCurrency(String currency);
	
	/**
	 * 依照幣別與日期區間找出對應的幣別與日期匯率
	 * @param currency
	 * @param startDate
	 * @param endDate
	 * @return
	 */
    @Query(value = "from ExchangeRate where currency=:currency and rateDate between :startDate and :endDate")
    List<ExchangeRate> findByCurrencyAndBetweenDates(String currency, LocalDate startDate, LocalDate endDate);

}
