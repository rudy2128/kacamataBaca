package com.kacamata.kacamatabacauser.ui.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamatabacauser.R;
import com.kacamata.kacamatabacauser.entity.Profile;
import com.kacamata.kacamatabacauser.repository.ViewModelFactory;

public class ProfileUpdateActivity extends AppCompatActivity {
    private EditText edtName, edtAddress, edtPhone;
    private String name, phone, address;
    ProfileViewModel viewModel;
    private Profile profile;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        edtPhone = findViewById(R.id.edt_phone);
        edtName = findViewById(R.id.edt_name);
        edtAddress = findViewById(R.id.edt_address);
        Button btnSave = findViewById(R.id.btn_save);

        viewModel = obtainViewModel(ProfileUpdateActivity.this);
        reference = FirebaseDatabase.getInstance().getReference("Kacamata");


        btnSave.setOnClickListener(v -> {

            phone = edtPhone.getText().toString();
            name = edtName.getText().toString();
            address = edtAddress.getText().toString();
            if (phone.isEmpty()) {
                edtPhone.setError(getString(R.string.empty));
            } else if (name.isEmpty()) {
                edtName.setError(getString(R.string.empty));
            } else if (address.isEmpty()) {
                edtAddress.setError(getString(R.string.empty));
            } else {

                profile = new Profile(phone, name, address);
                profile.setPhone(phone);
                profile.setName(name);
                profile.setAddress(address);
                viewModel.insert(profile);
                reference.child("order").child(phone).setValue(profile);
                Toast.makeText(getApplicationContext(), "Profile saved", Toast.LENGTH_SHORT).show();
                finish();
            }


        });
    }


    private ProfileViewModel obtainViewModel(ProfileUpdateActivity profileUpdateActivity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(profileUpdateActivity.getApplication());
        return new ViewModelProvider(profileUpdateActivity, factory).get(ProfileViewModel.class);
    }

}
