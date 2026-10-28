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

import com.BacktestApp.domain.Backtest;
import com.BacktestApp.dto.BacktestDTO;
import com.BacktestApp.dto.BacktestSearchDTO;
import com.BacktestApp.dto.BacktestPageDTO;
import com.BacktestApp.service.BacktestService;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/backtest")
@RestController
public class BacktestController {

	private final static Logger logger = LoggerFactory.getLogger(BacktestController.class);

	@Autowired
	BacktestService backtestService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Backtest> getAll() {

		List<Backtest> backtests = backtestService.findAll();
		
		return backtests;	
	}

	@GetMapping(value = "/{backtestId}")
	@ResponseBody
	public BacktestDTO getBacktest(@PathVariable Integer backtestId) {
		
		return (backtestService.getBacktestDTOById(backtestId));
	}

 	@RequestMapping(value = "/addBacktest", method = RequestMethod.POST)
	public ResponseEntity<?> addBacktest(@RequestBody BacktestDTO backtestDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = backtestService.addBacktest(backtestDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/backtests")
	public ResponseEntity<BacktestPageDTO> getBacktests(BacktestSearchDTO backtestSearchDTO) {
 
		return backtestService.getBacktests(backtestSearchDTO);
	}	

	@RequestMapping(value = "/updateBacktest", method = RequestMethod.POST)
	public ResponseEntity<?> updateBacktest(@RequestBody BacktestDTO backtestDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = backtestService.updateBacktest(backtestDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
