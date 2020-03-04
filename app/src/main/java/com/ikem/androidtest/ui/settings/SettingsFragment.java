package com.ikem.androidtest.ui.settings;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.ikem.androidtest.R;
import com.ikem.androidtest.ui.home.HomeFragment;
import com.ikem.androidtest.ui.home.HomeFragmentDirections;
import com.ikem.androidtest.ui.settings.SettingsFragmentDirections.ActionNavSettingsToNavHome;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    SharedPreferences sharedPref;
    TextInputEditText username;
    TextInputEditText maxPatients;
    String user, max_patients;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        sharedPref = Objects.requireNonNull(getContext()).getSharedPreferences("UsernameData", 0);

        username = root.findViewById(R.id.username);
        maxPatients = root.findViewById(R.id.max_patients);

        if(sharedPref.getString("Username","") != null) {

            user = sharedPref.getString("Username","");
            username.setText(user);

        }

        if(sharedPref.getString("MaxPatients","") != null) {

            max_patients = sharedPref.getString("MaxPatients","5");
            maxPatients.setText(max_patients);

        }

        Button save_button = root.findViewById(R.id.save_username);

        save_button.setOnClickListener(this::saveData);


        return root;
    }

    private void saveData(View v) {
        SharedPreferences sharedPref = getContext().getSharedPreferences("UsernameData", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Username", username.getText().toString());
        editor.putString("MaxPatients", maxPatients.getText().toString());
        editor.apply();

        ActionNavSettingsToNavHome action = SettingsFragmentDirections.actionNavSettingsToNavHome().setMaxPatients(Integer.parseInt(maxPatients.getText().toString()));
        Navigation.findNavController(v).navigate(action);
    }
}