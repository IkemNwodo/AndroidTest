package com.ikem.androidtest.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ikem.androidtest.db.PatientRepository;
import com.ikem.androidtest.model.Patient;

import java.util.List;

public class HomeFragmentViewModel extends AndroidViewModel {

    private PatientRepository patientRepository;

    private LiveData<List<Patient>> patients;

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository(application);
        patients = patientRepository.getAllPatients();
    }

    public LiveData<List<Patient>> getPatients() {
        return patients;
    }

    public void setPatient(Patient patient) {
        patientRepository.insertPatient(patient);
    }

    public void deleteAllPatients(){
        patientRepository.deleteAllPatients();
    }

}
