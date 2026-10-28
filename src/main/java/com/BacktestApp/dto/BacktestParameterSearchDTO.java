package com.BacktestApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BacktestParameterSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer backtestParameterId;
	
	private Integer backtestId;
	
	private String parameterName;
	
	private String parameterValue;
	
}
