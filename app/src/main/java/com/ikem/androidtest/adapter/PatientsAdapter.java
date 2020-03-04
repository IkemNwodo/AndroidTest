package com.ikem.androidtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ikem.androidtest.R;
import com.ikem.androidtest.model.Patient;

import java.util.List;

public class PatientsAdapter extends ListAdapter<Patient, PatientsAdapter.PatientsViewHolder> {

    public PatientsAdapter(){
        super(Patient.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PatientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list, parent, false);
        return new PatientsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsViewHolder holder, int position) {
        Patient patient = getItem(position);
        holder.textView.setText(patient.getCounter() + " Patient { fullName=" + patient.getFullname() +", Gender= "+ patient.getGender() +
                ", Age= " +patient.getAge() +" }");
    }

    public static class PatientsViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public PatientsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.patient_details);
        }
    }
}
