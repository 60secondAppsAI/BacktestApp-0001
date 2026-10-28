package com.BacktestApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.BacktestApp.domain.BacktestTrade;
import com.BacktestApp.dto.BacktestTradeDTO;
import com.BacktestApp.dto.BacktestTradeSearchDTO;
import com.BacktestApp.dto.BacktestTradePageDTO;
import com.BacktestApp.dto.BacktestTradeConvertCriteriaDTO;
import com.BacktestApp.service.GenericService;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface BacktestTradeService extends GenericService<BacktestTrade, Integer> {

	List<BacktestTrade> findAll();

	ResultDTO addBacktestTrade(BacktestTradeDTO backtestTradeDTO, RequestDTO requestDTO);

	ResultDTO updateBacktestTrade(BacktestTradeDTO backtestTradeDTO, RequestDTO requestDTO);

    Page<BacktestTrade> getAllBacktestTrades(Pageable pageable);

    Page<BacktestTrade> getAllBacktestTrades(Specification<BacktestTrade> spec, Pageable pageable);

	ResponseEntity<BacktestTradePageDTO> getBacktestTrades(BacktestTradeSearchDTO backtestTradeSearchDTO);
	
	List<BacktestTradeDTO> convertBacktestTradesToBacktestTradeDTOs(List<BacktestTrade> backtestTrades, BacktestTradeConvertCriteriaDTO convertCriteria);

	BacktestTradeDTO getBacktestTradeDTOById(Integer backtestTradeId);



	
}
