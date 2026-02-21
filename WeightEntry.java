package com.CS360.weighttracker.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//creates weight entries entity that saves the date, weight and userId

//Part of Category three planned enhancement
//This help with indexing HashMap by date and aggregating subqueries for first/last weights and BMI
@Entity(
        //Declares mapping between model and schema
        tableName = "weight_entries",
        //Indexes userId and date for faster lookup
        indices = {
                @Index(value = {"userId", "date"})
        },
        foreignKeys = {
                @ForeignKey(
                        //Connects to User database
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        //If user account is deleted, remove all associated weight entries
                        onDelete = ForeignKey.CASCADE
                )
        })

public class WeightEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private double weight;
    private int userId; // Foreign key association to the user

    public WeightEntry(String date, double weight, int userId) {
        this.date = date;
        this.weight = weight;
        this.userId = userId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
