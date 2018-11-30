package com.learning.instructor.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.learning.instructor.model.Instructor;

import java.util.List;

@Dao
public interface InstructorDao {
    @Insert
    void create(Instructor instructor);

    @Query("delete from instructor")
    void deleteAll();

    @Query("SELECT * from instructor ORDER BY name ASC")
    List<Instructor> getAllInstructors();

    @Query("SELECT * from instructor")
    LiveData<List<Instructor>> getInstructors();

    @Query("SELECT * from instructor where id=:id")
    List<Instructor> getInstructors(int id);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);
}
