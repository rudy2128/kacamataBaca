package com.kacamata.kacamatabacauser.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kacamata.kacamatabacauser.ui.cart.CartViewModel;
import com.kacamata.kacamatabacauser.ui.profile.ProfileViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private ViewModelFactory(Application application) {
        mApplication = application;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(mApplication);

        }else if (modelClass.isAssignableFrom(CartViewModel.class)){
            return (T) new CartViewModel(mApplication);
        }


        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

}
