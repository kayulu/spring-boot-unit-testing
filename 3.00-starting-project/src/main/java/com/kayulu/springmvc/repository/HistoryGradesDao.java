package com.kayulu.springmvc.repository;

import com.kayulu.springmvc.models.HistoryGrade;
import com.kayulu.springmvc.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryGradesDao extends CrudRepository<HistoryGrade, Integer> {
    Iterable<HistoryGrade> findGradesByStudentId(int id);
    void deleteByStudentId(int id);
}
