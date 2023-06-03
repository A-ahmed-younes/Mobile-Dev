package com.example.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        TextView user_text = (TextView) findViewById( R.id.textViewUser );
        TextView password_text = (TextView) findViewById( R.id.textViewPassword );
        Button btn_back = (Button) findViewById( R.id.buttonBack );

        Bundle b = getIntent().getExtras();
        String name = b.getString("username");
        String password = b.getString( "password" );
        user_text.setText(  user_text.getText().toString() + " " + name  );
        password_text.setText(  password_text.getText().toString() + " " + password  );

        btn_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}