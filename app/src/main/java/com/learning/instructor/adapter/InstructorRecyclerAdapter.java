package com.learning.instructor.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learning.R;
import com.learning.instructor.model.Instructor;
import com.learning.instructor.ui.InstructorActivity;
import com.learning.instructor.ui.InstructorsActivity;

import java.util.LinkedList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;


public class InstructorRecyclerAdapter
        extends RecyclerView.Adapter<InstructorRecyclerAdapter.InstructorViewHolder>
        implements Filterable {
    private List<Instructor> instructors;
    private List<Instructor> mInstructors;
    private int resource;
    private Activity activity;

    public InstructorRecyclerAdapter(List<Instructor> instructors, int resource, Activity activity) {
        this.instructors = instructors;
        this.mInstructors = instructors;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new InstructorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorViewHolder holder, int position) {
        final Instructor instructor = instructors.get(position);
        holder.name.setText(instructor.getName());
        //holder.country.setText(instructor.getCountry());
        holder.instructorRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, InstructorActivity.class);
                intent.putExtra("crud", "update");
                intent.putExtra("instructor", instructor);
                activity.startActivityForResult(intent, InstructorsActivity.UPDATE_INSTRUCTOR_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    String query = constraint.toString().toLowerCase();
                    List<Instructor> filteredList = new LinkedList<>();
                    for (Instructor course : mInstructors) {
                        if (course.getName().toLowerCase().contains(query) ||
                                course.getCountry().toLowerCase().contains(query)) {
                            filteredList.add(course);
                        }
                    }
                    filterResults.count = filteredList.size();
                    filterResults.values = filteredList;
                } else {
                    filterResults.count = mInstructors.size();
                    filterResults.values = mInstructors;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                instructors = (List<Instructor>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class InstructorViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {
        RelativeLayout instructorRelativeLayout;
        ImageView avatar;
        TextView name;
        TextView country;

        public InstructorViewHolder(View itemView) {
            super(itemView);
            instructorRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.instructor_relativelayout);
            avatar = (ImageView) itemView.findViewById(R.id.avatar_imageview);
            name = (TextView) itemView.findViewById(R.id.name_texview);
            country = (TextView) itemView.findViewById(R.id.country_texview);
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
