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
import com.project.model.Comment;

import com.project.service.CommentService;


@CrossOrigin("*")
@RestController
@RequestMapping("/auth/verfication/favMusic")
public class CommentsController {

	@Autowired
	private CommentService commentService;



	@GetMapping("/getCmt/{artistSongName}")
	public ResponseEntity<?> getcmt(@PathVariable("artistSongName") String artistSongName) {
		ResponseEntity<?> rs = null;

		try {

			List<Comment> cmt = commentService.findBySongName(artistSongName);
			rs = ResponseEntity.status(HttpStatus.OK).body(cmt);

		} catch (Exception e) {
			rs = ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return rs;
	}

	

	

	@PostMapping("/saveCmt")
	public ResponseEntity<?> savecmt(@RequestBody Comment c) {
		ResponseEntity<?> rs = null;

		try {

			Comment comment = commentService.saveComment(c);
			System.out.println(c.toString());
			if (comment != null) {
				rs = ResponseEntity.status(HttpStatus.CREATED).build();
			} else {
				rs = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			rs = ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return rs;

	}

	

}
