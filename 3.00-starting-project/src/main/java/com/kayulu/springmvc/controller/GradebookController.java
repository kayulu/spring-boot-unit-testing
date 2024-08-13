package com.kayulu.springmvc.controller;

import com.kayulu.springmvc.models.*;
import com.kayulu.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GradebookController {

	@Autowired
	private Gradebook gradebook;

	@Autowired
	private StudentAndGradeService studentAndGradeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getStudents(Model m) {
		Iterable<CollegeStudent> students = studentAndGradeService.getGradeBook();
		m.addAttribute("students", students);

		return "index";
	}

	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable int id, Model m) {
		GradebookCollegeStudent student = studentAndGradeService.getStudentInformation(id);

		if(student == null)
			return "error";

		StudentGrades studentGrades = student.getStudentGrades();

		List<Grade> mathGrades = studentGrades.getMathGradeResults();
		if(!mathGrades.isEmpty())
			m.addAttribute("mathAverage", studentGrades.findGradePointAverage(mathGrades));
		else
			m.addAttribute("mathAverage", "N/A");

		List<Grade> scienceGrades = studentGrades.getScienceGradeResults();
		if(!scienceGrades.isEmpty())
			m.addAttribute("scienceAverage", studentGrades.findGradePointAverage(scienceGrades));
		else
			m.addAttribute("scienceAverage", "N/A");

		List<Grade> historyGrades = studentGrades.getHistoryGradeResults();
		if(!historyGrades.isEmpty())
			m.addAttribute("historyAverage", studentGrades.findGradePointAverage(historyGrades));
		else
			m.addAttribute("historyAverage", "N/A");


		m.addAttribute("student", student);

		return "studentInformation";
	}

	@PostMapping("/")
	public String createStudent(@ModelAttribute("student") CollegeStudent student, Model model) {
		// Due to the @ModelAttribute annotation spring will create a College student based on the ur-encoded
		// form-parameters that were sent in the request-body. It will also add that object to the model
		// to be available in the 'view'.
		studentAndGradeService.createNewStudent(student);

		Iterable<CollegeStudent> students = studentAndGradeService.getGradeBook();
		model.addAttribute("students", students);

		return "index";
	}

	@GetMapping("/delete/student/{id}")
	public String deleteStudent(@PathVariable int id, Model m) {
		if(!studentAndGradeService.checkIfStudentIsPresent(id))
			return "error";

		studentAndGradeService.deleteStudentById(id);

		Iterable<CollegeStudent> students = studentAndGradeService.getGradeBook();
		m.addAttribute("students", students);

		return "index";
	}

	@PostMapping("/grades")
	public String createGrade(
			@ModelAttribute("studentId") int studentId,
			@ModelAttribute("gradeType") String gradeType,
			@ModelAttribute("grade") double grade,
			Model model) {

		if(!studentAndGradeService.createGrade(grade, studentId, gradeType))
			return "error";

		model.addAttribute("student", studentAndGradeService.getStudentInformation(studentId));

		return "studentInformation";
	}

	@GetMapping("/grades/{id}/{gradeType}")
	public String deleteGrade(
			@PathVariable("id") int gradeId,
			@PathVariable("gradeType") String gradeType,
			Model m) {
		int studentId = studentAndGradeService.deleteGrade(gradeId, gradeType);
		if(studentId == -1)
			return "error";

		m.addAttribute("student", studentAndGradeService.getStudentInformation(studentId));

		return "studentInformation";
	}
}