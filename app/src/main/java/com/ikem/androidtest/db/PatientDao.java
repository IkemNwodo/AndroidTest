package com.ikem.androidtest.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ikem.androidtest.model.Patient;

import java.util.List;

@Dao
public interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPatient(Patient patient);

    @Query("DELETE FROM patients_table")
    void deleteAllPatients();

    @Query("SELECT * from patients_table ORDER BY counter ASC")
    LiveData<List<Patient>> getAllPatients();
}
