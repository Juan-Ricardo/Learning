package com.learning.course.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.R;
import com.learning.course.model.Course;
import com.learning.course.ui.CourseActivity;
import com.learning.course.ui.CoursesActivity;
import com.learning.database.LearningDatabase;
import com.learning.database.LearningDatabase_Impl;
import com.learning.instructor.model.Instructor;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

public class CourseRecyclerAdapter
        extends RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder>
        implements Filterable {

    private List<Course> courses;
    private List<Course> mCourses;
    private int resource;
    private Activity activity;

    public CourseRecyclerAdapter(List<Course> courses, int resource, Activity activity) {
        this.courses = courses;
        this.mCourses = courses;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###.00");
        final Course course = courses.get(position);
        holder.name.setText(course.getName());
        List<Instructor> instructors = LearningDatabase.getInstance(activity.getBaseContext())
                .InstructorDao().getInstructors(course.getIdInstructor());
        holder.instructor.setText("" + instructors.get(0).getName());
        holder.ratingBar.setRating(course.getRatingBar());
        holder.picture.setImageResource(course.getPicture());
        String text = "<strike><font color=\'#757575\'>" + decimalFormat.format(course.getBeforePrice()) + "</font></strike>";
        holder.beforePrice.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        holder.currentPrice.setText("USD " + decimalFormat.format(course.getCurrentPrice()));

        holder.courseLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CourseActivity.class);
                intent.putExtra("crud", "update");
                intent.putExtra("course", course);
                activity.startActivityForResult(intent, CoursesActivity.UPDATE_COURSE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    String query = constraint.toString().toLowerCase();
                    List<Course> filteredList = new LinkedList<>();
                    for (Course course : mCourses) {
                        if (course.getName().toLowerCase().contains(query) ||
                                course.getName().toLowerCase().contains(query) ||
                                String.valueOf(course.getBeforePrice()).contains(query) ||
                                String.valueOf(course.getCurrentPrice()).contains(query)) {
                            filteredList.add(course);
                        }
                    }
                    filterResults.count = filteredList.size();
                    filterResults.values = filteredList;
                } else {
                    filterResults.count = mCourses.size();
                    filterResults.values = mCourses;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                courses = (List<Course>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {
        LinearLayout courseLinearLayout;
        ImageView picture;
        TextView name;
        TextView instructor;
        RatingBar ratingBar;
        TextView beforePrice;
        TextView currentPrice;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseLinearLayout = (LinearLayout) itemView.findViewById(R.id.course_linearlayout);
            picture = (ImageView) itemView.findViewById(R.id.picture_imageview);
            name = (TextView) itemView.findViewById(R.id.name_texview);
            instructor = (TextView) itemView.findViewById(R.id.instructor_textview);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            beforePrice = (TextView) itemView.findViewById(R.id.before_price_textview);
            currentPrice = (TextView) itemView.findViewById(R.id.current_price_textview);
        }

        @Override
        public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {

        }

        @Override
        public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {
            ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
            ViewCompat.setAlpha(itemView, 0);
        }

        @Override
        public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(0)
                    .alpha(1)
                    .setDuration(300)
                    .setListener(listener)
                    .start();
        }

        @Override
        public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(-itemView.getHeight() * 0.3f)
                    .alpha(0)
                    .setDuration(300)
                    .setListener(listener)
                    .start();
        }
    }
}
