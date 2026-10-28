package com.BacktestApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseBacktestTradeDTO {

	private Integer ownerId;

	private Integer backtestTradeId;

	private Integer backtestTradeStatus;
	
	private Integer nextOrPrevious;
}

