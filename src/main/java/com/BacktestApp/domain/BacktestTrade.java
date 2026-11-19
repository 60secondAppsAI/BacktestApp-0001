package com.BacktestApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="backtest_trades")
@Getter @Setter @NoArgsConstructor
public class BacktestTrade {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="backtest_trade_id")
	private Integer backtestTradeId;
    
  	@Column(name="backtest_id")
	private Integer backtestId;
    
  	@Column(name="instrument_id")
	private Integer instrumentId;
    
  	@Column(name="side")
	private String side;
    
  	@Column(name="quantity")
	private Double quantity;
    
  	@Column(name="price")
	private Double price;
    
  	@Column(name="executed_at")
	private Date executedAt;
    

}
