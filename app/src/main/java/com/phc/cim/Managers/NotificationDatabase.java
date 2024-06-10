package com.phc.cim.Managers;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.phc.cim.DataElements.NotificationDao;
import com.phc.cim.DataElements.NotificationItem;

@Database(entities = {NotificationItem.class}, version = 1, exportSchema = false)
public abstract class NotificationDatabase extends RoomDatabase {

    private static NotificationDatabase instance;

    public abstract NotificationDao notificationDao();

    public static synchronized NotificationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NotificationDatabase.class, "notification_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

