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
@Table(name="backtests")
@Getter @Setter @NoArgsConstructor
public class Backtest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="backtest_id")
	private Integer backtestId;
    
  	@Column(name="strategy_id")
	private Integer strategyId;
    
  	@Column(name="name")
	private String name;
    
  	@Column(name="start_date")
	private Date startDate;
    
  	@Column(name="end_date")
	private Date endDate;
    
  	@Column(name="status")
	private String status;
    

}
