package com.learning.instructor.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.learning.R;
import com.learning.instructor.adapter.InstructorRecyclerAdapter;
import com.learning.instructor.model.Instructor;
import com.learning.instructor.viewmodel.InstructorViewModel;

import java.util.LinkedList;
import java.util.List;

public class InstructorsActivity extends AppCompatActivity {

    private List<Instructor> instructors;
    private RecyclerView instructorRecyclerView;
    private InstructorRecyclerAdapter adapter;
    private Toolbar toolbar;
    private FloatingActionButton addInstructorFab;
    private InstructorViewModel instructorViewModel;
    public static final int NEW_INSTRUCTOR_ACTIVITY_REQUEST_CODE = 100;
    public static final int UPDATE_INSTRUCTOR_ACTIVITY_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors);
        finds();
        startObserver();
        events();
    }

    private void finds() {
        instructors = new LinkedList<>();
        instructorRecyclerView = findViewById(R.id.instructor_recyclerview);
        instructorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupToolbar("Instructores", "Android Dev Perú", true);
        addInstructorFab = findViewById(R.id.add_instructor_fab);
    }

    private void setupToolbar(String title, String subTitle, Boolean upButtom) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButtom);
    }

    private void startObserver() {
        instructorViewModel = ViewModelProviders.of(this).get(InstructorViewModel.class);
        instructorViewModel.getInstructors().observe(this, new Observer<List<Instructor>>() {
            @Override
            public void onChanged(@Nullable List<Instructor> instructors) {
                showInstructors(instructors);
            }
        });
    }

    private void showInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
        adapter = new InstructorRecyclerAdapter(instructors, R.layout.item_instructor, this);
        instructorRecyclerView.setAdapter(adapter);
    }

    private void events() {
        addInstructorFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorsActivity.this, InstructorActivity.class);
                intent.putExtra("crud", "create");
                startActivityForResult(intent, NEW_INSTRUCTOR_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Instructor instructor = (Instructor) data.getSerializableExtra("instructor");
            if (NEW_INSTRUCTOR_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
                instructorViewModel.create(instructor);
            }

            if (UPDATE_INSTRUCTOR_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
                instructorViewModel.update(instructor);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
