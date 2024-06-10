package com.phc.cim.DataElements;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NotificationItem> notifications);

    @Query("SELECT * FROM notifications")
    LiveData<List<NotificationItem>> getAllNotifications();
}

