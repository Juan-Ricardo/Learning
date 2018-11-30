package com.learning.course.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Spinner;

import com.learning.R;
import com.learning.course.adapter.CourseRecyclerAdapter;
import com.learning.course.dao.CourseDaoFake;
import com.learning.course.model.Course;
import com.learning.course.viewmodel.CourseViewModel;

import java.util.LinkedList;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {

    private List<Course> courses;
    private RecyclerView courseRecyclerView;
    private CourseRecyclerAdapter adapter;
    private Toolbar toolbar;
    private FloatingActionButton addCourseFab;
    private CourseViewModel courseViewModel;
    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 100;
    public static final int UPDATE_COURSE_ACTIVITY_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        finds();
        startObserver();
        events();
    }

    private void finds() {
        courses = new LinkedList<>();
        courseRecyclerView = findViewById(R.id.course_recyclerview);
        courseRecyclerView.setLayoutManager(new GridLayoutManager(this, calculateNoOfColumns(this)));
        setupToolbar("Cursos", "Android Dev Perú", true);
        addCourseFab = findViewById(R.id.add_course_fab);
    }

    private void setupToolbar(String title, String subTitle, Boolean upButtom) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButtom);
    }

    private void startObserver() {
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getUsers().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {
                showCourses(courses);
            }
        });
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    private void events() {
        addCourseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoursesActivity.this, CourseActivity.class);
                intent.putExtra("crud", "create");
                startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void showCourses(List<Course> courses) {
        this.courses = courses;
        adapter = new CourseRecyclerAdapter(courses, R.layout.item_course, this);
        courseRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Course course = (Course) data.getSerializableExtra("course");
            if (NEW_COURSE_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
                courseViewModel.create(course);
            }

            if (UPDATE_COURSE_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
                courseViewModel.update(course);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
