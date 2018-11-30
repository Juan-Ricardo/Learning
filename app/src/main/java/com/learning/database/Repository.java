package com.learning.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.learning.course.dao.CourseDao;
import com.learning.course.model.Course;
import com.learning.instructor.dao.InstructorDao;
import com.learning.instructor.model.Instructor;

import java.util.List;

public class Repository {
    private CourseDao courseDao;
    private InstructorDao instructorDao;
    private LiveData<List<Course>> courses;
    private LiveData<List<Instructor>> instructors;

    public Repository(Application application) {
        LearningDatabase learningDatabase = LearningDatabase.getInstance(application);
        courseDao = learningDatabase.CourseDao();
        instructorDao = learningDatabase.InstructorDao();
        courses = courseDao.getCourses();
        instructors = instructorDao.getInstructors();
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public LiveData<List<Instructor>> getInstructors() {
        return instructors;
    }

    public void create(Course course) {
        new CreateAsyncTask(courseDao).execute(course);
    }

    public void update(Course course) {
        new UpdateAsyncTask(courseDao).execute(course);
    }

    public void createInstructor(Instructor instructor) {
        new CreateInstructorAsyncTask(instructorDao).execute(instructor);
    }

    public void updateInstructor(Instructor instructor) {
        new UpdateInstructorAsyncTask(instructorDao).execute(instructor);
    }

    private static class CreateAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao courseDao;

        CreateAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... params) {
            courseDao.create(params[0]);
            return null;
        }
    }

    private static class CreateInstructorAsyncTask extends AsyncTask<Instructor, Void, Void> {

        private InstructorDao instructorDao;

        CreateInstructorAsyncTask(InstructorDao instructorDao) {
            this.instructorDao = instructorDao;
        }

        @Override
        protected Void doInBackground(Instructor... params) {
            instructorDao.create(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao courseDao;

        UpdateAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... params) {
            courseDao.update(params[0]);
            return null;
        }
    }

    private static class UpdateInstructorAsyncTask extends AsyncTask<Instructor, Void, Void> {

        private InstructorDao instructorDao;

        UpdateInstructorAsyncTask(InstructorDao instructorDao) {
            this.instructorDao = instructorDao;
        }

        @Override
        protected Void doInBackground(Instructor... params) {
            instructorDao.update(params[0]);
            return null;
        }
    }
}
