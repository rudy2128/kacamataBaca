package com.kacamata.kacamatabacauser.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamatabacauser.R;
import com.kacamata.kacamatabacauser.entity.Cart;
import com.kacamata.kacamatabacauser.entity.Profile;
import com.kacamata.kacamatabacauser.helper.DateHelper;
import com.kacamata.kacamatabacauser.repository.ViewModelFactory;
import com.kacamata.kacamatabacauser.ui.cart.CartViewModel;

import java.util.Objects;

public class OrderActivity extends AppCompatActivity {
    private EditText edtQty, edtNote;
    TextView tvId, tvTitle, tvPrice,tvDesc;
    ImageView imgPro;
    String proId, title, price,imgUrl,description;
    private DatabaseReference reference;
    FirebaseAuth mAuth;
    private String phone, name,address;
    private CartViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        imgPro = findViewById(R.id.img_pro);
        tvId = findViewById(R.id.tv_proId);
        tvTitle = findViewById(R.id.tv_title);
        tvPrice = findViewById(R.id.tv_price);
        edtQty = findViewById(R.id.edt_qty);
        edtNote = findViewById(R.id.edt_note);
        tvDesc = findViewById(R.id.tv_description);


        Button btnOrder = findViewById(R.id.btn_order);
        reference = FirebaseDatabase.getInstance().getReference("Kacamata");


        viewModel = obtainViewModel(OrderActivity.this);
        viewModel.getPhone().observe(this, phoneObserver);
        viewModel.getName().observe(this, nameObserver);
        viewModel.getAddress().observe(this,addressObserver);

        imgUrl = getIntent().getStringExtra("imgPro");
        proId = getIntent().getStringExtra("tvProId");
        title = getIntent().getStringExtra("tvTitle");
        price = getIntent().getStringExtra("tvPrice");
        description = getIntent().getStringExtra("tvDesc");


        mAuth = FirebaseAuth.getInstance();
        Glide.with(this)
                .load(imgUrl)
                .override(300, 300)
                .fitCenter()
                .into(imgPro);
        tvId.setText(proId);
        tvTitle.setText(title);
        tvPrice.setText(price);
        tvDesc.setText(description);

        btnOrder.setOnClickListener(v -> {

            String date = DateHelper.getCurrentDate();
            String proId = tvId.getText().toString();
            String title = tvTitle.getText().toString().trim();
            String price = tvPrice.getText().toString().trim();
            String quantity = edtQty.getText().toString().trim();
            String note = edtNote.getText().toString().trim();
            String subtotal = String.valueOf(Integer.parseInt(price) * Integer.parseInt(quantity));

            if (quantity.isEmpty()) {
                edtQty.setError(getString(R.string.empty));

            } else {

                String id = reference.push().getKey();
                Cart cart = new Cart(title,price,quantity,note,subtotal);
                int cartId = cart.getCartId();
                cart.setCartId(cartId);
                cart.setProId(proId);
                cart.setTitle(title);
                cart.setNote(note);
                cart.setQuantity(quantity);
                cart.setPrice(price);
                cart.setSubtotal(subtotal);
                cart.setDate(date);

                Profile profile = new Profile(phone,name,address);
                profile.setPhone(phone);
                profile.setName(name);
                profile.setAddress(address);

                viewModel.insert(cart);
                reference.child("order").child(phone).child("order_list").child(Objects.requireNonNull(id)).setValue(cart);
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
                edtQty.setText("");
                edtNote.setText("");
                finish();

            }
        });

    }
    private final Observer<String> phoneObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            if (s != null) {
                phone = s;
            }
        }
    };
    private final Observer<String> nameObserver = new Observer<String>() {
        @Override
        public void onChanged(String n) {
            if (n != null) {
                name = n;
            }
        }
    };

    private final Observer<String>addressObserver = new Observer<String>() {
        @Override
        public void onChanged(String ad) {
            if (ad !=null){
                address = ad ;
            }

        }
    };


    private CartViewModel obtainViewModel(OrderActivity orderActivity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(orderActivity.getApplication());
        return new ViewModelProvider(orderActivity, factory).get(CartViewModel.class);
    }
}