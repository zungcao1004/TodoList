package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<TodoItem> todoList;

    public TodoAdapter(Context context, ArrayList<TodoItem> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false);
        }

        // Get references to views in the list item layout
        TextView titleView = convertView.findViewById(R.id.title_view);
        CheckBox completedCheck = convertView.findViewById(R.id.completed_check);

        // Get the current to-do item
        TodoItem currentItem = (TodoItem) getItem(position);

        // Set the title
        String title = currentItem.getTitle();
        titleView.setText(title);

        // Set the completion status
        boolean isCompleted = currentItem.isCompleted();
        completedCheck.setChecked(isCompleted);

        // Set the checkbox listener to update the completion status
        completedCheck.setOnCheckedChangeListener((buttonView, isChecked) -> currentItem.setCompleted(isChecked));

        return convertView;
    }
}
