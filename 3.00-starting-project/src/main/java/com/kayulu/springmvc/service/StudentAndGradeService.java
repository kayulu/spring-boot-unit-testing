package com.kayulu.springmvc.service;

import com.kayulu.springmvc.models.CollegeStudent;
import com.kayulu.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
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
}
