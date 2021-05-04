package com.kacamata.kacamatabacauser.ui.profile;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kacamata.kacamatabacauser.entity.Profile;
import com.kacamata.kacamatabacauser.repository.LocalRepository;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    private LocalRepository mLocalRepository;

    public ProfileViewModel(Application application) {
        mLocalRepository = new LocalRepository(application);

    }


    public void insert(Profile profile) {
        mLocalRepository.insert(profile);
    }

    public void update(Profile profile) {
        mLocalRepository.update(profile);
    }

    public void updateProfile(String name,String address,String phone){mLocalRepository.updateProfile(name,address,phone);}

    public void deleteProf(String phone) {
        mLocalRepository.delete(phone);
    }

    public LiveData<String> getPhone() {
        return mLocalRepository.getPhone();
    }

    public LiveData<List<Profile>> getAllProf() {
        return mLocalRepository.getAllProf();
    }


}
