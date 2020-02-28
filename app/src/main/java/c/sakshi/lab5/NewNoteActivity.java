package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        // 1. Get EditText View
        // 2. Get Intent
        // 3. Get the value of integer "noteid" from intent
        // 4. Initialize class variable "noteid" with the value from intent

        // 1. Get EditText View
        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);

        // 2. Get Intent
        Intent intent = getIntent();

        // 3. Get the value of integer "noteid" from intent
        int noteidValue = intent.getIntExtra("noteid",-1);

        // 4. Initialize class variable "noteid" with the value from intent
        noteid = noteidValue;

        if(noteid != -1) {
            // Display content of note by retrieving "notes" ArrayList in NotesActivity
            Note note = NotesActivity.notes.get(noteid);
            String noteContent = note.getContent();
            // use editText.setText() to display contents to the screen
            noteEditText.setText(noteContent);

        }
    }

    public void clickSaveButton(View view) {
        // 1. Get editText view and the content that the user entered
        EditText noteEditText = findViewById(R.id.noteEditText);
        String content = noteEditText.getText().toString();

        // 2. Init SQLiteDatabase Instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        // 3. Init DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 4. Set username in the following variable by fetching it from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        // 5. Save information to DB
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1) { // Add note
            title = "NOTE_" + (NotesActivity.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else { // Update note
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content);
        }

        // 6. Go to second activity using intents
        goToNotesActivity();
    }

    public void goToNotesActivity() {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }
}
