package com.freelancers.Stream24.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.freelancers.Stream24.Entity.Video;
import com.freelancers.Stream24.Service.VideoService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class VideoController {

	@Autowired
	private VideoService videoService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadMedia(@RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("video") MultipartFile videoFile,
			@RequestParam("image") MultipartFile imageFile) {
		try {
			Video video = videoService.addMedia(title, description, videoFile, imageFile);
//            return ResponseEntity.ok().body("Upload successful. Video ID: " + video.getId());
			return ResponseEntity.ok().body("Upload successful");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to upload files: " + e.getMessage());
		}
	}

	

	@GetMapping("/videos")
	public ResponseEntity<List<Video>> getMovies() {
		List<Video> movies = videoService.getAllMovies();
		return ResponseEntity.ok(movies);
	}



}
