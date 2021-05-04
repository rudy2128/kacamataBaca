package com.kacamata.kacamatabacauser.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.kacamata.kacamatabacauser.entity.Cart;
import com.kacamata.kacamatabacauser.entity.Profile;

@Database(entities = {Profile.class, Cart.class}, version = 1, exportSchema = false)
public abstract class  ProductDatabase extends RoomDatabase {
        public abstract ProductDao productDao();

        private static volatile ProductDatabase INSTANCE;

        public static ProductDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (ProductDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                ProductDatabase.class, "product_store.db")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

}
