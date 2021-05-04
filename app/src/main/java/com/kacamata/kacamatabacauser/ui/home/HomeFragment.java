package com.kacamata.kacamatabacauser.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamatabacauser.R;
import com.kacamata.kacamatabacauser.entity.Product;
import com.kacamata.kacamatabacauser.helper.RupiahHelper;
import com.kacamata.kacamatabacauser.repository.ViewModelFactory;
import com.kacamata.kacamatabacauser.ui.profile.ProfileUpdateActivity;
import com.kacamata.kacamatabacauser.ui.profile.ProfileViewModel;


public class HomeFragment extends Fragment {
    private FirebaseRecyclerOptions<Product> options;
    private FirebaseRecyclerAdapter<Product, ProHolder> adapter;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    ProfileViewModel viewModel;
    private String phone;


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvPro = view.findViewById(R.id.rv_pro);
        viewModel = obtainViewModel(requireActivity());
        viewModel.getPhone().observe(requireActivity(), phoneObserver);

        rvPro.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPro.setHasFixedSize(false);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance()
                .getReference()
                .child("Kacamata")
                .child("product");
        reference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(reference, Product.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Product, ProHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProHolder holder, int position, @NonNull Product model) {
                holder.tvProId.setText(model.getProId());
                holder.tvTitle.setText(model.getTitle());
                holder.tvPrice.setText(RupiahHelper.formatRupiah(Double.parseDouble(model.getPrice())));
                holder.tvDesc.setText(model.getDescription());
                RequestOptions requestOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(requireActivity())
                        .load(model.getImageUrl())
                        .apply(requestOptions)
                        .override(400, 400)
                        .into(holder.imgPro);
                holder.itemView.setOnClickListener(v -> {
                    if (phone != null) {
                        Intent intent = new Intent(view.getContext(), OrderActivity.class);
                        intent.putExtra("tvProId", model.getProId());
                        intent.putExtra("tvTitle", model.getTitle());
                        intent.putExtra("tvPrice", model.getPrice());
                        intent.putExtra("tvDesc", model.getDescription());
                        intent.putExtra("imgPro", model.getImageUrl());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(view.getContext(), ProfileUpdateActivity.class);
                        startActivity(intent);

                    }


                });


            }

            @NonNull
            @Override
            public ProHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
                return new ProHolder(view);
            }
        };
        rvPro.setAdapter(adapter);

    }

    private final Observer<String> phoneObserver = new Observer<String>() {
        @Override
        public void onChanged(String ph) {
            if (ph != null) {
                phone = ph;
            }

        }
    };

    private static class ProHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvProId, tvPrice, tvDesc;
        ImageView imgPro;

        public ProHolder(@NonNull View itemView) {
            super(itemView);
            tvProId = itemView.findViewById(R.id.tv_proId);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDesc = itemView.findViewById(R.id.tv_description);
            imgPro = itemView.findViewById(R.id.img_pro);

        }
    }

    private ProfileViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(ProfileViewModel.class);
    }
}


