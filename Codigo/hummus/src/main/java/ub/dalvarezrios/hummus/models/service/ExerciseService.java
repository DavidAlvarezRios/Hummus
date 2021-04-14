package ub.dalvarezrios.hummus.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ub.dalvarezrios.hummus.models.dao.IExerciseDao;
import ub.dalvarezrios.hummus.models.entity.Exercise;

import java.util.List;

@Repository
public class ExerciseService implements IExerciseServer{

    @Autowired
    IExerciseDao exerciseDao;

    @Override
    public List<Exercise> findAll() {
        return (List<Exercise>) exerciseDao.findAll();
    }

    @Override
    public void save(Exercise exercise) {
        exerciseDao.save(exercise);
    }

    @Override
    public Exercise findOne(Long id) {
        return exerciseDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        exerciseDao.deleteById(id);
    }

    @Override
    public Exercise findByName(String name) {
        return exerciseDao.findByName(name).get(0);
    }
}
