/*
 * Part of planned enhancement: Category 1 - Software Design: Refactoring service layer
 * Professor Conlan
 * CS499
 * Created: 1/21/26
 */
package com.CS360.weighttracker.service;

import com.CS360.weighttracker.Dao.GoalWeightDAO;
import com.CS360.weighttracker.model.GoalWeight;

//This is a service class that manages business logic related to a user's goal weight. It as a
//middle man between the UI layer and the data access layer. This ensures activites do not interact
//directly with database operations
public class GoalService {
    private final GoalWeightDAO dao;

    public GoalService(GoalWeightDAO dao) {
        this.dao = dao;
    }
    //gets goal weight per specific user Id
    public GoalWeight getGoal(int userId) {
        return dao.getGoalWeightForUser(userId);
    }
    //Adds users goal weight
    public void saveGoal(int userId, double weight) {
        dao.insert(new GoalWeight(weight, userId));
    }
}
