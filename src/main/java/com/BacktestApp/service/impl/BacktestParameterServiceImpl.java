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
import com.BacktestApp.dao.BacktestParameterDAO;
import com.BacktestApp.domain.BacktestParameter;
import com.BacktestApp.dto.BacktestParameterDTO;
import com.BacktestApp.dto.BacktestParameterSearchDTO;
import com.BacktestApp.dto.BacktestParameterPageDTO;
import com.BacktestApp.dto.BacktestParameterConvertCriteriaDTO;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;
import com.BacktestApp.service.BacktestParameterService;
import com.BacktestApp.util.ControllerUtils;


@Service
public class BacktestParameterServiceImpl extends GenericServiceImpl<BacktestParameter, Integer> implements BacktestParameterService {

    private final static Logger logger = LoggerFactory.getLogger(BacktestParameterServiceImpl.class);

	@Autowired
	BacktestParameterDAO backtestParameterDao;

	

	@Override
	public GenericDAO<BacktestParameter, Integer> getDAO() {
		return (GenericDAO<BacktestParameter, Integer>) backtestParameterDao;
	}
	
	public List<BacktestParameter> findAll () {
		List<BacktestParameter> backtestParameters = backtestParameterDao.findAll();
		
		return backtestParameters;	
		
	}

	public ResultDTO addBacktestParameter(BacktestParameterDTO backtestParameterDTO, RequestDTO requestDTO) {

		BacktestParameter backtestParameter = new BacktestParameter();

		backtestParameter.setBacktestParameterId(backtestParameterDTO.getBacktestParameterId());

		backtestParameter.setBacktestId(backtestParameterDTO.getBacktestId());

		backtestParameter.setParameterName(backtestParameterDTO.getParameterName());

		backtestParameter.setParameterValue(backtestParameterDTO.getParameterValue());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		backtestParameter = backtestParameterDao.save(backtestParameter);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<BacktestParameter> getAllBacktestParameters(Pageable pageable) {
		return backtestParameterDao.findAll(pageable);
	}

	public Page<BacktestParameter> getAllBacktestParameters(Specification<BacktestParameter> spec, Pageable pageable) {
		return backtestParameterDao.findAll(spec, pageable);
	}

	public ResponseEntity<BacktestParameterPageDTO> getBacktestParameters(BacktestParameterSearchDTO backtestParameterSearchDTO) {
	
			Integer backtestParameterId = backtestParameterSearchDTO.getBacktestParameterId(); 
 			Integer backtestId = backtestParameterSearchDTO.getBacktestId(); 
 			String parameterName = backtestParameterSearchDTO.getParameterName(); 
 			String parameterValue = backtestParameterSearchDTO.getParameterValue(); 
 			String sortBy = backtestParameterSearchDTO.getSortBy();
			String sortOrder = backtestParameterSearchDTO.getSortOrder();
			String searchQuery = backtestParameterSearchDTO.getSearchQuery();
			Integer page = backtestParameterSearchDTO.getPage();
			Integer size = backtestParameterSearchDTO.getSize();

	        Specification<BacktestParameter> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, backtestParameterId, "backtestParameterId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, backtestId, "backtestId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, parameterName, "parameterName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, parameterValue, "parameterValue"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("parameterName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("parameterValue")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<BacktestParameter> backtestParameters = this.getAllBacktestParameters(spec, pageable);
		
		//System.out.println(String.valueOf(backtestParameters.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(backtestParameters.getTotalPages()));
		
		List<BacktestParameter> backtestParametersList = backtestParameters.getContent();
		
		BacktestParameterConvertCriteriaDTO convertCriteria = new BacktestParameterConvertCriteriaDTO();
		List<BacktestParameterDTO> backtestParameterDTOs = this.convertBacktestParametersToBacktestParameterDTOs(backtestParametersList,convertCriteria);
		
		BacktestParameterPageDTO backtestParameterPageDTO = new BacktestParameterPageDTO();
		backtestParameterPageDTO.setBacktestParameters(backtestParameterDTOs);
		backtestParameterPageDTO.setTotalElements(backtestParameters.getTotalElements());
		return ResponseEntity.ok(backtestParameterPageDTO);
	}

	public List<BacktestParameterDTO> convertBacktestParametersToBacktestParameterDTOs(List<BacktestParameter> backtestParameters, BacktestParameterConvertCriteriaDTO convertCriteria) {
		
		List<BacktestParameterDTO> backtestParameterDTOs = new ArrayList<BacktestParameterDTO>();
		
		for (BacktestParameter backtestParameter : backtestParameters) {
			backtestParameterDTOs.add(convertBacktestParameterToBacktestParameterDTO(backtestParameter,convertCriteria));
		}
		
		return backtestParameterDTOs;

	}
	
	public BacktestParameterDTO convertBacktestParameterToBacktestParameterDTO(BacktestParameter backtestParameter, BacktestParameterConvertCriteriaDTO convertCriteria) {
		
		BacktestParameterDTO backtestParameterDTO = new BacktestParameterDTO();

		backtestParameterDTO.setBacktestParameterId(backtestParameter.getBacktestParameterId());

		backtestParameterDTO.setBacktestId(backtestParameter.getBacktestId());

		backtestParameterDTO.setParameterName(backtestParameter.getParameterName());

		backtestParameterDTO.setParameterValue(backtestParameter.getParameterValue());
		
		return backtestParameterDTO;
	}

	public ResultDTO updateBacktestParameter(BacktestParameterDTO backtestParameterDTO, RequestDTO requestDTO) {
		
		BacktestParameter backtestParameter = backtestParameterDao.getById(backtestParameterDTO.getBacktestParameterId());
		
		backtestParameter.setBacktestParameterId(ControllerUtils.setValue(backtestParameter.getBacktestParameterId(), backtestParameterDTO.getBacktestParameterId()));
		
		backtestParameter.setBacktestId(ControllerUtils.setValue(backtestParameter.getBacktestId(), backtestParameterDTO.getBacktestId()));
		
		backtestParameter.setParameterName(ControllerUtils.setValue(backtestParameter.getParameterName(), backtestParameterDTO.getParameterName()));
		
		backtestParameter.setParameterValue(ControllerUtils.setValue(backtestParameter.getParameterValue(), backtestParameterDTO.getParameterValue()));

        backtestParameter = backtestParameterDao.save(backtestParameter);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BacktestParameterDTO getBacktestParameterDTOById(Integer backtestParameterId) {
	
		BacktestParameter backtestParameter = backtestParameterDao.getById(backtestParameterId);
		
		BacktestParameterConvertCriteriaDTO convertCriteria = new BacktestParameterConvertCriteriaDTO();
		return(this.convertBacktestParameterToBacktestParameterDTO(backtestParameter,convertCriteria));
	}

}
