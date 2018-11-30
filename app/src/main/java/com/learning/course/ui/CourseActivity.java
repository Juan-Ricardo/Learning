package com.learning.course.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.learning.R;
import com.learning.course.model.Course;
import com.learning.database.Repository;
import com.learning.instructor.model.Instructor;
import com.learning.instructor.viewmodel.InstructorViewModel;

import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private String idInstructor;
    private Spinner instructorSpinner;
    private SpinnerAdapter adapter;
    private TextInputEditText nameTextInputEditText;
    private TextInputEditText beforePriceTextInputEditText;
    private TextInputEditText currentPriceTextInputEditText;
    private MaterialButton saveOrUpdateMaterialButton;
    private android.support.v7.widget.Toolbar toolbar;
    private Course currentCourse;
    private InstructorViewModel instructorViewModel;
    private String crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        finds();
        events();
    }

    private void finds() {
        instructorSpinner = findViewById(R.id.instructor_spinner);
        nameTextInputEditText = findViewById(R.id.name_edittext);
        beforePriceTextInputEditText = findViewById(R.id.before_price_edittext);
        currentPriceTextInputEditText = findViewById(R.id.current_price_edittext);
        saveOrUpdateMaterialButton = findViewById(R.id.save_or_update_material_button);
        setupToolbar("Curso", "Android Dev Perú", true);
    }

    private void setupToolbar(String title, String subTitle, Boolean upButtom) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButtom);
    }

    private void events() {
        saveOrUpdateMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrUpdate();
            }
        });
    }

    private void saveOrUpdate() {
        Course course = new Course();
        course.setIdInstructor(Integer.parseInt(idInstructor));
        course.setPicture(getPicture());
        course.setRatingBar(getAleatorioRatingBar());
        course.setName(nameTextInputEditText.getText().toString());
        course.setBeforePrice(Double.parseDouble(beforePriceTextInputEditText.getText().toString()));
        course.setCurrentPrice(Double.parseDouble(currentPriceTextInputEditText.getText().toString()));

        if (crud.equalsIgnoreCase("create")) {
            //id, autogenerado.
        } else {
            course.setId(currentCourse.getId());
            course.setPicture(currentCourse.getPicture());
            course.setIdInstructor(Integer.parseInt(idInstructor));
        }

        Intent intent = new Intent();
        intent.putExtra("course", course);

        if (TextUtils.isEmpty(nameTextInputEditText.getText().toString()) &&
                TextUtils.isEmpty(currentPriceTextInputEditText.getText().toString())) {
            setResult(RESULT_CANCELED, intent);
        } else {
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    public static int getAleatorioPicture() {
        int Max = (10) + 1;
        int Min = 1;
        return ((int) (Math.random() * (Max - Min)) + Min);
    }

    public static int getAleatorioRatingBar() {
        int Max = (5) + 1;
        int Min = 1;
        return ((int) (Math.random() * (Max - Min)) + Min);
    }

    public static int getPicture() {
        int resource = getAleatorioPicture();
        switch (resource) {
            case 1:
                resource = R.drawable.ic_course_1;
                break;
            case 2:
                resource = R.drawable.ic_course_2;
                break;
            case 3:
                resource = R.drawable.ic_course_3;
                break;
            case 4:
                resource = R.drawable.ic_course_4;
                break;
            case 5:
                resource = R.drawable.ic_course_5;
                break;
            case 6:
                resource = R.drawable.ic_course_6;
                break;
            case 7:
                resource = R.drawable.ic_course_7;
                break;
            case 8:
                resource = R.drawable.ic_course_8;
                break;
            case 9:
                resource = R.drawable.ic_course_9;
                break;
            case 10:
                resource = R.drawable.ic_course_10;
                break;
        }
        return resource;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            crud = extras.getString("crud");
            if (crud.equalsIgnoreCase("update")) {
                currentCourse = (Course) extras.getSerializable("course");
                if (currentCourse != null) {
                    nameTextInputEditText.setText(currentCourse.getName());
                    beforePriceTextInputEditText.setText("" + currentCourse.getBeforePrice());
                    currentPriceTextInputEditText.setText("" + currentCourse.getCurrentPrice());
                    saveOrUpdateMaterialButton.setText("Actualizar");
                }
            } else {
                saveOrUpdateMaterialButton.setText("Guardar");
            }
        }
        loadInstructors();
    }

    private void loadInstructors() {
        instructorViewModel = ViewModelProviders.of(this).get(InstructorViewModel.class);
        instructorViewModel.getInstructors().observe(this, new Observer<List<Instructor>>() {
            @Override
            public void onChanged(@Nullable final List<Instructor> instructors) {
                adapter = new com.learning.course.adapter.SpinnerAdapter(
                        getBaseContext(), android.R.layout.simple_spinner_item, instructors);
                instructorSpinner.setAdapter(adapter);
                if (instructors.size() > 0)
                    idInstructor = String.valueOf(instructors.get(0).getId());
                instructorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Instructor instructor = instructors.get(position);
                        idInstructor = String.valueOf(instructor.getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
