package com.learning.course.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.learning.course.model.Course;
import com.learning.database.Repository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Course>> courses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        courses = repository.getCourses();
    }

    public LiveData<List<Course>> getUsers() {
        return courses;
    }

    public void create(Course course) {
        repository.create(course);
    }

    public void update(Course course) {
        repository.update(course);
    }
}
