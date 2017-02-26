package com.bams.android.examenspeechtotext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bams on 2/23/17.
 */

public class Product implements Serializable {
    public String name;
    public String status;
    public Date date;

    public Product(String name, String status, Date date) {
        this.name = name;
        this.status = status;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        SimpleDateFormat ft = new SimpleDateFormat("d-M-yyyy H:mm:ss");
        return ft.format(date);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
