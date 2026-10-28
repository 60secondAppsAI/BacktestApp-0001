package com.BacktestApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.BacktestApp.dao.GenericDAO;
import com.BacktestApp.service.GenericService;
import com.BacktestApp.service.impl.GenericServiceImpl;
import com.BacktestApp.dao.BacktestResultDAO;
import com.BacktestApp.domain.BacktestResult;
import com.BacktestApp.dto.BacktestResultDTO;
import com.BacktestApp.dto.BacktestResultSearchDTO;
import com.BacktestApp.dto.BacktestResultPageDTO;
import com.BacktestApp.dto.BacktestResultConvertCriteriaDTO;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;
import com.BacktestApp.service.BacktestResultService;
import com.BacktestApp.util.ControllerUtils;


@Service
public class BacktestResultServiceImpl extends GenericServiceImpl<BacktestResult, Integer> implements BacktestResultService {

    private final static Logger logger = LoggerFactory.getLogger(BacktestResultServiceImpl.class);

	@Autowired
	BacktestResultDAO backtestResultDao;

	

	@Override
	public GenericDAO<BacktestResult, Integer> getDAO() {
		return (GenericDAO<BacktestResult, Integer>) backtestResultDao;
	}
	
	public List<BacktestResult> findAll () {
		List<BacktestResult> backtestResults = backtestResultDao.findAll();
		
		return backtestResults;	
		
	}

	public ResultDTO addBacktestResult(BacktestResultDTO backtestResultDTO, RequestDTO requestDTO) {

		BacktestResult backtestResult = new BacktestResult();

		backtestResult.setBacktestResultId(backtestResultDTO.getBacktestResultId());

		backtestResult.setBacktestId(backtestResultDTO.getBacktestId());

		backtestResult.setTotalReturn(backtestResultDTO.getTotalReturn());

		backtestResult.setMaxDrawdown(backtestResultDTO.getMaxDrawdown());

		backtestResult.setSharpeRatio(backtestResultDTO.getSharpeRatio());

		backtestResult.setTradeCount(backtestResultDTO.getTradeCount());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		backtestResult = backtestResultDao.save(backtestResult);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<BacktestResult> getAllBacktestResults(Pageable pageable) {
		return backtestResultDao.findAll(pageable);
	}

	public Page<BacktestResult> getAllBacktestResults(Specification<BacktestResult> spec, Pageable pageable) {
		return backtestResultDao.findAll(spec, pageable);
	}

	public ResponseEntity<BacktestResultPageDTO> getBacktestResults(BacktestResultSearchDTO backtestResultSearchDTO) {
	
			Integer backtestResultId = backtestResultSearchDTO.getBacktestResultId(); 
 			Integer backtestId = backtestResultSearchDTO.getBacktestId(); 
    			Integer tradeCount = backtestResultSearchDTO.getTradeCount(); 
 			String sortBy = backtestResultSearchDTO.getSortBy();
			String sortOrder = backtestResultSearchDTO.getSortOrder();
			String searchQuery = backtestResultSearchDTO.getSearchQuery();
			Integer page = backtestResultSearchDTO.getPage();
			Integer size = backtestResultSearchDTO.getSize();

	        Specification<BacktestResult> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, backtestResultId, "backtestResultId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, backtestId, "backtestId"); 
			
			
			
			
			spec = ControllerUtils.andIfNecessary(spec, tradeCount, "tradeCount"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<BacktestResult> backtestResults = this.getAllBacktestResults(spec, pageable);
		
		//System.out.println(String.valueOf(backtestResults.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(backtestResults.getTotalPages()));
		
		List<BacktestResult> backtestResultsList = backtestResults.getContent();
		
		BacktestResultConvertCriteriaDTO convertCriteria = new BacktestResultConvertCriteriaDTO();
		List<BacktestResultDTO> backtestResultDTOs = this.convertBacktestResultsToBacktestResultDTOs(backtestResultsList,convertCriteria);
		
		BacktestResultPageDTO backtestResultPageDTO = new BacktestResultPageDTO();
		backtestResultPageDTO.setBacktestResults(backtestResultDTOs);
		backtestResultPageDTO.setTotalElements(backtestResults.getTotalElements());
		return ResponseEntity.ok(backtestResultPageDTO);
	}

	public List<BacktestResultDTO> convertBacktestResultsToBacktestResultDTOs(List<BacktestResult> backtestResults, BacktestResultConvertCriteriaDTO convertCriteria) {
		
		List<BacktestResultDTO> backtestResultDTOs = new ArrayList<BacktestResultDTO>();
		
		for (BacktestResult backtestResult : backtestResults) {
			backtestResultDTOs.add(convertBacktestResultToBacktestResultDTO(backtestResult,convertCriteria));
		}
		
		return backtestResultDTOs;

	}
	
	public BacktestResultDTO convertBacktestResultToBacktestResultDTO(BacktestResult backtestResult, BacktestResultConvertCriteriaDTO convertCriteria) {
		
		BacktestResultDTO backtestResultDTO = new BacktestResultDTO();

		backtestResultDTO.setBacktestResultId(backtestResult.getBacktestResultId());

		backtestResultDTO.setBacktestId(backtestResult.getBacktestId());

		backtestResultDTO.setTotalReturn(backtestResult.getTotalReturn());

		backtestResultDTO.setMaxDrawdown(backtestResult.getMaxDrawdown());

		backtestResultDTO.setSharpeRatio(backtestResult.getSharpeRatio());

		backtestResultDTO.setTradeCount(backtestResult.getTradeCount());
		
		return backtestResultDTO;
	}

	public ResultDTO updateBacktestResult(BacktestResultDTO backtestResultDTO, RequestDTO requestDTO) {
		
		BacktestResult backtestResult = backtestResultDao.getById(backtestResultDTO.getBacktestResultId());
		
		backtestResult.setBacktestResultId(ControllerUtils.setValue(backtestResult.getBacktestResultId(), backtestResultDTO.getBacktestResultId()));
		
		backtestResult.setBacktestId(ControllerUtils.setValue(backtestResult.getBacktestId(), backtestResultDTO.getBacktestId()));
		
		backtestResult.setTotalReturn(ControllerUtils.setValue(backtestResult.getTotalReturn(), backtestResultDTO.getTotalReturn()));
		
		backtestResult.setMaxDrawdown(ControllerUtils.setValue(backtestResult.getMaxDrawdown(), backtestResultDTO.getMaxDrawdown()));
		
		backtestResult.setSharpeRatio(ControllerUtils.setValue(backtestResult.getSharpeRatio(), backtestResultDTO.getSharpeRatio()));
		
		backtestResult.setTradeCount(ControllerUtils.setValue(backtestResult.getTradeCount(), backtestResultDTO.getTradeCount()));

        backtestResult = backtestResultDao.save(backtestResult);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BacktestResultDTO getBacktestResultDTOById(Integer backtestResultId) {
	
		BacktestResult backtestResult = backtestResultDao.getById(backtestResultId);
		
		BacktestResultConvertCriteriaDTO convertCriteria = new BacktestResultConvertCriteriaDTO();
		return(this.convertBacktestResultToBacktestResultDTO(backtestResult,convertCriteria));
	}

}
