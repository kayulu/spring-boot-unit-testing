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
		m.addAttribute(students);

		return "index";
	}

	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable int id, Model m) {
		return "studentInformation";
		}

	@PostMapping("/")
	public String createStudent(@ModelAttribute("student") CollegeStudent student, Model model) {

		studentAndGradeService.createNewStudent(student);

		Iterable<CollegeStudent> students = studentAndGradeService.getGradeBook();
		model.addAttribute("students", students);

		return "index";
	}

	@DeleteMapping("/delete/student/{id}")
	public String deleteStudent(@PathVariable int id, Model m) {
		studentAndGradeService.deleteStudentById(id);

		Iterable<CollegeStudent> students = studentAndGradeService.getGradeBook();
		m.addAttribute("students", students);

		return "index";
	}

}
