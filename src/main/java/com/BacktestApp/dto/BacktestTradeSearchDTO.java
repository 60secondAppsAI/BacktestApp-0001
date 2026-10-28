package com.BacktestApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BacktestTradeSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer backtestTradeId;
	
	private Integer backtestId;
	
	private Integer instrumentId;
	
	private String side;
	
	private Double quantity;
	
	private Double price;
	
	private Date executedAt;
	
}
