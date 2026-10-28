package com.BacktestApp.dao;

import java.util.List;
import java.util.Date;

import com.BacktestApp.dao.GenericDAO;
import com.BacktestApp.domain.Backtest;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface BacktestDAO extends GenericDAO<Backtest, Integer> {
  
	List<Backtest> findAll();
	


}

