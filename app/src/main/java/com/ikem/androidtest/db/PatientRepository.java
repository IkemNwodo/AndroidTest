package com.ikem.androidtest.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ikem.androidtest.model.Patient;

import java.util.List;

public class PatientRepository {

    private PatientDao patientDao;
    private LiveData<List<Patient>> allPatients;

    public PatientRepository(Application application) {
        PatientDatabase database = PatientDatabase.getDatabase(application);
        patientDao = database.patientDao();
        allPatients = patientDao.getAllPatients();
    }

    public LiveData<List<Patient>> getAllPatients() {
        return allPatients;
    }

    public void insertPatient(Patient patient) {
        PatientDatabase.databaseWriteExecutor.execute(() -> patientDao.insertPatient(patient));
    }

    public void deleteAllPatients(){
        PatientDatabase.databaseWriteExecutor.execute(() -> patientDao.deleteAllPatients());
    }
}
