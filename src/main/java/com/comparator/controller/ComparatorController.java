package com.comparator.controller;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comparator.model.CompareInput;
import com.comparator.model.JsonDiff;
import com.comparator.service.IComparatorService;
import com.fasterxml.jackson.core.JsonParseException;

@RestController
@RequestMapping("/comparator")
public class ComparatorController {
	private final Logger		logger	= LoggerFactory.getLogger(ComparatorController.class);

	@Autowired
	private IComparatorService	comparatorService;

	@RequestMapping(value = "/compare", method = RequestMethod.POST)
	public ResponseEntity<String> runCompare(@RequestBody CompareInput compare) {
		JsonDiff jsonDiff;
		String diff = "";
		//CompareOutput cmp = new CompareOutput();
		try {
			logger.info("start compare");
			jsonDiff = comparatorService.compareJson(compare);
			diff = jsonDiff.diff.toString();
			logger.info("finish compare");
		} catch (JsonParseException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(diff, HttpStatus.OK);
	}

	@RequestMapping(value = "/compare/details", method = RequestMethod.POST)
	public ResponseEntity<JsonDiff> runCompareDetails(@RequestBody CompareInput compare) {
		JsonDiff jsonDiff;
		try {
			logger.info("start compare");
			jsonDiff = comparatorService.compareJson(compare);
			logger.info("finish compare");
		} catch (JsonParseException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<JsonDiff>(jsonDiff, HttpStatus.OK);
	}
}
