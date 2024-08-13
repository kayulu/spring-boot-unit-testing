package com.kayulu.springmvc.service;

import com.kayulu.springmvc.models.*;
import com.kayulu.springmvc.repository.HistoryGradesDao;
import com.kayulu.springmvc.repository.MathGradesDao;
import com.kayulu.springmvc.repository.ScienceGradesDao;
import com.kayulu.springmvc.repository.StudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    private static final Logger logger = LoggerFactory.getLogger(StudentAndGradeService.class);

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    public void createNewStudent(String firstname, String lastName, String email) {
        CollegeStudent student = new CollegeStudent(firstname, lastName, email);

        studentDao.save(student);
    }

    public void createNewStudent(CollegeStudent student) {
        studentDao.save(student);
    }

    public boolean checkIfStudentIsPresent(int id) {
        Optional<CollegeStudent> studentOptional = studentDao.findById(id);

        return studentOptional.isPresent();
    }

    public void deleteStudentById(int id) {
        if(studentDao.findById(id).isPresent()) {
            studentDao.deleteById(id);
            mathGradesDao.deleteByStudentId(id);
            scienceGradesDao.deleteByStudentId(id);
            historyGradesDao.deleteByStudentId(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {
        return studentDao.findAll();
    }

    public boolean createGrade(double grade, int studentId, String gradeType) {
        if(studentDao.findById(studentId).isEmpty() || grade < 0 || grade > 100.0
                || !(gradeType.equalsIgnoreCase("math")
                || gradeType.equalsIgnoreCase("science")
                || gradeType.equalsIgnoreCase("history")))
            return false;

        try {
            switch (gradeType.toLowerCase()) {
                case "math" -> {
                    MathGrade mathGrade = new MathGrade(grade);
                    mathGrade.setStudentId(studentId);
                    mathGradesDao.save(mathGrade);
                }
                case "science" -> {
                    ScienceGrade scienceGrade = new ScienceGrade(grade);
                    scienceGrade.setStudentId(studentId);
                    scienceGradesDao.save(scienceGrade);
                }
                case "history" -> {
                    HistoryGrade historyGrade = new HistoryGrade(grade);
                    historyGrade.setStudentId(studentId);
                    historyGradesDao.save(historyGrade);
                }
                default -> throw new IllegalArgumentException("Grade type not found");
            }
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public int deleteGrade(int gradeId, String gradeType) {
        switch (gradeType.toLowerCase()) {
            case "math" -> {
                Optional<MathGrade> grade = mathGradesDao.findById(gradeId);
                if(grade.isPresent())
                    mathGradesDao.deleteById(gradeId);

                return grade.map(MathGrade::getStudentId).orElse(-1);
            }
            case "science" -> {
                Optional<ScienceGrade> grade = scienceGradesDao.findById(gradeId);
                if(grade.isPresent())
                    scienceGradesDao.deleteById(gradeId);

                return grade.map(ScienceGrade::getStudentId).orElse(-1);
            }
            case "history" -> {
                Optional<HistoryGrade> grade = historyGradesDao.findById(gradeId);
                if(grade.isPresent())
                    historyGradesDao.deleteById(gradeId);

                return grade.map(HistoryGrade::getStudentId).orElse(-1);
            }
            default -> {return -1;}
        }
    }

    public GradebookCollegeStudent getStudentInformation(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);

        if(student.isEmpty())
            return null;

        Iterable<MathGrade> mathGrades = mathGradesDao.findGradesByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradesByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradesByStudentId(1);

        List<Grade> mathGradeList = new ArrayList<>();
        mathGrades.forEach(mathGradeList::add);

        List<Grade> scienceGradeList = new ArrayList<>();
        scienceGrades.forEach(scienceGradeList::add);

        List<Grade> historyGradeList = new ArrayList<>();
        historyGrades.forEach(historyGradeList::add);

        StudentGrades studentGrades = new StudentGrades();
        studentGrades.setMathGradeResults(mathGradeList);
        studentGrades.setScienceGradeResults(scienceGradeList);
        studentGrades.setHistoryGradeResults(historyGradeList);

        return new GradebookCollegeStudent(student.get().getId(),
                student.get().getFirstname(), student.get().getLastname(),
                student.get().getEmailAddress(), studentGrades);
    }
}
