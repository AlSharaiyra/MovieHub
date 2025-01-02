package com.AAAW.MovieAPP.Controllers;

import com.AAAW.MovieAPP.Repositories.MediaRepo;
import com.AAAW.MovieAPP.models.Media;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaRepo mediaRepo;

    public MediaController(MediaRepo mediaRepo) {
        this.mediaRepo = mediaRepo;
    }


    @GetMapping("/")
    public List<Media> getAllMedia(){
        return mediaRepo.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMedia(@RequestBody Media media){
        try{
            mediaRepo.save(media);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user. Reason: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Media added successfully");
    }


}
