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
@Table(name="backtest_parameters")
@Getter @Setter @NoArgsConstructor
public class BacktestParameter {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="backtest_parameter_id")
	private Integer backtestParameterId;
    
  	@Column(name="backtest_id")
	private Integer backtestId;
    
  	@Column(name="parameter_name")
	private String parameterName;
    
  	@Column(name="parameter_value")
	private String parameterValue;
    

}
