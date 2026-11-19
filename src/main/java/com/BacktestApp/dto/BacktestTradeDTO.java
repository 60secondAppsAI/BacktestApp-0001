package com.BacktestApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BacktestTradeDTO {

	private Integer backtestTradeId;

	private Integer backtestId;

	private Integer instrumentId;

	private String side;

	private Double quantity;

	private Double price;

	private Date executedAt;






}
