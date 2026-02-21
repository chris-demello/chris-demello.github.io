package com.CS360.weighttracker.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.CS360.weighttracker.model.WeightEntry;

import java.util.List;
//This adds a weight entry, gets specfic weight entries based on user id,
//uses live data to help keep data in the dashboard activity current.
//Parameterized SQL queries to prevent SQL injection attacks
@Dao
public interface WeightEntryDAO {
    @Insert
    void insert(WeightEntry weightEntry);

    //gets all weight entries in a list for a specific user id
    @Query("SELECT * FROM weight_entries WHERE userId = :userId ORDER BY id DESC")
    List<WeightEntry> getAllWeightEntries(int userId);

    //Returns live data, so dashboard can be updated automatically
    @Query("SELECT * FROM weight_entries WHERE userId = :userId ORDER BY date ASC")
    LiveData<List<WeightEntry>> observeAllWeightEntries(int userId);

    //Delete a weight entry
    @Delete
    void delete(WeightEntry weightEntry);

    //Queries below are part of the Category three planned enhancement
    //LiveData is used to ensure stats are dynamic and update whenever
    //the user enters new input.

    //Gets most recent weight
    @Query("SELECT weight FROM weight_entries WHERE userId = :userId ORDER BY date DESC LIMIT 1")
    LiveData<Double> observeLatestWeight(int userId);

    // Get most recent weight for BMI and total weight difference stats
    @Query("SELECT weight FROM weight_entries WHERE userId = :userId ORDER BY date DESC LIMIT 1")
    LiveData<Double> observeRecentWeight(int userId);

    //Aggregate to calculate total weight loss (first weight - last weight)
    @Query(
            "SELECT " +
                    " (SELECT weight FROM weight_entries WHERE userId = :userId ORDER BY date ASC LIMIT 1) " +
                    " - " +
                    " (SELECT weight FROM weight_entries WHERE userId = :userId ORDER BY date DESC LIMIT 1) "
    )
    LiveData<Double> observeTotalWeightLost(int userId);

    //Compute BMI using the most recent weight and height
    //BMI = (weight_lbs * 703) / (height_in^2)
    @Query(
            "SELECT CASE " +
                    " WHEN (SELECT heightInches FROM users WHERE id = :userId) <= 0 THEN NULL " +
                    " WHEN (SELECT weight FROM weight_entries WHERE userId = :userId ORDER BY date DESC LIMIT 1) IS NULL THEN NULL " +
                    " ELSE (" +
                    "   (SELECT weight FROM weight_entries WHERE userId = :userId ORDER BY date DESC LIMIT 1) * 703.0" +
                    " ) / (" +
                    "   (SELECT heightInches FROM users WHERE id = :userId) * (SELECT heightInches FROM users WHERE id = :userId)" +
                    " ) END"
    )
    LiveData<Double> observeBMI(int userId);
}