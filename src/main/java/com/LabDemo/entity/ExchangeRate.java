package com.LabDemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name="exchange_rate")
public class ExchangeRate {

	@Id
	@Column(name="seq", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;
	
	@Column(name="currency")
	private String currency;

	@Column(name="rate_date")
	private LocalDate rateDate;

	@Column(name="rate")
	private String rate;
	
    @Column(name="create_date")
	private Timestamp createDate;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDate getRateDate() {
		return rateDate;
	}

	public void setRateDate(LocalDate rateDate) {
		this.rateDate = rateDate;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}
