package com.example.bikso;

public class ServiceHistory {

    private String sname, bid, services_done, services_selected, time_done, time_selected, amount, curr_status, notfound;
    private int status;

    int getStatus() {
        return status;
    }

    String getNotfound() {
        return notfound;
    }

    String getSname() {
        return sname;
    }

    public String getBid() {
        return bid;
    }

    public String getServices_done() {
        return services_done;
    }

    public String getServices_selected() {
        return services_selected;
    }

    String getTime_done() {
        return time_done;
    }

    String getTime_selected() {
        return time_selected;
    }

    String getAmount() {
        return amount;
    }

    String getCurr_status() {
        return curr_status;
    }
}
