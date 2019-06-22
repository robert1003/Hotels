package com.example.android.hotels.activities;

import java.io.Serializable;

public class SearchResult implements Comparable<SearchResult>, Serializable {
    private int star, price, hotel_id;

    public SearchResult(int star, int price, int hotel_id) {
        this.star = star;
        this.price = price;
        this.hotel_id = hotel_id;
    }

    public int getStar() {
        return star;
    }

    public int getPrice() {
        return price;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    @Override
    public int compareTo(SearchResult o) {
        if (star > o.star) return -1;
        else if (star == o.star) {
            if (price < o.price) return -1;
            else if(price == o.price) return 0;
            else return 1;
        }
        else return 1;
    }

    @Override
    public String toString() {
        return star + " " + price;
    }
}
