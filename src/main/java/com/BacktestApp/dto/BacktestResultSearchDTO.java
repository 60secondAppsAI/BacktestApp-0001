package com.BacktestApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BacktestResultSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer backtestResultId;
	
	private Integer backtestId;
	
	private Double totalReturn;
	
	private Double maxDrawdown;
	
	private Double sharpeRatio;
	
	private Integer tradeCount;
	
}
