package ub.dalvarezrios.hummus.models.service;

import ub.dalvarezrios.hummus.models.entity.Exercise;

import java.util.List;

public interface IExerciseServer {
    public List<Exercise> findAll();
    public void save(Exercise exercise);
    public Exercise findOne(Long id);
    public void delete(Long id);
    public Exercise findByName(String name);
}
