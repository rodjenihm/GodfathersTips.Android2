package com.rodjenihm.godfatherstips.service;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.rodjenihm.godfatherstips.model.Tip;

import java.util.List;

@Dao
public interface TipDao {
    @Query("SELECT * FROM tip")
    List<Tip> getAll();

    @Insert
    void insertAll(Tip... tips);

    @Insert
    void insert(Tip tip);

    @Delete
    void delete(Tip tip);

    @Query("DELETE FROM tip")
    void nukeTable();
}
