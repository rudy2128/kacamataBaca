package com.kacamata.kacamatabacauser.ui.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamatabacauser.R;
import com.kacamata.kacamatabacauser.entity.Profile;
import com.kacamata.kacamatabacauser.repository.ViewModelFactory;

public class ProfUpDelActivity extends AppCompatActivity {
    private EditText edtName,edtAddress;
    private TextView tvPhone;
    private String name,phone,address;
    ProfileViewModel viewModel;
    private Profile profile;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_up_del);
        tvPhone = findViewById(R.id.tv_phone);
        edtName = findViewById(R.id.edt_name);
        edtAddress = findViewById(R.id.edt_address);
        Button btnUpdate = findViewById(R.id.btn_update);

        phone = getIntent().getStringExtra("tvPhone");
        name = getIntent().getStringExtra("tvName");
        address = getIntent().getStringExtra("tvAddress");
        tvPhone.setText(phone);
        edtName.setText(name);
        edtAddress.setText(address);

        viewModel = obtainViewModel(ProfUpDelActivity.this);
        reference = FirebaseDatabase.getInstance().getReference("Kacamata");


        btnUpdate.setOnClickListener(v -> {
            phone = tvPhone.getText().toString();
            name = edtName.getText().toString();
            address = edtAddress.getText().toString();
            if (name.isEmpty()){
                edtName.setError(getString(R.string.empty));
            }else if (address.isEmpty()){
                edtAddress.setError(getString(R.string.empty));
            }else {

                profile = new Profile(phone, name, address);
                profile.setPhone(phone);
                profile.setName(name);
                profile.setAddress(address);
                viewModel.update(profile);
                reference.child("order").child(phone).child("name").setValue(name);
                reference.child("order").child(phone).child("address").setValue(address);
                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.del_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        String dialogTitle, dialogMessage;
        dialogMessage = getString(R.string.message_delete);
        dialogTitle = getString(R.string.delete);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    viewModel.deleteProf(phone);
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }
    private ProfileViewModel obtainViewModel(ProfUpDelActivity profUpDelActivity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(profUpDelActivity.getApplication());
        return new ViewModelProvider(profUpDelActivity, factory).get(ProfileViewModel.class);
    }

}