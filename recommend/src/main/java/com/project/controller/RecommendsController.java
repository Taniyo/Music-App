package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.exception.RecommendationListEmpty;
import com.project.exception.SongNameDoesnotExists;
import com.project.model.Recommendation;
import com.project.service.RecommendationService;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth/verfication/favMusic")
public class RecommendsController {
	
	@Autowired
	private RecommendationService rsd;
	
	@GetMapping("/getAllRecom")
	public ResponseEntity<?> recommendAll() {
		ResponseEntity<?> rs = null;
		try {
			List<Recommendation> blist = rsd.findALL();
			rs = ResponseEntity.status(HttpStatus.OK).body(blist);
		} catch (RecommendationListEmpty e) {
			rs = ResponseEntity.status(HttpStatus.CONFLICT).build();

		}
		return rs;
	}
	
	@PostMapping("/addRecom")
	public ResponseEntity<?> saveRecommendationcount(@RequestBody Recommendation mf) throws SongNameDoesnotExists {
		ResponseEntity<?> rs = null;
		try {
			Recommendation bk = rsd.saveRecom(mf);
			if (bk != null)
				rs = ResponseEntity.status(HttpStatus.CREATED).build();
			else
				rs = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		} catch (SongNameDoesnotExists e) {
			rs = ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return rs;

	}


}
