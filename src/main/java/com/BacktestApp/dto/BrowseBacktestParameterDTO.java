package com.BacktestApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseBacktestParameterDTO {

	private Integer ownerId;

	private Integer backtestParameterId;

	private Integer backtestParameterStatus;
	
	private Integer nextOrPrevious;
}

