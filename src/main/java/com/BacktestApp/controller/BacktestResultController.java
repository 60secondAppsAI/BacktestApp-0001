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

import com.BacktestApp.domain.BacktestResult;
import com.BacktestApp.dto.BacktestResultDTO;
import com.BacktestApp.dto.BacktestResultSearchDTO;
import com.BacktestApp.dto.BacktestResultPageDTO;
import com.BacktestApp.service.BacktestResultService;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/backtestResult")
@RestController
public class BacktestResultController {

	private final static Logger logger = LoggerFactory.getLogger(BacktestResultController.class);

	@Autowired
	BacktestResultService backtestResultService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<BacktestResult> getAll() {

		List<BacktestResult> backtestResults = backtestResultService.findAll();
		
		return backtestResults;	
	}

	@GetMapping(value = "/{backtestResultId}")
	@ResponseBody
	public BacktestResultDTO getBacktestResult(@PathVariable Integer backtestResultId) {
		
		return (backtestResultService.getBacktestResultDTOById(backtestResultId));
	}

 	@RequestMapping(value = "/addBacktestResult", method = RequestMethod.POST)
	public ResponseEntity<?> addBacktestResult(@RequestBody BacktestResultDTO backtestResultDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = backtestResultService.addBacktestResult(backtestResultDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/backtestResults")
	public ResponseEntity<BacktestResultPageDTO> getBacktestResults(BacktestResultSearchDTO backtestResultSearchDTO) {
 
		return backtestResultService.getBacktestResults(backtestResultSearchDTO);
	}	

	@RequestMapping(value = "/updateBacktestResult", method = RequestMethod.POST)
	public ResponseEntity<?> updateBacktestResult(@RequestBody BacktestResultDTO backtestResultDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = backtestResultService.updateBacktestResult(backtestResultDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
