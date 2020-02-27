package c.sakshi.lab5;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NotesActivity extends AppCompatActivity {

    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        Intent intent = getIntent();
        String username = intent.getStringExtra("message");
        welcomeTextView.setText("Welcome " + username + "!");
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
                // TODO what action to take when user selects to Logout
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
                Toast.makeText(this, "Add Note Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void logoutSelect() {
        goToMainActivity();
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
