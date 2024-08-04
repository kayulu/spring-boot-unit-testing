package com.kayulu.springmvc.service;

import com.kayulu.springmvc.models.CollegeStudent;
import com.kayulu.springmvc.repository.StudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    private static final Logger logger = LoggerFactory.getLogger(StudentAndGradeService.class);

    @Autowired
    private StudentDao studentDao;

    public void createNewStudent(String firstname, String lastName, String email) {
        CollegeStudent student = new CollegeStudent(firstname, lastName, email);

        studentDao.save(student);
    }

    public boolean checkIfStudentIsPresent(int id) {
        Optional<CollegeStudent> studentOptional = studentDao.findById(id);

        return studentOptional.isPresent();
    }

    public void deleteStudentById(int id) {
        logger.info("student with id {} exists: {}", id, checkIfStudentIsPresent(id));
        studentDao.deleteById(id);
        logger.info("student with id {} exists: {}", id, checkIfStudentIsPresent(id));
    }

    public Iterable<CollegeStudent> getGradeBook() {
        return studentDao.findAll();
    }
}
