package com.BacktestApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseBacktestDTO {

	private Integer ownerId;

	private Integer backtestId;

	private Integer backtestStatus;
	
	private Integer nextOrPrevious;
}

