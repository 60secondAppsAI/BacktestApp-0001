package com.BacktestApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.BacktestApp.domain.Backtest;
import com.BacktestApp.dto.BacktestDTO;
import com.BacktestApp.dto.BacktestSearchDTO;
import com.BacktestApp.dto.BacktestPageDTO;
import com.BacktestApp.dto.BacktestConvertCriteriaDTO;
import com.BacktestApp.service.GenericService;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface BacktestService extends GenericService<Backtest, Integer> {

	List<Backtest> findAll();

	ResultDTO addBacktest(BacktestDTO backtestDTO, RequestDTO requestDTO);

	ResultDTO updateBacktest(BacktestDTO backtestDTO, RequestDTO requestDTO);

    Page<Backtest> getAllBacktests(Pageable pageable);

    Page<Backtest> getAllBacktests(Specification<Backtest> spec, Pageable pageable);

	ResponseEntity<BacktestPageDTO> getBacktests(BacktestSearchDTO backtestSearchDTO);
	
	List<BacktestDTO> convertBacktestsToBacktestDTOs(List<Backtest> backtests, BacktestConvertCriteriaDTO convertCriteria);

	BacktestDTO getBacktestDTOById(Integer backtestId);



	
}
