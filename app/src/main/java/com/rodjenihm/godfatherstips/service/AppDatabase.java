package com.rodjenihm.godfatherstips.service;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rodjenihm.godfatherstips.model.Message;
import com.rodjenihm.godfatherstips.model.Tip;

@Database(entities = {Tip.class, Message.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract TipDao tipDao();
    public abstract MessageDao messageDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "gf-tips")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
