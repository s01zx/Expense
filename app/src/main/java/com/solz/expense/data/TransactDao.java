package com.solz.expense.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TransactDao {

    @Query("SELECT * FROM expense")
    List<Transaction> loadAllTasks();


    // works for both inserting new entry and updating the entry
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Transaction taskEntry);

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateTask(Transaction taskEntry);

    @Delete
    void deleteTask(Transaction taskEntry);
}
