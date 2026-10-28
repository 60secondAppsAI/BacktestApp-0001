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

import com.BacktestApp.domain.BacktestTrade;
import com.BacktestApp.dto.BacktestTradeDTO;
import com.BacktestApp.dto.BacktestTradeSearchDTO;
import com.BacktestApp.dto.BacktestTradePageDTO;
import com.BacktestApp.service.BacktestTradeService;
import com.BacktestApp.dto.common.RequestDTO;
import com.BacktestApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/backtestTrade")
@RestController
public class BacktestTradeController {

	private final static Logger logger = LoggerFactory.getLogger(BacktestTradeController.class);

	@Autowired
	BacktestTradeService backtestTradeService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<BacktestTrade> getAll() {

		List<BacktestTrade> backtestTrades = backtestTradeService.findAll();
		
		return backtestTrades;	
	}

	@GetMapping(value = "/{backtestTradeId}")
	@ResponseBody
	public BacktestTradeDTO getBacktestTrade(@PathVariable Integer backtestTradeId) {
		
		return (backtestTradeService.getBacktestTradeDTOById(backtestTradeId));
	}

 	@RequestMapping(value = "/addBacktestTrade", method = RequestMethod.POST)
	public ResponseEntity<?> addBacktestTrade(@RequestBody BacktestTradeDTO backtestTradeDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = backtestTradeService.addBacktestTrade(backtestTradeDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/backtestTrades")
	public ResponseEntity<BacktestTradePageDTO> getBacktestTrades(BacktestTradeSearchDTO backtestTradeSearchDTO) {
 
		return backtestTradeService.getBacktestTrades(backtestTradeSearchDTO);
	}	

	@RequestMapping(value = "/updateBacktestTrade", method = RequestMethod.POST)
	public ResponseEntity<?> updateBacktestTrade(@RequestBody BacktestTradeDTO backtestTradeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = backtestTradeService.updateBacktestTrade(backtestTradeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
