package com.lask.poopal_server.poopal_server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lask.poopal_server.poopal_server.models.NearestToilet;
import com.lask.poopal_server.poopal_server.models.Review;
import com.lask.poopal_server.poopal_server.models.Toilet;
import com.lask.poopal_server.poopal_server.services.S3Service;
import com.lask.poopal_server.poopal_server.services.ToiletService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonStructure;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/toilet")
public class ToiletController {

    @Autowired
    ToiletService ts;

    @Autowired
    S3Service s3Service;


    // get list of toilets from database
    @GetMapping("/browse/all")
    public ResponseEntity<String> getAllToilets() {
        List<Toilet> toilets = ts.getAllToilets();

        // serialise to jsonp format thingy rip use the method in the model class easier
        // nearter
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Toilet toilet : toilets) {
            jab.add(toilet.toJson());
        }

        JsonStructure js = jab.build();
        System.out.println(js);
        return ResponseEntity.ok(js.toString());
    }

    @GetMapping("/browse/nearest")
    public ResponseEntity<String> getNearestToilets(@RequestParam(name = "lat") Double lat,
            @RequestParam(name = "lon") Double lon) {

        List<NearestToilet> fiveNearest = ts.getNearestToilets(lat, lon);

        // serialise to jsonp format thingy rip use the method in the model class easier
        // nearter
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (NearestToilet toilet : fiveNearest) {
            jab.add(toilet.toJson());
        }

        JsonStructure js = jab.build();
        System.out.println(js);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(js.toString());
    }

    @PostMapping("/review/upload")
    public ResponseEntity<String> uploadReview(  @RequestParam("cleanliness") String cleanliness,
                                            @RequestParam("smell") String smell,
                                            @RequestParam("recommended") String recommended,
                                            @RequestParam("comments") String comments,
                                            @RequestPart("file") MultipartFile file,
                                            @RequestHeader("userId") String userId,
                                            @RequestHeader("toiletId") String toiletId) throws IOException {

        // Upload image to S3 and get back the image URL
        String imageUrl = s3Service.upload(file, comments, userId, toiletId);

        //save review to database
        ts.saveReview(cleanliness, smell, recommended, comments, imageUrl, userId, toiletId);

        return null;
    }

    @GetMapping("/getreviews/{toiletId}")
    public ResponseEntity<String> getReviews(@PathVariable String toiletId) {
        List<Review> reviews = ts.getReviews(toiletId);

        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Review r : reviews) {
            jab.add(r.toJson());
        }
        JsonStructure js = jab.build();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(js.toString());

    }
    

}