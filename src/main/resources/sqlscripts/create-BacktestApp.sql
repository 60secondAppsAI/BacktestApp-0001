CREATE DATABASE IF NOT EXISTS BacktestApp;

CREATE TABLE BacktestApp.backtests (
    `backtest_id` INT AUTO_INCREMENT,
    `strategy_id` INT,
    `name` VARCHAR(255),
    `start_date` DATETIME,
    `end_date` DATETIME,
    `status` VARCHAR(255),
PRIMARY KEY (backtest_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE BacktestApp.backtest_parameters (
    `backtest_parameter_id` INT AUTO_INCREMENT,
    `backtest_id` INT,
    `parameter_name` VARCHAR(255),
    `parameter_value` VARCHAR(255),
PRIMARY KEY (backtest_parameter_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE BacktestApp.backtest_trades (
    `backtest_trade_id` INT AUTO_INCREMENT,
    `backtest_id` INT,
    `instrument_id` INT,
    `side` VARCHAR(255),
    `quantity` DOUBLE,
    `price` DOUBLE,
    `executed_at` DATETIME,
PRIMARY KEY (backtest_trade_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE BacktestApp.backtest_results (
    `backtest_result_id` INT AUTO_INCREMENT,
    `backtest_id` INT,
    `total_return` DOUBLE,
    `max_drawdown` DOUBLE,
    `sharpe_ratio` DOUBLE,
    `trade_count` INT,
PRIMARY KEY (backtest_result_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
