package ub.dalvarezrios.hummus.models.dao;

import org.springframework.data.repository.CrudRepository;
import ub.dalvarezrios.hummus.models.entity.Exercise;

import java.util.List;

public interface IExerciseDao extends CrudRepository<Exercise, Long> {

    List<Exercise> findByName(String name);
}
