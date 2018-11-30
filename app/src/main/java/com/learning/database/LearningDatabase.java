package com.learning.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.learning.course.dao.CourseDao;
import com.learning.course.model.Course;
import com.learning.instructor.dao.InstructorDao;
import com.learning.instructor.model.Instructor;

@Database(entities = {Course.class, Instructor.class}, version = 1, exportSchema = false)
public abstract class LearningDatabase extends RoomDatabase {

    public abstract CourseDao CourseDao();
    public abstract InstructorDao InstructorDao();

    private static volatile LearningDatabase INSTANCE;

    public static LearningDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (LearningDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LearningDatabase.class, Constans.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
