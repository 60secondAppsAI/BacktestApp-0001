package com.BacktestApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseBacktestResultDTO {

	private Integer ownerId;

	private Integer backtestResultId;

	private Integer backtestResultStatus;
	
	private Integer nextOrPrevious;
}

