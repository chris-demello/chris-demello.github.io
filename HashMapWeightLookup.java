package com.CS360.weighttracker.util;

import com.CS360.weighttracker.model.WeightEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Category 2 of planned enhancement: Algorithms and Data Structures:
//Create a Hashmap index of weight entries by date to allow for O(1) lookup
//This is more efficient than O(n) which will check every item in a list, whereas a hashmap
//will use a key to find the date using only one operation.

//key = date string
//value = weight entry for that date


/*
 * This implementation was sourced by Java HashMap behavior as described
 * in the following guide to HashMap:
 *   https://www.baeldung.com/java-hashmap
 * See: Baeldung. “Guide to Java HashMap.”
 */

public class HashMapWeightLookup {

    private HashMapWeightLookup() {}

    //Builds an index from a list of WeightEntry rows. If duplicate dates exist,
    //the last one encountered is picked.
    //String = date, WeightEntry = full record
    public static Map<String, WeightEntry> buildIndex(List<WeightEntry> entries) {
        //Creates empty HashMap
        Map<String, WeightEntry> map = new HashMap<>();
        //Checks list if null, else return
        if (entries == null) {
            return map;
        }
        //Loops through weight entries in list
        for (WeightEntry w : entries) {
            //Checks if entry is null, else return
            if (w == null) {
                continue;
            }
            //Grabs the date, which will be used as the key
            String date = w.getDate();
            //Checks if date is empty, else return
            if (date == null) {
                continue;
            }
            //inserts date and entry
            map.put(date, w);
        }
        //returns hashmap
        return map;
    }

    //Finds WeightEntry by date key. Returns null if not found
    public static WeightEntry findByDate(Map<String, WeightEntry> index, String dateKey) {
        if (index == null || dateKey == null) {
            return null;
        }
        return index.get(dateKey);
    }
}
