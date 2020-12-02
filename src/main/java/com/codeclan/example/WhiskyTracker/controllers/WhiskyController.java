package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;

    @Autowired
    DistilleryRepository distilleryRepository;

    @GetMapping(value = "/whiskies")
    public ResponseEntity<List<Whisky>> getAllWhiskies(@RequestParam(name = "year", required = false)Integer year,
                                                       @RequestParam(name = "age", required = false)Integer age,
                                                       @RequestParam(name = "distillery", required = false)String distillery,
                                                       @RequestParam(name = "region", required = false)String region){
        if (age != null && distillery != null){
            Distillery foundDistillery = distilleryRepository.findByName(distillery);
            return new ResponseEntity<>(whiskyRepository.findByDistilleryAndAge(foundDistillery, age), HttpStatus.OK);
        }

        if (year != null){
            return new ResponseEntity<>(whiskyRepository.findByYear(year), HttpStatus.OK);
        }
        if (region != null){
            return new ResponseEntity<>(whiskyRepository.findWhiskyByDistilleryRegion(region), HttpStatus.OK);
        }

        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);

    }
}