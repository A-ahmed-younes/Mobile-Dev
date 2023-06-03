package ma.enset.IPLocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        String savedUsername = sharedPreferences.getString(PREF_USERNAME, "");
        String savedPassword = sharedPreferences.getString(PREF_PASSWORD, "");
        etUsername.setText(savedUsername);
        etPassword.setText(savedPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (isValidCredentials(username, password)) {
                    // Save the login credentials to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PREF_USERNAME, username);
                    editor.putString(PREF_PASSWORD, password);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {

        return username.equals("admin") && password.equals("admin");
    }
}