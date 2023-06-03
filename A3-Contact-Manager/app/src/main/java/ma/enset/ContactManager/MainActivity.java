package ma.enset.ContactManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btt_add_contact;

    List<Contact> dataList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB roomDB;
    MinAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btt_add_contact = findViewById(R.id.add_contact_home);

        btt_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddContact.class);
                startActivity(i);
            }
        });

        recyclerView = findViewById(R.id.recycler_view);

        roomDB = RoomDB.getInstance(this);

//        roomDB.mainDao().insert(new Contact("test","fe","fr","rre","frf"));

        System.out.println("database data test"+ roomDB.mainDao().getAll());
        dataList = roomDB.mainDao().getAll();


        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MinAdapter(MainActivity.this,dataList);

        recyclerView.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;

    }

    @Override
    public boolean onSupportNavigateUp() {
        return false;

    }
}