//Chart rendering uses MPAndroidChart
//Library: MPAndroidChart by Phi Jay
//License: Apache 2.0
//Source: https://github.com/PhilJay/MPAndroidChart

/*
 * Part of planned enhancement:
 * Category 1 - Software Design
 * Category 2 - Data Structure and Algorithms
 * Category 3 - Databases
 *
 * Christopher DeMello
 * Professor Conlan
 * CS499
 * Created: 1/24/26
 * Modified: 2/20/26
 */

package com.CS360.weighttracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.CS360.weighttracker.Dao.AppDatabase;
import com.CS360.weighttracker.model.WeightEntry;
import com.CS360.weighttracker.repository.WeightRepository;
import com.CS360.weighttracker.util.ChartUtils;
import com.CS360.weighttracker.util.HashMapWeightLookup;
import com.CS360.weighttracker.util.InputValidator;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private int userId;
    private WeightRepository weightRepository;

    //Used to help render chart
    private LineChart weightLineChart;
    private final List<String> xLabels = new ArrayList<>();

    //Used to implement HashMap lookup
    private EditText editTextLookupDate;
    private TextView tvDisplayWeightFromDate;
    private Map<String, WeightEntry> weightByDate = new HashMap<>();

    //Used to implement stats - total weight loss and BMI
    private TextView tvDisplayDifferenceWeight;
    private TextView tvDisplayBMI;

    //Dashboard activity is started
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Grab XML layout
        setContentView(R.layout.activity_dashboard);

        userId = getIntent().getIntExtra("USER_ID", -1);

        //Inflates toolbar for returning to main activity and logging out
        Toolbar toolbar = findViewById(R.id.toolbarDashboard);
        setSupportActionBar(toolbar);

        //Finds chart container in XML layout
        weightLineChart = findViewById(R.id.lineChartWeightTrend);

        //finds text view in XML layout for stats
        tvDisplayDifferenceWeight = findViewById(R.id.tvDisplayDifferenceWeight);
        tvDisplayBMI = findViewById(R.id.tvDisplayBMI);

        //Finds edit and text view in XML layout for weight lookup
        editTextLookupDate = findViewById(R.id.editTextLookupDate);
        tvDisplayWeightFromDate = findViewById(R.id.tvDisplayWeightFromDate);

        //Triggers a search lookup when user finishes keyboard input
        editTextLookupDate.setOnEditorActionListener((v, actionId, event) -> {
            boolean imeDone = (actionId == EditorInfo.IME_ACTION_SEARCH);
            boolean enterKey = (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN);

            if (imeDone || enterKey) {
                runLookupByDate();
                return true;
            }
            return false;
        });

        //Sets up database to be used
        AppDatabase database = AppDatabase.getInstance(this);
        //Communicates to database through repository
        weightRepository = new WeightRepository(database.weightEntryDao());
        //Sets up Chart
        setupChartBaseStyle();
        //Watches to see if weights are added and reformats chart
        observeWeightsandRender();
        //Watches to update to current stats
        observeStats();
    }

    //Creates a chart for single use based on weight entries list.
    private void setupChartBaseStyle() {
        //Removes description text for cleaner look
        //Removes right-side Y-axis
        //Adds text if chart is empty
        //Set background color to white
        weightLineChart.getDescription().setEnabled(false);
        weightLineChart.getAxisRight().setEnabled(false);
        weightLineChart.setNoDataText("No weights yet. Add an entry to see chart.");
        weightLineChart.setBackgroundColor(Color.WHITE);

        //Gets the x-axis object, passes to chart utility. This helps with bottom positioning
        //Ensures one label per entry, and converts numeric index to date string
        ChartUtils.styleXAxisAsDates(weightLineChart.getXAxis(), xLabels);
    }

    //Allows chart to be dynamic. It listens for new weight entries and re-renders when needed
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void observeWeightsandRender() {
        weightRepository.observeEntries(userId).observe(this, weights -> {

            //Build /refresh HashMap index (Category 2)
            weightByDate = HashMapWeightLookup.buildIndex(weights);

            //Builds chart points and updates xLabels with dates
            weightLineChart.setData(ChartUtils.buildWeightLineData(weights, xLabels));
            //When new data arrives, xLabels changes. This means reapplying labels for alignment
            ChartUtils.styleXAxisAsDates(weightLineChart.getXAxis(), xLabels);
            //Forces a redraw of view
            weightLineChart.invalidate();

            //if user typed a date, refresh the lookup result automatically. This keeps it dynamic
            //like the chart
            String currentTyped = editTextLookupDate.getText().toString();
            if (currentTyped != null && !currentTyped.trim().isEmpty()) {
                runLookupByDate();
            }
        });
    }

    //Inflates menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    //Inflates Icons within menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //if home icon is clicked, return to main activity
        if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("USER_ID", userId);
            // Optional: avoids stacking multiple MainActivities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        //if dropdown menu, then logout is selected. Return Login screen
        if (id == R.id.action_logout) {
            showLogoutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Creates a dialog box to make sure user wants to logout
    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Log out?")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Log out", (dialog, which) -> {
                    Intent intent = new Intent(this, LoginActivity.class);

                    // Clear back stack so user can't press back into the app
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    //Gets user input for weight lookup from specific date
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void runLookupByDate() {
        //gets user input
        String dateInput = editTextLookupDate.getText().toString();

        //Reuse InputValidator class to validate date
        InputValidator.InputValidationResult dateResult = InputValidator.validateDate(dateInput);

        //if date result not valid, display result in text view
        if (!dateResult.ok) {
            tvDisplayWeightFromDate.setText(dateResult.error);
            return;
        }
        //normalize date after validation
        String normalizedDate = dateResult.value;
        //finds matching date and returns weight entry
        WeightEntry match = HashMapWeightLookup.findByDate(weightByDate, normalizedDate);

        if (match == null) {
            //if no date not found
            tvDisplayWeightFromDate.setText("No weight entry found");
        } else {
            //display weight from weight entry
            tvDisplayWeightFromDate.setText(match.getWeight() + " lbs");
        }

    }

    //Method set up for the planned enhancement category three: databases
    //Used to display BMI and total weight lost stats
    private void observeStats() {
        //Display total weight lost
        weightRepository.observeTotalWeightLost(userId).observe(this, totalLost -> {
            //checks if null
            if (totalLost == null) {
                tvDisplayDifferenceWeight.setText("N/A");
                //displays if available
            } else {
                tvDisplayDifferenceWeight.setText(String.format("%.1f lbs", totalLost));
            }
        });
        //Display BMI
        weightRepository.observeBMI(userId).observe(this, bmi -> {
            if (bmi == null) {
                tvDisplayBMI.setText("N/A");
            } else {
                tvDisplayBMI.setText(String.format("%.1f", bmi));
            }
        });

    }
}