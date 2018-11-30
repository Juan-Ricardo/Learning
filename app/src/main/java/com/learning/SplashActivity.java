package com.learning;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.learning.course.adapter.CourseRecyclerAdapter;
import com.learning.course.ui.CoursesActivity;
import com.learning.instructor.ui.InstructorsActivity;

public class SplashActivity extends AppCompatActivity {

    private MaterialButton showCourseMaterialButton;
    private MaterialButton showInstructorMaterialButton;
    private TextView meetupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        finds();
        events();
    }

    private void finds() {
        meetupTextView = findViewById(R.id.meetup_textview);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Mentega.ttf");
        meetupTextView.setTypeface(typeface);
        showCourseMaterialButton = findViewById(R.id.show_course_material_button);
        showInstructorMaterialButton = findViewById(R.id.instructor_material_button);
    }

    private void events() {
        showCourseMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, CoursesActivity.class));
            }
        });

        showInstructorMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, InstructorsActivity.class));
            }
        });
    }
}
