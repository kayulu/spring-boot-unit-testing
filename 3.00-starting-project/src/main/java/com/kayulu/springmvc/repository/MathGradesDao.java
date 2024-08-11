package com.kayulu.springmvc.repository;

import com.kayulu.springmvc.models.MathGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathGradesDao extends CrudRepository<MathGrade, Integer> {
    Iterable<MathGrade> findGradesByStudentId(int id);

    void deleteByStudentId(int id);
}
