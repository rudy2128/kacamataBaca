package com.kacamata.kacamatabacauser.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kacamata.kacamatabacauser.data.ProductDao;
import com.kacamata.kacamatabacauser.data.ProductDatabase;
import com.kacamata.kacamatabacauser.entity.Cart;
import com.kacamata.kacamatabacauser.entity.Profile;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalRepository {
    private ProductDao mProductDao;
    private ExecutorService executorService;


    public LocalRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        ProductDatabase db = ProductDatabase.getDatabase(application);
        mProductDao = db.productDao();

    }

    public void insert(final Profile profile) {
        executorService.execute(() -> mProductDao.insert(profile));
    }
    public void delete(String phone) {
        executorService.execute(() -> mProductDao.delete(phone));



    } public void update(final Profile profile) {
        executorService.execute(() -> mProductDao.update(profile));

    }

    public LiveData<List<Profile>> getAllProf() {
        return mProductDao.getAllProf();
    }

    public LiveData<String>getPhone(){
        return mProductDao.getPhone();
    }

    public LiveData<String>getName(){
        return mProductDao.getName();
    }
    public LiveData<String>getDate(){
        return mProductDao.getDate();
    }
    public void updateProfile(String name,String address,String phone){
        executorService.execute(()->mProductDao.updateProfile(name,address,phone));
    }


    public LiveData<String>getAddress(){
        return mProductDao.getAddress();
    }

    public void insert(final Cart cart){
        executorService.execute(() -> mProductDao.insert(cart));
    }
    public void update(final Cart cart){
        executorService.execute(() -> mProductDao.update(cart));
    }

    public void deleteAllCart(){
        executorService.execute(()-> mProductDao.deleteAllCart());
    }

    public void deleteAllCus(){
        executorService.execute(()-> mProductDao.deleteAllCus());
    }

    public LiveData<List<Cart>>getAllCart(){ return mProductDao.getAllCart();}

    public LiveData<String>getTotalCart(){return  mProductDao.getTotalCart();}

    public LiveData<String>getTotalItem(){return  mProductDao.getTotalItem();}

}
