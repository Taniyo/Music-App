package com.project.controller;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.exception.MusicAlreadyExistsException;

import com.project.exception.SongNameDoesnotExists;
import com.project.model.UserFavMap;

import com.project.service.UserFavMapService;

import io.jsonwebtoken.Claims;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/auth/verfication/favMusic")
public class MusicFavController {
	@Autowired
	private UserFavMapService ufms;
	
	
	@GetMapping("/getFavMap")
	public ResponseEntity<?> getMap(ServletRequest req) {
//		System.out.println(req.getAttribute("claims").get);
		Claims c = (Claims) req.getAttribute("claims");
		System.out.println(c.getId());
		System.out.println("XXXX");
		System.out.println("ANkita");
		ResponseEntity<?> rs = null;
		try {
			UserFavMap b = ufms.getFavMapByEmailUser(c.getId());
			rs = ResponseEntity.status(HttpStatus.OK).body(b);
		} catch (MusicAlreadyExistsException e) {
			rs = ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return rs;
	}
	
	@PostMapping("/saveMap")
	public ResponseEntity<?> saveMapList(@RequestBody UserFavMap mf) {
		ResponseEntity<?> rs = null;
		try {
			System.out.println(mf.getUserPref());
			UserFavMap bk = ufms.saveList(mf);
			if (bk != null)
				rs = ResponseEntity.status(HttpStatus.CREATED).build();
			else
				rs = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		} catch (MusicAlreadyExistsException e) {
			rs = ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return rs;

	}
	
	
}