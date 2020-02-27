package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        // check if we already have a username
        if (!sharedPreferences.getString("username","").equals("")) {
            String username = sharedPreferences.getString("username","");
            // go to notes activity
            goToNotesActivity(username);
        }
        // else go to main activity
        else {
            setContentView(R.layout.activity_main);
        }
    }

    public void clickButton(View view) {
        // Make sharedPreferences reference
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        // Make edit text reference to username
        EditText usernameTextField = (EditText) findViewById(R.id.usernameEditText);

        // Make edit text reference to password
        EditText passwordTextField = (EditText) findViewById(R.id.passwordEditText);

        // get username string
        String username = usernameTextField.getText().toString();

        // get password string
        String password = passwordTextField.getText().toString(); // NOTE - unused

        // Store username in sharedPreferences
        sharedPreferences.edit().putString("username", username).apply();
        goToNotesActivity(username);
    }

    public void goToNotesActivity(String username) {
        Intent intent = new Intent(this, NotesActivity.class);
        intent.putExtra("message",username);
        startActivity(intent);
    }
}
