package com.learning.instructor.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.learning.database.Repository;
import com.learning.instructor.model.Instructor;

import java.util.List;

public class InstructorViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Instructor>> instructors;

    public InstructorViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        instructors = repository.getInstructors();
    }

    public LiveData<List<Instructor>> getInstructors() {
        return instructors;
    }

    public void create(Instructor instructor) {
        repository.createInstructor(instructor);
    }

    public void update(Instructor instructor) {
        repository.updateInstructor(instructor);
    }
}
