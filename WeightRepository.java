/*
 * Part of planned enhancement: Category 1 - Software Design: Refactoring repository layer
 * Christopher DeMello
 * Professor Conlan
 * CS499
 * Created: 1/21/26
 */
package com.CS360.weighttracker.repository;

import androidx.lifecycle.LiveData;

import com.CS360.weighttracker.Dao.WeightEntryDAO;
import com.CS360.weighttracker.model.WeightEntry;

import java.util.List;

//This class acts as an abstraction layer between business logic and the Room database. It centralizes
//operations related to WeightEntry objects and hides direct DAO access from the UI layer
public class WeightRepository {
    private final WeightEntryDAO weightDao;

    public WeightRepository(WeightEntryDAO weightDao) {
        this.weightDao = weightDao;
    }
    //Gets all weight entries (weight and date) associated to user id
    public List<WeightEntry> getEntries(int userId) {
        return weightDao.getAllWeightEntries(userId);
    }
    //Used to stop manually requerying data. This will be implemented in the line chart
    public LiveData<List<WeightEntry>> observeEntries(int userId) {
        return weightDao.observeAllWeightEntries(userId);
    }
    //Adds new  weight entry to database
    public void addEntry(WeightEntry entry) {
        weightDao.insert(entry);
    }
    //Deletes a specific weight entry from database
    public void deleteEntry(WeightEntry entry) {
        weightDao.delete(entry);
    }

    //Added new methods to grab stats for BMI and total weight lost
    //Part of planned enhancement. Category 3: Databases

    //Gets Total weight lost stat
    public LiveData<Double> observeTotalWeightLost(int userId) {
        return weightDao.observeTotalWeightLost(userId);
    }

    //Gets BMI stat
    public LiveData<Double> observeBMI(int userId) {
        return weightDao.observeBMI(userId);
    }

    //Gets most recent weight
    public LiveData<Double> observeLastestWeight(int userId) {
        return weightDao.observeLatestWeight(userId);
    }

}
