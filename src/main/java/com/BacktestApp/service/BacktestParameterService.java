package com.BacktestApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.BacktestApp.domain.BacktestParameter;
import com.BacktestApp.dto.BacktestParameterDTO;
import com.BacktestApp.dto.BacktestParameterSearchDTO;
import com.BacktestApp.dto.BacktestParameterPageDTO;
import com.BacktestApp.dto.BacktestParameterConvertCriteriaDTO;
import com.BacktestApp.service.GenericService;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface BacktestParameterService extends GenericService<BacktestParameter, Integer> {

	List<BacktestParameter> findAll();

	ResultDTO addBacktestParameter(BacktestParameterDTO backtestParameterDTO, RequestDTO requestDTO);

	ResultDTO updateBacktestParameter(BacktestParameterDTO backtestParameterDTO, RequestDTO requestDTO);

    Page<BacktestParameter> getAllBacktestParameters(Pageable pageable);

    Page<BacktestParameter> getAllBacktestParameters(Specification<BacktestParameter> spec, Pageable pageable);

	ResponseEntity<BacktestParameterPageDTO> getBacktestParameters(BacktestParameterSearchDTO backtestParameterSearchDTO);
	
	List<BacktestParameterDTO> convertBacktestParametersToBacktestParameterDTOs(List<BacktestParameter> backtestParameters, BacktestParameterConvertCriteriaDTO convertCriteria);

	BacktestParameterDTO getBacktestParameterDTOById(Integer backtestParameterId);



	
}
