package c.sakshi.lab5;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;



// TODO left off at "Building Third screen" in "Lab5.pdf"

public class NotesActivity extends AppCompatActivity {

    public static ArrayList<Note> notes = new ArrayList<>();

    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        Intent intent = getIntent();
        String username = sharedPreferences.getString("username","");
        welcomeTextView.setText("Welcome " + username + "!");

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        notes = dbHelper.readNotes(username);

        ArrayList<String> displayNotes = new ArrayList<>();
        for(Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(),note.getDate()));
        }

        // 5. Use ListView view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.noteListView);
        listView.setAdapter(adapter);

        // 6. Add onItemsClickListener for ListView item, a note in our case
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Initialize intent to take user to third activity (NewNoteActivity)
                Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                // Add the position of the item that was clicked on as "noteid".
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            // User selects to "Logout"
            case R.id.logoutItem:
                SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                // remove username from sharedPreferences
                sharedPreferences.edit().remove("username").apply();
                // logout (go to main activity)
                logoutSelect();
                //Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show();
                return true;
            // User selects to "Add Note"
            case R.id.addNoteItem:
                // TODO what action to take when user selects to Add Note
                addNoteSelect();
                //Toast.makeText(this, "Add Note Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void logoutSelect() {
        goToMainActivity();
    }

    public void addNoteSelect() {
        gotToNewNoteActivity();
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void gotToNewNoteActivity() {
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivity(intent);
    }

}
