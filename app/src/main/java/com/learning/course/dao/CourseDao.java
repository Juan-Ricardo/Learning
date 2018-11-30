package com.learning.course.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.learning.course.model.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void create(Course course);

    @Query("delete from course")
    void deleteAll();

    @Query("SELECT * from course ORDER BY name ASC")
    List<Course> getAllCourses();

    @Query("SELECT * from course")
    LiveData<List<Course>> getCourses();

    @Update
    void update(Course user);

    @Delete
    void delete(Course user);
}
