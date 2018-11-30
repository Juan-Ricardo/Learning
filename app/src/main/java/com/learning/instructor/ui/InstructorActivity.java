package com.learning.instructor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.learning.R;
import com.learning.course.model.Course;
import com.learning.instructor.model.Instructor;

public class InstructorActivity extends AppCompatActivity {

    private TextInputEditText nameTextInputEditText;
    private TextInputEditText countryTextInputEditText;
    private MaterialButton saveOrUpdateMaterialButton;
    private android.support.v7.widget.Toolbar toolbar;
    private Instructor currentInstructor;
    private String crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        finds();
        events();
    }

    private void finds() {
        nameTextInputEditText = findViewById(R.id.name_edittext);
        saveOrUpdateMaterialButton = findViewById(R.id.save_or_update_material_button);
        setupToolbar("Instructor", "Android Dev Perú", true);
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
        Instructor instructor = new Instructor();
        instructor.setName(nameTextInputEditText.getText().toString());

        if (crud.equalsIgnoreCase("create")) {
            //id, autogenerado.
        } else {
            instructor.setId(currentInstructor.getId());
        }

        Intent intent = new Intent();
        intent.putExtra("instructor", instructor);

        if (TextUtils.isEmpty(nameTextInputEditText.getText().toString())) {
            setResult(RESULT_CANCELED, intent);
        } else {
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            crud = extras.getString("crud");
            if (crud.equalsIgnoreCase("update")) {
                currentInstructor = (Instructor) extras.getSerializable("instructor");
                if (currentInstructor != null) {
                    nameTextInputEditText.setText(currentInstructor.getName());
                    saveOrUpdateMaterialButton.setText("Actualizar");
                }
            } else {
                saveOrUpdateMaterialButton.setText("Guardar");
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
