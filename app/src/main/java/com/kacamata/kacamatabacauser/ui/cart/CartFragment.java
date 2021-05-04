package com.kacamata.kacamatabacauser.ui.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamatabacauser.R;
import com.kacamata.kacamatabacauser.entity.Cart;
import com.kacamata.kacamatabacauser.entity.Profile;
import com.kacamata.kacamatabacauser.helper.DateHelper;
import com.kacamata.kacamatabacauser.helper.RupiahHelper;
import com.kacamata.kacamatabacauser.repository.ViewModelFactory;

import java.util.List;


public class CartFragment extends Fragment {
    private CartAdapter adapter;
    private TextView tvTotal, tvTotalItem, tvName, tvPhone, tvAddress, tvDate;
    CartViewModel viewModel;
    private Button btnPay;
    private View viewRek;
    String total, date;
    DatabaseReference reference;
    String phone;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_order);
        tvTotal = view.findViewById(R.id.tv_total);
        tvName = view.findViewById(R.id.tv_name);
        tvDate = view.findViewById(R.id.tv_date);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvAddress = view.findViewById(R.id.tv_address);
        viewRek = view.findViewById(R.id.lay_rek);
        Button btnFinish = view.findViewById(R.id.btn_finish);
        tvTotalItem = view.findViewById(R.id.tv_total_item);
        date = DateHelper.getCurrentDate();
        tvDate.setText(date);
        reference = FirebaseDatabase.getInstance().getReference("Kacamata");


        btnFinish.setOnClickListener(v -> showMessageDialog());


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new CartAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        Button btnClear = view.findViewById(R.id.btn_clear);
        btnPay = view.findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(v -> {
            viewRek.setVisibility(View.VISIBLE);
            btnPay.setVisibility(View.INVISIBLE);
        });
        btnClear.setOnClickListener(v -> setAlertDialog());

        viewModel = obtainViewModel(requireActivity());
        viewModel.getAllCart().observe(requireActivity(), cartObserver);
        viewModel.getTotalCart().observe(requireActivity(), totalObserver);
        viewModel.getTotalItem().observe(requireActivity(), itemObserver);
        viewModel.getName().observe(requireActivity(), nameObserver);
        viewModel.getPhone().observe(requireActivity(), phoneObserver);
        viewModel.getAddress().observe(requireActivity(), addressObserver);


    }

    private void showMessageDialog() {

        String dialogTitle, dialogMessage;
        dialogMessage = getString(R.string.message_pay);
        dialogTitle = getString(R.string.message_send);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    Profile profile = new Profile();
                    profile.setProcess("Sudah bayar");
                    reference.child("order").child(phone).child("process").setValue(date);

                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }


    private void setAlertDialog() {
        String dialogTitle, dialogMessage;
        dialogMessage = getString(R.string.message_delete);
        dialogTitle = getString(R.string.delete);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    viewModel.deleteAllCart();
                    tvTotalItem.setText("");
                    tvTotal.setText("");
                    tvDate.setText("");
                    btnPay.setVisibility(View.VISIBLE);
                    viewRek.setVisibility(View.INVISIBLE);


                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    private final Observer<List<Cart>> cartObserver = new Observer<List<Cart>>() {
        @Override
        public void onChanged(List<Cart> carts) {
            if (carts != null) {
                adapter.setListCart(carts);
            }
        }
    };
    private final Observer<String> totalObserver = new Observer<String>() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onChanged(String s) {
            if (s != null) {
                total = s;
                tvTotal.setText(RupiahHelper.formatRupiah(Double.parseDouble(total)));

            }
        }
    };
    private final Observer<String> itemObserver = new Observer<String>() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onChanged(String it) {
            if (it != null) {
                tvTotalItem.setText(it);
            }
        }
    };
    private final Observer<String> nameObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            if (s != null) {
                tvName.setText(s);
            }
        }
    };
    private final Observer<String> phoneObserver = new Observer<String>() {
        @Override
        public void onChanged(String p) {
            if (p != null) {
                phone = p;
                tvPhone.setText(phone);
            }

        }
    };


    private final Observer<String> addressObserver = new Observer<String>() {
        @Override
        public void onChanged(String ad) {
            if (ad != null) {
                tvAddress.setText(ad);
            }

        }
    };


    private CartViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(CartViewModel.class);
    }

}