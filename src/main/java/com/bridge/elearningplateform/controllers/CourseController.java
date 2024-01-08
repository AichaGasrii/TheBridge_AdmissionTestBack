package com.bridge.elearningplateform.controllers;

import com.bridge.elearningplateform.entities.Course;
import com.bridge.elearningplateform.services.ICourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@AllArgsConstructor
// Marks the class as a REST controller, with all handler methods mapped to the '/course' path.
@RestController
@RequestMapping("/course")
public class CourseController {

    // Autowires the ICourseService to handle the business logic.
    ICourseService iCourseService ;

    // Handles POST requests for creating a new course. It accepts form data including title, price, and an image file.
    @PostMapping("/addCourse")
    public ResponseEntity<Course> CreateCourse(
            @RequestParam("title") String title,
            @RequestParam("price") double price,
            @RequestParam("imagePath") MultipartFile imagePath
    ) {
        try {
            Course course = iCourseService.addCourse(title, price, imagePath);
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Handles PUT requests for updating an existing course by its ID. All parameters are optional.
    @PutMapping("/updatecourse/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Integer id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) MultipartFile image) {
        try {
            Course updatedCourse = iCourseService.updateCourse(id, title, price, image);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Handles DELETE requests to remove a course by its ID.
    @DeleteMapping("/deleteCourse/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        iCourseService.deleteCourse(id);
    }

    // Handles GET requests to retrieve a specific course by its ID.
    @GetMapping("/getCourse/{id}")
    public Course getCourseById(@PathVariable Integer id) {
        return iCourseService.getCourseById(id);
    }
    // Handles GET requests to retrieve all courses available.
    @GetMapping("/getAllCourses")
    public List<Course> getAllCourses(){
        return iCourseService.getAllCourses();
    }

}
