package ub.dalvarezrios.hummus.models.service;

import ub.dalvarezrios.hummus.models.entity.Exercise;
import ub.dalvarezrios.hummus.models.entity.User;

import java.util.List;

public interface IExerciseService {
    public List<Exercise> findAll();
    public void save(Exercise exercise);
    public Exercise findOne(Long id);
    public void delete(Long id);
    public Exercise findByName(String name);
    public Exercise findAllByUser(User user);
}
