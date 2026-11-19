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
import com.BacktestApp.dao.BacktestDAO;
import com.BacktestApp.domain.Backtest;
import com.BacktestApp.dto.BacktestDTO;
import com.BacktestApp.dto.BacktestSearchDTO;
import com.BacktestApp.dto.BacktestPageDTO;
import com.BacktestApp.dto.BacktestConvertCriteriaDTO;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;
import com.BacktestApp.service.BacktestService;
import com.BacktestApp.util.ControllerUtils;


@Service
public class BacktestServiceImpl extends GenericServiceImpl<Backtest, Integer> implements BacktestService {

    private final static Logger logger = LoggerFactory.getLogger(BacktestServiceImpl.class);

	@Autowired
	BacktestDAO backtestDao;

	

	@Override
	public GenericDAO<Backtest, Integer> getDAO() {
		return (GenericDAO<Backtest, Integer>) backtestDao;
	}
	
	public List<Backtest> findAll () {
		List<Backtest> backtests = backtestDao.findAll();
		
		return backtests;	
		
	}

	public ResultDTO addBacktest(BacktestDTO backtestDTO, RequestDTO requestDTO) {

		Backtest backtest = new Backtest();

		backtest.setBacktestId(backtestDTO.getBacktestId());

		backtest.setStrategyId(backtestDTO.getStrategyId());

		backtest.setName(backtestDTO.getName());

		backtest.setStartDate(backtestDTO.getStartDate());

		backtest.setEndDate(backtestDTO.getEndDate());

		backtest.setStatus(backtestDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		backtest = backtestDao.save(backtest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Backtest> getAllBacktests(Pageable pageable) {
		return backtestDao.findAll(pageable);
	}

	public Page<Backtest> getAllBacktests(Specification<Backtest> spec, Pageable pageable) {
		return backtestDao.findAll(spec, pageable);
	}

	public ResponseEntity<BacktestPageDTO> getBacktests(BacktestSearchDTO backtestSearchDTO) {
	
			Integer backtestId = backtestSearchDTO.getBacktestId(); 
 			Integer strategyId = backtestSearchDTO.getStrategyId(); 
 			String name = backtestSearchDTO.getName(); 
 			Date startDate = backtestSearchDTO.getStartDate();
 			Date endDate = backtestSearchDTO.getEndDate();
 			String status = backtestSearchDTO.getStatus(); 
 			String sortBy = backtestSearchDTO.getSortBy();
			String sortOrder = backtestSearchDTO.getSortOrder();
			String searchQuery = backtestSearchDTO.getSearchQuery();
			Integer page = backtestSearchDTO.getPage();
			Integer size = backtestSearchDTO.getSize();

	        Specification<Backtest> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, backtestId, "backtestId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, strategyId, "strategyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, startDate, "startDate");
			
			spec = ControllerUtils.andIfNecessary(spec, endDate, "endDate");
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Backtest> backtests = this.getAllBacktests(spec, pageable);
		
		//System.out.println(String.valueOf(backtests.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(backtests.getTotalPages()));
		
		List<Backtest> backtestsList = backtests.getContent();
		
		BacktestConvertCriteriaDTO convertCriteria = new BacktestConvertCriteriaDTO();
		List<BacktestDTO> backtestDTOs = this.convertBacktestsToBacktestDTOs(backtestsList,convertCriteria);
		
		BacktestPageDTO backtestPageDTO = new BacktestPageDTO();
		backtestPageDTO.setBacktests(backtestDTOs);
		backtestPageDTO.setTotalElements(backtests.getTotalElements());
		return ResponseEntity.ok(backtestPageDTO);
	}

	public List<BacktestDTO> convertBacktestsToBacktestDTOs(List<Backtest> backtests, BacktestConvertCriteriaDTO convertCriteria) {
		
		List<BacktestDTO> backtestDTOs = new ArrayList<BacktestDTO>();
		
		for (Backtest backtest : backtests) {
			backtestDTOs.add(convertBacktestToBacktestDTO(backtest,convertCriteria));
		}
		
		return backtestDTOs;

	}
	
	public BacktestDTO convertBacktestToBacktestDTO(Backtest backtest, BacktestConvertCriteriaDTO convertCriteria) {
		
		BacktestDTO backtestDTO = new BacktestDTO();

		backtestDTO.setBacktestId(backtest.getBacktestId());

		backtestDTO.setStrategyId(backtest.getStrategyId());

		backtestDTO.setName(backtest.getName());

		backtestDTO.setStartDate(backtest.getStartDate());

		backtestDTO.setEndDate(backtest.getEndDate());

		backtestDTO.setStatus(backtest.getStatus());
		
		return backtestDTO;
	}

	public ResultDTO updateBacktest(BacktestDTO backtestDTO, RequestDTO requestDTO) {
		
		Backtest backtest = backtestDao.getById(backtestDTO.getBacktestId());
		
		backtest.setBacktestId(ControllerUtils.setValue(backtest.getBacktestId(), backtestDTO.getBacktestId()));
		
		backtest.setStrategyId(ControllerUtils.setValue(backtest.getStrategyId(), backtestDTO.getStrategyId()));
		
		backtest.setName(ControllerUtils.setValue(backtest.getName(), backtestDTO.getName()));
		
		backtest.setStartDate(ControllerUtils.setValue(backtest.getStartDate(), backtestDTO.getStartDate()));
		
		backtest.setEndDate(ControllerUtils.setValue(backtest.getEndDate(), backtestDTO.getEndDate()));
		
		backtest.setStatus(ControllerUtils.setValue(backtest.getStatus(), backtestDTO.getStatus()));

        backtest = backtestDao.save(backtest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BacktestDTO getBacktestDTOById(Integer backtestId) {
	
		Backtest backtest = backtestDao.getById(backtestId);
		
		BacktestConvertCriteriaDTO convertCriteria = new BacktestConvertCriteriaDTO();
		return(this.convertBacktestToBacktestDTO(backtest,convertCriteria));
	}

}
