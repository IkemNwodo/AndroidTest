package com.ikem.androidtest.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "patients_table")
public class Patient {

    @NonNull
    @PrimaryKey
     String counter;
     String fullname;
     String email;
     String age;
     String gender;

    public Patient(String fullname, String email, String age, String gender, @NonNull String counter) {
        this.fullname = fullname;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.counter = counter;
    }
   public static DiffUtil.ItemCallback<Patient> DIFF_CALLBACK = new DiffUtil.ItemCallback<Patient>() {
        @Override
        public boolean areItemsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
            return oldItem.counter.equals(newItem.counter);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean areContentsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
            return oldItem.equals(newItem);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient Patient = (Patient) o;
        return Objects.equals(counter, Patient.counter) &&
                Objects.equals(fullname, Patient.fullname) &&
                Objects.equals(age, Patient.age) &&
                Objects.equals(email, Patient.email) &&
                Objects.equals(gender, Patient.gender);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(fullname, counter, age, email, gender);
    }

    @NonNull
    public String getCounter() {
        return counter;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }


}
