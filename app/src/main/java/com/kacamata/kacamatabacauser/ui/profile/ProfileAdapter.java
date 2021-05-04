package com.kacamata.kacamatabacauser.ui.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kacamata.kacamatabacauser.R;
import com.kacamata.kacamatabacauser.entity.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    List<Profile>profiles = new ArrayList<>();
    final Activity activity;

    public ProfileAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile,parent,false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.tvName.setText(profile.getName());
        holder.tvPhone.setText("Tlp/HP : "+profile.getPhone());
        holder.tvAddress.setText("Alamat : "+profile.getAddress());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(),ProfUpDelActivity.class);
            intent.putExtra("tvName",profile.getName());
            intent.putExtra("tvPhone",profile.getPhone());
            intent.putExtra("tvAddress",profile.getAddress());
            v.getContext().startActivity(intent);


        });




    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public void setListProfile(List<Profile> prof) {
        profiles.clear();
        profiles.addAll(prof);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvPhone,tvAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvAddress = itemView.findViewById(R.id.tv_address);


        }
    }
}
