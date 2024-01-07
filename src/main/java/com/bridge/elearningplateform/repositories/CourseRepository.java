package com.bridge.elearningplateform.repositories;

import com.bridge.elearningplateform.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {


}
