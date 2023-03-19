package learn.carbackend.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.carbackend.persistence.CarEntity;
import learn.carbackend.persistence.CarRepository;
import learn.carbackend.request.AddCarRequest;
import learn.carbackend.upload.model.FileDBEntity;
import learn.carbackend.upload.repository.FileDBRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    FileDBRepository fileDBRepository;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PostMapping(
        value = "/addCar",
        consumes = "application/json",
        produces = "application/json")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> addCar(@RequestBody AddCarRequest addCarRequest) {  
        //Get images from database
        Set<FileDBEntity> imageSet = new HashSet<>();
        for (String uuid : addCarRequest.getImageUuids()) {
            imageSet.add(fileDBRepository.findById(uuid).get());
        }
        //Create CarEntity from request
        CarEntity carEntity = new CarEntity(addCarRequest.getBrand(),
            addCarRequest.getModel(), addCarRequest.getColor(),
            addCarRequest.getNumber(), addCarRequest.getYear1(),
            addCarRequest.getPrice(), imageSet);

        carRepository.save(carEntity);

        return ResponseEntity.ok("New car has been added successfully");
    }
}
