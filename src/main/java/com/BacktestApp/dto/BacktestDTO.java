package com.BacktestApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BacktestDTO {

	private Integer backtestId;

	private Integer strategyId;

	private String name;

	private Date startDate;

	private Date endDate;

	private String status;






}
