package ma.enset.ContactManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddContact extends AppCompatActivity {
     private EditText nome,prenom,email,phone,job;
    RoomDB roomDB;
    private Button btt_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        roomDB = RoomDB.getInstance(this);

        btt_add =findViewById(R.id.btt_add);
        nome = findViewById(R.id.first_name_add);
        prenom = findViewById(R.id.last_name_add);
        job = findViewById(R.id.job_add);
        phone = findViewById(R.id.phone_add);
        email = findViewById(R.id.email_add);

        btt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get string from edit text
                String set_first_name = nome.getText().toString().trim();
                String set_last_name = prenom.getText().toString().trim();
                String set_job = job.getText().toString().trim();
                String set_phone = phone.getText().toString().trim();
                String set_email = email.getText().toString().trim();

                Contact contact = new Contact();

                contact.setFirst_name(set_first_name);
                contact.setLast_name( set_last_name );
                contact.setJob( set_job );
                contact.setPhone( set_phone );
                contact.setEmail( set_email );
                System.out.println(contact.toString());
                roomDB.mainDao().insert(contact);

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);


            }
        });



    }
}
