package com.bams.android.examenspeechtotext;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReportsActivity extends AppCompatActivity {

    private int dayFrom, monthFrom, yearFrom, hourFrom, minuteFrom;
    private int dayTo, monthTo, yearTo, hourTo, minuteTo;
    private Calendar dFrom;
    private Calendar dTo;
    private Button btnChooseFromDate, btnChooseFromTime, btnChooseToDate, btnChooseToTime, btnSearch;
    private TextView txtReportsFromDate, txtReportsFromTime, txtReportsToTime, txtReportsToDate;
    private ArrayList<Product> listProducts;
    private Calendar calendar;
    public static ArrayList<Product> searchListProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listProducts = new ArrayList<>();
        searchListProducts = new ArrayList<>();
        listProducts = MainActivity.listProducts;
        txtReportsFromDate = (TextView) findViewById(R.id.txtReportsFromDate);
        txtReportsFromTime = (TextView) findViewById(R.id.txtReportsFromTime);
        btnChooseFromDate = (Button) findViewById(R.id.btnChooseFromDate);
        btnChooseFromTime = (Button) findViewById(R.id.btnChooseFromTime);

        txtReportsToDate = (TextView) findViewById(R.id.txtReportsToDate);
        txtReportsToTime = (TextView) findViewById(R.id.txtReportsToTime);
        btnChooseToDate = (Button) findViewById(R.id.btnChooseToDate);
        btnChooseToTime = (Button) findViewById(R.id.btnChooseToTime);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        /**
         * Calendar
         */
        calendar = Calendar.getInstance();
        dayFrom = calendar.get(Calendar.DAY_OF_MONTH);
        monthFrom = calendar.get(Calendar.MONTH);
        yearFrom = calendar.get(Calendar.YEAR);
        hourFrom = calendar.get(Calendar.HOUR_OF_DAY);
        minuteFrom = calendar.get(Calendar.MINUTE);
        dayTo = calendar.get(Calendar.DAY_OF_MONTH);
        monthTo = calendar.get(Calendar.MONTH);
        yearTo = calendar.get(Calendar.YEAR);
        hourTo = calendar.get(Calendar.HOUR_OF_DAY);
        minuteTo = calendar.get(Calendar.MINUTE);


        txtReportsFromDate.setText(String.format("%1$d-%2$d-%3$d",
                dayFrom, monthFrom, yearFrom));
        txtReportsFromTime.setText(String.format("%1$d:%2$d",
                hourFrom, minuteFrom));
        txtReportsToDate.setText(String.format("%1$d-%2$d-%3$d",
                dayTo, monthTo, yearTo));
        txtReportsToTime.setText(String.format("%1$d:%2$d",
                hourTo, minuteTo));

        btnChooseFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReportsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtReportsFromDate.setText(String.format("%1$d-%2$d-%3$d",
                                dayOfMonth, (monthOfYear + 1), year));
                    }
                }, yearFrom, monthFrom, dayFrom);
                datePickerDialog.show();
            }
        });

        btnChooseFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReportsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourFrom = hourOfDay;
                        minuteFrom = minute;
                        txtReportsFromTime.setText(String.format("%1$d:%2$d",
                                hourFrom, minuteFrom));
                    }
                }, hourFrom, minuteFrom, false);
                timePickerDialog.show();
            }
        });

        btnChooseToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReportsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yearDate, int monthOfYear, int dayOfMonth) {
                        monthTo = monthOfYear;
                        dayTo = dayOfMonth;
                        yearTo = yearDate;
                        txtReportsToDate.setText(String.format("%1$d-%2$d-%3$d",
                                dayTo, (monthTo + 1), yearTo));
                    }
                }, yearTo, monthTo, dayTo);
                datePickerDialog.show();
            }
        });

        btnChooseToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReportsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourTo = hourOfDay;
                        minuteTo = minute;
                        txtReportsToTime.setText(String.format("%1$d:%2$d",
                                hourOfDay, minute));
                    }
                }, hourTo, minuteTo, false);
                timePickerDialog.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dFrom = Calendar.getInstance();
                dFrom.set(yearFrom, monthFrom, dayFrom, hourFrom, minuteFrom, 0);
                dTo = Calendar.getInstance();
                dTo.set(yearTo, monthTo, dayTo, hourTo, minuteTo, 59);
                searchListProducts(dFrom, dTo);
            }
        });

    }

    /**
     * Search products between dates and with status BOUGHT
     *
     * @param from
     * @param to
     */
    public void searchListProducts(Calendar from, Calendar to) {
        searchListProducts = new ArrayList<Product>();

        for (Product a : listProducts) {
            if ((a.date.after(from.getTime()) && a.date.before(to.getTime())) && a.status == "BOUGHT") {
                searchListProducts.add(a);
            }
        }


        Intent intent = new Intent(ReportsActivity.this, SearchListProductsActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}