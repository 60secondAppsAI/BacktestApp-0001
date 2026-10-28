package com.BacktestApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.BacktestApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.BacktestApp.domain.BacktestParameter;
import com.BacktestApp.dto.BacktestParameterDTO;
import com.BacktestApp.dto.BacktestParameterSearchDTO;
import com.BacktestApp.dto.BacktestParameterPageDTO;
import com.BacktestApp.service.BacktestParameterService;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/backtestParameter")
@RestController
public class BacktestParameterController {

	private final static Logger logger = LoggerFactory.getLogger(BacktestParameterController.class);

	@Autowired
	BacktestParameterService backtestParameterService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<BacktestParameter> getAll() {

		List<BacktestParameter> backtestParameters = backtestParameterService.findAll();
		
		return backtestParameters;	
	}

	@GetMapping(value = "/{backtestParameterId}")
	@ResponseBody
	public BacktestParameterDTO getBacktestParameter(@PathVariable Integer backtestParameterId) {
		
		return (backtestParameterService.getBacktestParameterDTOById(backtestParameterId));
	}

 	@RequestMapping(value = "/addBacktestParameter", method = RequestMethod.POST)
	public ResponseEntity<?> addBacktestParameter(@RequestBody BacktestParameterDTO backtestParameterDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = backtestParameterService.addBacktestParameter(backtestParameterDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/backtestParameters")
	public ResponseEntity<BacktestParameterPageDTO> getBacktestParameters(BacktestParameterSearchDTO backtestParameterSearchDTO) {
 
		return backtestParameterService.getBacktestParameters(backtestParameterSearchDTO);
	}	

	@RequestMapping(value = "/updateBacktestParameter", method = RequestMethod.POST)
	public ResponseEntity<?> updateBacktestParameter(@RequestBody BacktestParameterDTO backtestParameterDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = backtestParameterService.updateBacktestParameter(backtestParameterDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
