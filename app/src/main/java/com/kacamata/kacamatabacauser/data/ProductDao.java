package com.kacamata.kacamatabacauser.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kacamata.kacamatabacauser.entity.Cart;
import com.kacamata.kacamatabacauser.entity.Profile;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Profile profile);
    @Update
    void update(Profile profile);
    @Delete
    void delete(Profile profile);

    @Query("Delete from profile_table where phone=:phone")
    void delete(String phone);

    @Query("UPDATE profile_table SET name=:name, address=:address WHERE phone=:phone")
    void updateProfile(String name,String address,String phone);



    @Query("SELECT * from profile_table LIMIT 1")
    LiveData<List<Profile>>getAllProf();

    @Query("SELECT phone from profile_table")
    LiveData<String>getPhone();

    @Query("SELECT date from cart_table")
    LiveData<String>getDate();


    @Query("SELECT address from profile_table")
    LiveData<String>getAddress();

    @Query("SELECT name from profile_table")
    LiveData<String>getName();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cart cart);
    @Update
    void update(Cart cart);
    @Delete
    void delete(Cart cart);

    @Query("SELECT * from cart_table ")
    LiveData<List<Cart>> getAllCart();

    @Query(" DELETE from cart_table")
    void  deleteAllCart();

    @Query(" DELETE from profile_table")
    void  deleteAllCus();

    @Query("SELECT COALESCE(sum(COALESCE(subtotal,0)), 0) From cart_table")
    LiveData<String> getTotalCart();

    @Query("SELECT COALESCE(sum(COALESCE(quantity,0)), 0) From cart_table")
    LiveData<String> getTotalItem();


}
