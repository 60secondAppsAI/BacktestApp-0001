package com.BacktestApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BacktestParameterDTO {

	private Integer backtestParameterId;

	private Integer backtestId;

	private String parameterName;

	private String parameterValue;






}
