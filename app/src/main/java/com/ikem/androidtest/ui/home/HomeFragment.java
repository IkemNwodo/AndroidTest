package com.ikem.androidtest.ui.home;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ikem.androidtest.R;
import com.ikem.androidtest.adapter.PatientsAdapter;
import com.ikem.androidtest.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class HomeFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    SharedPreferences sharedPrefs;
    String patientCount;
    static int counter;
    int maxPatients;
    String patientGender;
    TextInputEditText fullname, age, email;
    RecyclerView patientList;
    RadioGroup genderGroup;
    PatientsAdapter adapter;

    private HomeFragmentViewModel viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);

        sharedPrefs = Objects.requireNonNull(getContext()).getSharedPreferences("PatientsCountData", 0);

        counter = (sharedPrefs != null) ? sharedPrefs.getInt("counter", 0) : 0;

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        fullname = root.findViewById(R.id.full_name_input);
        age = root.findViewById(R.id.age_input);
        email = root.findViewById(R.id.email_input);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(v -> showList());

        // Max patients from settings
        assert getArguments() != null;
        maxPatients = HomeFragmentArgs.fromBundle(getArguments()).getMaxPatients();

        genderGroup = root.findViewById(R.id.gender_group);
        genderGroup.setOnCheckedChangeListener(this);

        patientList = root.findViewById(R.id.patient_list);
        patientList.setHasFixedSize(false);
        patientList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PatientsAdapter();

        viewModel.getPatients().observe(getViewLifecycleOwner(), patients -> adapter.submitList(patients));

        patientList.setAdapter(adapter);
        return root;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sharedPrefs
                .edit()
                .putInt("counter", counter)
                .apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            counter = 0;
            viewModel.deleteAllPatients();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showList() {
            if (counter < maxPatients) {
                saveData();
            }else {
                Toast.makeText(getContext(), "Maximum number of patients reached!", Toast.LENGTH_LONG).show();
            }
    }

    public void saveData() {
        String patientName = fullname.getText().toString();
        String patientAge = age.getText().toString();
        String patientEmail = email.getText().toString();
            if (patientName != null && patientAge != null && patientEmail != null) {
                counter++;

                //patientCount = String.valueOf(counter);
                Patient patient = new Patient(patientName, patientEmail, patientAge, patientGender, Integer.toString(counter));
                viewModel.setPatient(patient);
                fullname.setText("");
                age.setText("");
                email.setText("");

                genderGroup.clearCheck();
                genderGroup.check(R.id.other_gender);
                fullname.setFocusable(true);
            } else {
                Toast.makeText(getContext(), "Empty fields!", Toast.LENGTH_LONG).show();
            }

        }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.male_gender:
                patientGender = "M";
                break;
            case R.id.female_gender:
                patientGender = "F";
                break;
            case R.id.other_gender:
                patientGender = "O";
                break;
        }
    }
}