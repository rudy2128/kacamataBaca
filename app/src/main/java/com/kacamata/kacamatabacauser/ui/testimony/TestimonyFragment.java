package com.kacamata.kacamatabacauser.ui.testimony;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamatabacauser.R;
import com.kacamata.kacamatabacauser.entity.Test;


public class TestimonyFragment extends Fragment {

    private FirebaseRecyclerOptions<Test> options;
    private FirebaseRecyclerAdapter<Test, TestHolder> adapter;
    DatabaseReference reference;
    FirebaseAuth mAuth;

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


    public TestimonyFragment() {
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
        return inflater.inflate(R.layout.fragment_testimony, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvTest = view.findViewById(R.id.rv_test);
        rvTest.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTest.setHasFixedSize(false);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance()
                .getReference("Kacamata")
                .child("test");
        reference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Test>()
                .setQuery(reference, Test.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Test, TestHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TestHolder holder, int position, @NonNull Test model) {
                holder.tvTestId.setText(model.getTestId());
                holder.tvDesc.setText(model.getDescription());
                Glide.with(requireActivity())
                        .load(model.getImageUrl())
                        .override(400, 400)
                        .fitCenter()
                        .into(holder.imgTest);
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(requireActivity(),DetailTestActivity.class);
                    intent.putExtra("tvTestId",model.getTestId());
                    intent.putExtra("tvDesc",model.getDescription());
                    intent.putExtra("imgTest",model.getImageUrl());
                    startActivity(intent);
                });

            }


            @NonNull
            @Override
            public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
                return new TestHolder(view);
            }
        };
        rvTest.setAdapter(adapter);
    }


    private static class TestHolder extends RecyclerView.ViewHolder {
        TextView tvTestId, tvDesc;
        ImageView imgTest;

        public TestHolder(@NonNull View itemView) {
            super(itemView);
            tvTestId = itemView.findViewById(R.id.tv_testId);
            tvDesc = itemView.findViewById(R.id.tv_description);
            imgTest = itemView.findViewById(R.id.img_test);

        }
    }
}
