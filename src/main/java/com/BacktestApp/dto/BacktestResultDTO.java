package com.BacktestApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BacktestResultDTO {

	private Integer backtestResultId;

	private Integer backtestId;

	private Double totalReturn;

	private Double maxDrawdown;

	private Double sharpeRatio;

	private Integer tradeCount;






}
