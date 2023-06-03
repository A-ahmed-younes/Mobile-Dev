package ma.enset.ContactManager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName ="contact")
public class Contact implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "first_name")
    private String first_name;
    @ColumnInfo(name = "last_name")
    private String last_name;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "job")
    private String job;
    @ColumnInfo(name = "phone")
    private String phone;

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", job='" + job + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public Contact(String first_name, String last_name, String email, String job, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.job = job;
        this.phone = phone;
    }

    @Ignore
    public Contact() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
