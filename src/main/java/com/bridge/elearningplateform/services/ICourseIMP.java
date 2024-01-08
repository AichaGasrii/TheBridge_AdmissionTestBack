package com.bridge.elearningplateform.services;

import com.bridge.elearningplateform.entities.Course;
import com.bridge.elearningplateform.repositories.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ICourseIMP implements ICourseService{

    /*@Value("${file.upload}")
    private String pathFile;*/

    @Autowired
    private CourseRepository courseRepository;

    // Adds a file to the server's file system. Returns true if the file is successfully uploaded.
// Cette stocke uniquement le nom du fichier
    public boolean storeFileName(MultipartFile file) {
        String fileName = null; // Déclaration en dehors du bloc try pour élargir la portée
        try {
            fileName = file.getOriginalFilename();
            // Votre logique existante ici
            log.info("Nom du fichier '{}' enregistré avec succès.", fileName);
            return true;
        } catch (Exception e) {
            log.error("Échec de l'enregistrement du nom du fichier '{}': {}", fileName, e.getMessage());
            return false;
        }
    }



    // Adds a new course with a title, price, and image file. It first uploads the image using addFile() and then saves the course details to the database.
    public Course addCourse(String title, double price, MultipartFile image) {
        boolean fileNameStored = storeFileName(image);
        if (!fileNameStored) {
            log.error("Échec de l'enregistrement du nom de l'image pour le cours '{}'.", title);
            throw new RuntimeException("Erreur lors de l'enregistrement du nom de l'image.");
        }
        String imagePath = image.getOriginalFilename(); // Juste le nom du fichier
        Course course = new Course();
        course.setTitle(title);
        course.setPrice(price);
        course.setImagePath(imagePath);
        Course savedCourse = courseRepository.save(course);
        log.info("Cours '{}' ajouté avec succès avec l'ID {}.", title, savedCourse.getId());
        return savedCourse;
    }

    @Override
    public Course updateCourse(Integer id, String title, double price, MultipartFile image) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        if (image != null && !image.isEmpty()) {
            boolean fileNameStored = storeFileName(image);
            if (!fileNameStored) {
                log.error("Échec de la mise à jour de l'image pour le cours '{}'.", title);
                throw new RuntimeException("Erreur lors de la mise à jour de l'image.");
            }
            course.setImagePath(image.getOriginalFilename());
        }

        course.setTitle(title);
        course.setPrice(price);

        Course updatedCourse = courseRepository.save(course);
        log.info("Cours avec l'ID {} mis à jour avec succès.", course.getId());
        return updatedCourse;
    }

    // Retrieves and returns a list of all courses from the database.
    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        log.info("Récupération de tous les cours avec succès. Nombre de cours trouvés: {}", courses.size());
        return courses;
    }

    // Retrieves and returns a single course by its ID from the database. Throws an exception if the course is not found.
    public Course getCourseById(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        log.info("Cours avec l'ID {} récupéré avec succès.", id);
        return course;
    }

    // Deletes a course from the database by its ID. Throws an exception if the course is not found.
    public void deleteCourse(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.deleteById(id);
        log.info("Cours avec l'ID {} supprimé avec succès.", id);
    }



}