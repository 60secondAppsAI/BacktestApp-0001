package com.BacktestApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BacktestSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer backtestId;
	
	private Integer strategyId;
	
	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	private String status;
	
}
