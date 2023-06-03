package ma.enset.ContactManager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;






//Add database entities
@Database(entities={Contact.class},version=1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

        // create database instance
        private static RoomDB database;

        // Define database name
        private static String DATABASE_NAME="contactsdb";

        public synchronized static RoomDB getInstance(Context context)
        {
            // check condition
            if(database==null)
            {
                // when database is null
                // Initialize database
                database= Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DATABASE_NAME)
                        .allowMainThreadQueries().fallbackToDestructiveMigration().build();
            }
            // Return database
            return database;
        }

        //Create DAO
        public abstract MinDao mainDao();
    }

