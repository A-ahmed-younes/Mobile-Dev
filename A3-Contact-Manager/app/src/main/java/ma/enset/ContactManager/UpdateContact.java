package ma.enset.ContactManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateContact extends AppCompatActivity {
    Button btt_update ;
    EditText nome,prenom,email,phone,job;
    Contact contact;
    RoomDB roomDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_contact);

        roomDB = RoomDB.getInstance(this);


        nome = findViewById( R.id.first_name_update );
        prenom = findViewById( R.id.last_name_update );
        job = findViewById( R.id.job_update);
        phone = findViewById( R.id.phone_update );
        email = findViewById( R.id.email_update );


        btt_update = findViewById( R.id.btt_update );

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        contact = roomDB.mainDao().getById(id);

        nome.setText( contact.getFirst_name() );
        prenom.setText( contact.getLast_name() );
        job.setText( contact.getJob() );
        phone.setText( contact.getPhone() );
        email.setText( contact.getEmail() );


        btt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String set_first_name = nome.getText().toString().trim();
                String set_last_name = prenom.getText().toString().trim();
                String set_job = job.getText().toString().trim();
                String set_phone = phone.getText().toString().trim();
                String set_email = email.getText().toString().trim();


                Contact contact = new Contact();

                contact.setId(id);
                contact.setFirst_name(set_first_name);
                contact.setLast_name( set_last_name );
                contact.setJob( set_job );
                contact.setPhone( set_phone );
                contact.setEmail( set_email );

                roomDB.mainDao().update(contact);

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

}
