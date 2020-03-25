package com.example.notesapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText note;
    String noteContent;
    String originalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        note = findViewById(R.id.noteText);

        originalText = getIntent().getStringExtra("content");
        if (originalText != null) {
            note.setText(originalText);

        }
    }

    @Override
    public void onBackPressed() {
        noteContent = note.getText().toString();
        if (!noteContent.isEmpty()) {
            MainActivity.noteText = noteContent;
        }
        super.onBackPressed();

    }
}
