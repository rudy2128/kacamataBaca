package com.kacamata.kacamatabacauser.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kacamata.kacamatabacauser.R;
import com.kacamata.kacamatabacauser.entity.Profile;
import com.kacamata.kacamatabacauser.repository.ViewModelFactory;

import java.util.List;


public class ProfileFragment extends Fragment {
    ProfileAdapter adapter;
    ProfileViewModel viewModel;

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_prof);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new ProfileAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        viewModel = obtainViewModel(requireActivity());
        viewModel.getAllProf().observe(requireActivity(), profileObserver);


    }

    private final Observer<List<Profile>> profileObserver = new Observer<List<Profile>>() {
        @Override
        public void onChanged(List<Profile> prof) {
            adapter.setListProfile(prof);

        }
    };


    private ProfileViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(ProfileViewModel.class);
    }

}