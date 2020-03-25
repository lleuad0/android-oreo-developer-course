package com.example.notesapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView emptyTextView;
    ListView notesListView;
    SharedPreferences sharedPreferences;
    ArrayList<String> notesArray;
    ArrayAdapter arrayAdapter;
    static String noteText;
    static int noteID;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        emptyTextView = findViewById(R.id.emptyTextView);
        notesListView = findViewById(R.id.notesListView);

        notesArray = getNotesArray();
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, notesArray);
        notesListView.setAdapter(arrayAdapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String text = (String) textView.getText();

                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("content", text);
                noteID = position;
                startActivity(intent);
            }
        });

        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setMessage("Delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeNote(position);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

        noteText = null;
        noteID = -1;
    }

    @Override
    protected void onResume() {
        if (noteText != null) {
            if (noteID != -1) {
                notesArray.remove(noteID);
                addNote(noteID, noteText);
            } else {
                addNote(noteText);
            }
        }
        noteText = null;
        noteID = -1;
        if (notesArray.isEmpty()) {
            showEmptyMessage();
        } else {
            showNotesList();
        }
        super.onResume();
    }

    public void showEmptyMessage() {
        emptyTextView.setVisibility(View.VISIBLE);
        notesListView.setVisibility(View.INVISIBLE);
    }

    public void showNotesList() {
        emptyTextView.setVisibility(View.INVISIBLE);
        notesListView.setVisibility(View.VISIBLE);
    }

    public void addNote(int id, String content) {
        notesArray.add(id, content);
        arrayAdapter.notifyDataSetChanged();
        try {
            String serializedNotes = ObjectSerializer.serialize(notesArray);
            sharedPreferences.edit().putString("serializedNotes", serializedNotes).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNote(String content) {
        notesArray.add(content);
        arrayAdapter.notifyDataSetChanged();
        try {
            String serializedNotes = ObjectSerializer.serialize(notesArray);
            sharedPreferences.edit().putString("serializedNotes", serializedNotes).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeNote(int id) {
        notesArray.remove(id);
        arrayAdapter.notifyDataSetChanged();
        try {
            String serializedNotes = ObjectSerializer.serialize(notesArray);
            sharedPreferences.edit().putString("serializedNotes", serializedNotes).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (notesArray.isEmpty()) {
            showEmptyMessage();
        }
    }

    public ArrayList<String> getNotesArray() {
        ArrayList<String> notesArray = new ArrayList<>();
        String serializedNotes = sharedPreferences.getString("serializedNotes", null);
        if (serializedNotes != null) {
            try {
                notesArray = (ArrayList<String>) ObjectSerializer.deserialize(serializedNotes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            notesArray = new ArrayList<>();
        }

        return notesArray;
    }

}
