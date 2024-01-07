package com.bridge.elearningplateform.services;

import com.bridge.elearningplateform.entities.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ICourseService {

    public boolean storeFileName(MultipartFile file) ;
    public Course addCourse(String title, double price, MultipartFile image) ;
    public Course updateCourse(Integer id, String title, double price, MultipartFile image);
    List<Course> getAllCourses();
    public Course getCourseById(Integer id);
    public void deleteCourse(Integer id);



}