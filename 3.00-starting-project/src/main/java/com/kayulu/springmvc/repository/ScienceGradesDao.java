package com.kayulu.springmvc.repository;

import com.kayulu.springmvc.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScienceGradesDao extends CrudRepository<ScienceGrade, Integer> {
    Iterable<ScienceGrade> findGradesByStudentId(int id);
}
