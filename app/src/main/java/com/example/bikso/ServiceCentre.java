package com.example.bikso;

import java.util.ArrayList;

class ServiceCentre {

    private String name, about, address, photo1, photo2, photo3, photo4, photo5, stars, location, authorisation;
    private ArrayList<Reviews> reviews;
    private String[] timeslots;
    private int sid;

    String[] getTimeslots() {
        return timeslots;
    }

    String getAbout() {
        return about;
    }

    String getAddress() {
        return address;
    }

    ArrayList<Reviews> getReviews() {
        return reviews;
    }

    String getName() {
        return name;
    }

    String getPhoto1() {
        return photo1;
    }

    String getStars() {
        return stars;
    }

    String getLocation() {
        return location;
    }

    String getAuthorisation() {
        return authorisation;
    }

    Integer getSid() {
        return sid;
    }

    String getPhoto2() {
        return photo2;
    }

    String getPhoto3() {
        return photo3;
    }

    String getPhoto4() {
        return photo4;
    }

    String getPhoto5() {
        return photo5;
    }
}
