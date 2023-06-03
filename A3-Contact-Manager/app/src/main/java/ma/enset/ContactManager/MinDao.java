package ma.enset.ContactManager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MinDao {
    // Insert query
    @Insert
    void insert(Contact contact);

    // Delete query
    @Delete
    void reset(List<Contact> contacts);
    //to do

    // Update query
    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("Select * from contact where id = :id")
    Contact getById(int id);
    // Get all data query
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

}
