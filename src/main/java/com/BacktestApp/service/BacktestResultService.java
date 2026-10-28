package com.BacktestApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.BacktestApp.domain.BacktestResult;
import com.BacktestApp.dto.BacktestResultDTO;
import com.BacktestApp.dto.BacktestResultSearchDTO;
import com.BacktestApp.dto.BacktestResultPageDTO;
import com.BacktestApp.dto.BacktestResultConvertCriteriaDTO;
import com.BacktestApp.service.GenericService;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface BacktestResultService extends GenericService<BacktestResult, Integer> {

	List<BacktestResult> findAll();

	ResultDTO addBacktestResult(BacktestResultDTO backtestResultDTO, RequestDTO requestDTO);

	ResultDTO updateBacktestResult(BacktestResultDTO backtestResultDTO, RequestDTO requestDTO);

    Page<BacktestResult> getAllBacktestResults(Pageable pageable);

    Page<BacktestResult> getAllBacktestResults(Specification<BacktestResult> spec, Pageable pageable);

	ResponseEntity<BacktestResultPageDTO> getBacktestResults(BacktestResultSearchDTO backtestResultSearchDTO);
	
	List<BacktestResultDTO> convertBacktestResultsToBacktestResultDTOs(List<BacktestResult> backtestResults, BacktestResultConvertCriteriaDTO convertCriteria);

	BacktestResultDTO getBacktestResultDTOById(Integer backtestResultId);



	
}
