package com.kacamata.kacamatabacauser.ui.cart;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kacamata.kacamatabacauser.entity.Cart;
import com.kacamata.kacamatabacauser.entity.Profile;
import com.kacamata.kacamatabacauser.repository.LocalRepository;

import java.util.List;

public class CartViewModel extends ViewModel {
    private LocalRepository mLocalRepository;

    public CartViewModel(Application application) {
        mLocalRepository = new LocalRepository(application);

    }

    public LiveData<List<Cart>> getAllCart() {
        return mLocalRepository.getAllCart();
    }

    public void insert(Cart cart) {
        mLocalRepository.insert(cart);
    }
    public void update(Cart cart) {
        mLocalRepository.update(cart);
    }

    public void deleteAllCart() {
        mLocalRepository.deleteAllCart();
    }

    public LiveData<String> getTotalCart() {
        return mLocalRepository.getTotalCart();
    }

    public LiveData<String> getTotalItem() {
        return mLocalRepository.getTotalItem();
    }

    public LiveData<String> getPhone() {
        return mLocalRepository.getPhone();
    }
    public LiveData<String> getAddress() {
        return mLocalRepository.getAddress();
    }

    public LiveData<String> getDate() {
        return mLocalRepository.getDate();
    }



    public void update(Profile profile){
        mLocalRepository.update(profile);
    }

    public LiveData<String> getName() {
        return mLocalRepository.getName();
    }



}
