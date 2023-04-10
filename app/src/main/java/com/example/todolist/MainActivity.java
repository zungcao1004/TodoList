package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private ArrayList<TodoItem> todoList;
    private TodoAdapter todoAdapter;
    private TodoDatabaseHelper todoDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create new TodoDatabaseHelper to create and access the database
        todoDatabaseHelper = new TodoDatabaseHelper(this);

        // Load todo items from database
        todoList = new ArrayList<>();
        SQLiteDatabase db = todoDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TodoDatabaseHelper.TABLE_TODO,
                new String[]{TodoDatabaseHelper.COLUMN_ID, TodoDatabaseHelper.COLUMN_TITLE, TodoDatabaseHelper.COLUMN_COMPLETED},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(TodoDatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(TodoDatabaseHelper.COLUMN_TITLE));
            @SuppressLint("Range") boolean completed = cursor.getInt(cursor.getColumnIndex(TodoDatabaseHelper.COLUMN_COMPLETED)) != 0;

            TodoItem item = new TodoItem(title);
            item.setId(id);
            item.setCompleted(completed);

            todoList.add(item);
        }

        cursor.close();

        // Set up list view and adapter
        ListView listView = findViewById(R.id.list_view);
        todoAdapter = new TodoAdapter(this, todoList);
        listView.setAdapter(todoAdapter);

        // Set up input text field
        inputText = findViewById(R.id.input_text);

        // Set up add button
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> addTodo());
    }

    private void addTodo() {
        String todoText = inputText.getText().toString();

        if (todoText.isEmpty()) {
            return;
        }

        // Insert new to-do item into database
        SQLiteDatabase db = todoDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TodoDatabaseHelper.COLUMN_TITLE, todoText);
        db.insert(TodoDatabaseHelper.TABLE_TODO, null, values);

        // Add new to-do item to list view
        TodoItem newItem = new TodoItem(todoText);
        todoList.add(newItem);
        todoAdapter.notifyDataSetChanged();
        inputText.setText("");
    }



}
