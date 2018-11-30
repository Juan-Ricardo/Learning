package com.learning.course.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.learning.R;
import com.learning.instructor.model.Instructor;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Instructor> {
    private List<Instructor> instructors;
    private Context context;
    public SpinnerAdapter(@NonNull Context context, int resource, List<Instructor> instructors) {
        super(context, resource,instructors);
        this.context=context;
        this.instructors=instructors;
    }

    @Override
    public int getCount() {
        return instructors.size();
    }

    @Nullable
    @Override
    public Instructor getItem(int position) {
        return instructors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(context.getResources().getColor(R.color.primary_text));
        label.setText(instructors.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(context.getResources().getColor(R.color.primary_text));
        label.setText(instructors.get(position).getName());
        return label;
    }
}
