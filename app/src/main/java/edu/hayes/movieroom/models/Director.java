package edu.hayes.movieroom.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author James Hayes
 */
@Entity(tableName = "director_table",
        indices = {@Index(value = "last_name", unique = true)})
public class Director {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dir_id")
    private int id;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    @NonNull
    private String lastName;

    @ColumnInfo(name = "full_name")
    @Ignore
    private String fullName;


    public int getId() {return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @NonNull
    public String getLastName() { return lastName; }
    public void setLastName(@NonNull String lastName) { this.lastName = lastName; }

    public String getFullName() {return fullName; }
    public void setFullName() { this.fullName = firstName + " " + lastName; }
}
