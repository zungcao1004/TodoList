package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todolist.db";
    public static final String TABLE_TODO = "todo";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COMPLETED = "completed";

    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT, "
            + COLUMN_COMPLETED + " INTEGER DEFAULT 0)";

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        // Create tables again
        onCreate(db);
    }

    public void addTodoItem(TodoItem todoItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, todoItem.getTitle());
        values.put(COLUMN_COMPLETED, todoItem.isCompleted() ? 1 : 0);

        db.insert(TABLE_TODO, null, values);
        db.close();
    }

    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                TodoItem todoItem = new TodoItem();
                todoItem.setId(Integer.parseInt(cursor.getString(0)));
                todoItem.setTitle(cursor.getString(1));
                todoItem.setCompleted(cursor.getInt(2) == 1);

                todoItems.add(todoItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return todoItems;
    }

    public void updateTodoItem(TodoItem todoItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, todoItem.getId());
        values.put(COLUMN_TITLE, todoItem.getTitle());
        values.put(COLUMN_COMPLETED, todoItem.isCompleted() ? 1 : 0);

        db.update(TABLE_TODO, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(todoItem.getId()) });

        db.close();
    }

    public void deleteTodoItem(TodoItem todoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_ID + " = ?",
                new String[]{String.valueOf(todoItem.getId())});

        db.close();
    }


}
