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
import com.BacktestApp.dao.BacktestTradeDAO;
import com.BacktestApp.domain.BacktestTrade;
import com.BacktestApp.dto.BacktestTradeDTO;
import com.BacktestApp.dto.BacktestTradeSearchDTO;
import com.BacktestApp.dto.BacktestTradePageDTO;
import com.BacktestApp.dto.BacktestTradeConvertCriteriaDTO;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;
import com.BacktestApp.service.BacktestTradeService;
import com.BacktestApp.util.ControllerUtils;


@Service
public class BacktestTradeServiceImpl extends GenericServiceImpl<BacktestTrade, Integer> implements BacktestTradeService {

    private final static Logger logger = LoggerFactory.getLogger(BacktestTradeServiceImpl.class);

	@Autowired
	BacktestTradeDAO backtestTradeDao;

	

	@Override
	public GenericDAO<BacktestTrade, Integer> getDAO() {
		return (GenericDAO<BacktestTrade, Integer>) backtestTradeDao;
	}
	
	public List<BacktestTrade> findAll () {
		List<BacktestTrade> backtestTrades = backtestTradeDao.findAll();
		
		return backtestTrades;	
		
	}

	public ResultDTO addBacktestTrade(BacktestTradeDTO backtestTradeDTO, RequestDTO requestDTO) {

		BacktestTrade backtestTrade = new BacktestTrade();

		backtestTrade.setBacktestTradeId(backtestTradeDTO.getBacktestTradeId());

		backtestTrade.setBacktestId(backtestTradeDTO.getBacktestId());

		backtestTrade.setInstrumentId(backtestTradeDTO.getInstrumentId());

		backtestTrade.setSide(backtestTradeDTO.getSide());

		backtestTrade.setQuantity(backtestTradeDTO.getQuantity());

		backtestTrade.setPrice(backtestTradeDTO.getPrice());

		backtestTrade.setExecutedAt(backtestTradeDTO.getExecutedAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		backtestTrade = backtestTradeDao.save(backtestTrade);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<BacktestTrade> getAllBacktestTrades(Pageable pageable) {
		return backtestTradeDao.findAll(pageable);
	}

	public Page<BacktestTrade> getAllBacktestTrades(Specification<BacktestTrade> spec, Pageable pageable) {
		return backtestTradeDao.findAll(spec, pageable);
	}

	public ResponseEntity<BacktestTradePageDTO> getBacktestTrades(BacktestTradeSearchDTO backtestTradeSearchDTO) {
	
			Integer backtestTradeId = backtestTradeSearchDTO.getBacktestTradeId(); 
 			Integer backtestId = backtestTradeSearchDTO.getBacktestId(); 
 			Integer instrumentId = backtestTradeSearchDTO.getInstrumentId(); 
 			String side = backtestTradeSearchDTO.getSide(); 
   			Date executedAt = backtestTradeSearchDTO.getExecutedAt();
 			String sortBy = backtestTradeSearchDTO.getSortBy();
			String sortOrder = backtestTradeSearchDTO.getSortOrder();
			String searchQuery = backtestTradeSearchDTO.getSearchQuery();
			Integer page = backtestTradeSearchDTO.getPage();
			Integer size = backtestTradeSearchDTO.getSize();

	        Specification<BacktestTrade> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, backtestTradeId, "backtestTradeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, backtestId, "backtestId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, side, "side"); 
			
			
			
			spec = ControllerUtils.andIfNecessary(spec, executedAt, "executedAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("side")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<BacktestTrade> backtestTrades = this.getAllBacktestTrades(spec, pageable);
		
		//System.out.println(String.valueOf(backtestTrades.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(backtestTrades.getTotalPages()));
		
		List<BacktestTrade> backtestTradesList = backtestTrades.getContent();
		
		BacktestTradeConvertCriteriaDTO convertCriteria = new BacktestTradeConvertCriteriaDTO();
		List<BacktestTradeDTO> backtestTradeDTOs = this.convertBacktestTradesToBacktestTradeDTOs(backtestTradesList,convertCriteria);
		
		BacktestTradePageDTO backtestTradePageDTO = new BacktestTradePageDTO();
		backtestTradePageDTO.setBacktestTrades(backtestTradeDTOs);
		backtestTradePageDTO.setTotalElements(backtestTrades.getTotalElements());
		return ResponseEntity.ok(backtestTradePageDTO);
	}

	public List<BacktestTradeDTO> convertBacktestTradesToBacktestTradeDTOs(List<BacktestTrade> backtestTrades, BacktestTradeConvertCriteriaDTO convertCriteria) {
		
		List<BacktestTradeDTO> backtestTradeDTOs = new ArrayList<BacktestTradeDTO>();
		
		for (BacktestTrade backtestTrade : backtestTrades) {
			backtestTradeDTOs.add(convertBacktestTradeToBacktestTradeDTO(backtestTrade,convertCriteria));
		}
		
		return backtestTradeDTOs;

	}
	
	public BacktestTradeDTO convertBacktestTradeToBacktestTradeDTO(BacktestTrade backtestTrade, BacktestTradeConvertCriteriaDTO convertCriteria) {
		
		BacktestTradeDTO backtestTradeDTO = new BacktestTradeDTO();

		backtestTradeDTO.setBacktestTradeId(backtestTrade.getBacktestTradeId());

		backtestTradeDTO.setBacktestId(backtestTrade.getBacktestId());

		backtestTradeDTO.setInstrumentId(backtestTrade.getInstrumentId());

		backtestTradeDTO.setSide(backtestTrade.getSide());

		backtestTradeDTO.setQuantity(backtestTrade.getQuantity());

		backtestTradeDTO.setPrice(backtestTrade.getPrice());

		backtestTradeDTO.setExecutedAt(backtestTrade.getExecutedAt());
		
		return backtestTradeDTO;
	}

	public ResultDTO updateBacktestTrade(BacktestTradeDTO backtestTradeDTO, RequestDTO requestDTO) {
		
		BacktestTrade backtestTrade = backtestTradeDao.getById(backtestTradeDTO.getBacktestTradeId());
		
		backtestTrade.setBacktestTradeId(ControllerUtils.setValue(backtestTrade.getBacktestTradeId(), backtestTradeDTO.getBacktestTradeId()));
		
		backtestTrade.setBacktestId(ControllerUtils.setValue(backtestTrade.getBacktestId(), backtestTradeDTO.getBacktestId()));
		
		backtestTrade.setInstrumentId(ControllerUtils.setValue(backtestTrade.getInstrumentId(), backtestTradeDTO.getInstrumentId()));
		
		backtestTrade.setSide(ControllerUtils.setValue(backtestTrade.getSide(), backtestTradeDTO.getSide()));
		
		backtestTrade.setQuantity(ControllerUtils.setValue(backtestTrade.getQuantity(), backtestTradeDTO.getQuantity()));
		
		backtestTrade.setPrice(ControllerUtils.setValue(backtestTrade.getPrice(), backtestTradeDTO.getPrice()));
		
		backtestTrade.setExecutedAt(ControllerUtils.setValue(backtestTrade.getExecutedAt(), backtestTradeDTO.getExecutedAt()));

        backtestTrade = backtestTradeDao.save(backtestTrade);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BacktestTradeDTO getBacktestTradeDTOById(Integer backtestTradeId) {
	
		BacktestTrade backtestTrade = backtestTradeDao.getById(backtestTradeId);
		
		BacktestTradeConvertCriteriaDTO convertCriteria = new BacktestTradeConvertCriteriaDTO();
		return(this.convertBacktestTradeToBacktestTradeDTO(backtestTrade,convertCriteria));
	}

}
