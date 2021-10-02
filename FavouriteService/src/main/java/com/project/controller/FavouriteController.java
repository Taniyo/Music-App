package com.project.controller;

import java.util.List;

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
import com.project.exception.MusicFavListEmpty;
import com.project.model.MusicFav;
import com.project.service.MusicFavService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/auth/verfication/favMusic")

public class FavouriteController {
	
	@Autowired
	private MusicFavService ms;
	
	
	@GetMapping("/getallFav")
	public ResponseEntity<?> viewAll() {
		ResponseEntity<?> rs = null;
		try {
			List<MusicFav> blist = ms.findALL();
			rs = ResponseEntity.status(HttpStatus.OK).body(blist);
		} catch (MusicFavListEmpty e) {
			rs = ResponseEntity.status(HttpStatus.CONFLICT).build();

		}
		return rs;
	}
	
	@PostMapping("/addToFavourites")	
	public ResponseEntity<?> saveSong(@RequestBody MusicFav m) {		
		ResponseEntity<?> rs=null;		
		try	{			
			MusicFav mf=ms.saveMusicFav(m);			
			if(mf!=null) {				
				rs=ResponseEntity.status(HttpStatus.CREATED).build();			
				}			
			else {				
				rs=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();			}		}		catch(Exception e)		{			rs=ResponseEntity.status(HttpStatus.CONFLICT).build();		}		return rs;	}

	
	@GetMapping("/getFav/{id}")
	public ResponseEntity<?> getFavMusic(@PathVariable("id") String id) {
		System.out.println("Inside getFAV{id}");
		System.out.println(id);
		ResponseEntity<?> rs = null;
		try {
			MusicFav b = ms.getFavMusicById(id);
			rs = ResponseEntity.status(HttpStatus.OK).body(b);
		} catch (MusicAlreadyExistsException e) {
			rs = ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		System.out.println(rs);
		return rs;
	}

}
