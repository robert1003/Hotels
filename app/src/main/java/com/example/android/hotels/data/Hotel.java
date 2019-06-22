package com.example.android.hotels.data;

public class Hotel {
    // variables for hotels
    public int hotelID;
    public int hotelStar;
    public String locality;
    public String streetAddress;
    public int singlePrice;
    public int singleCount;
    public int dualPrice;
    public int dualCount;
    public int quadPrice;
    public int quadCount;

    // construct a new hotel
    public Hotel(int hotelID, int hotelStar, String locality, String streetAddress, int singlePrice,
                 int singleCount, int dualPrice, int dualCount, int quadPrice, int quadCount) {
        this.hotelID = hotelID;
        this.hotelStar = hotelStar;
        this.locality = locality;
        this.streetAddress = streetAddress;
        this.singlePrice = singlePrice;
        this.singleCount = singleCount;
        this.dualPrice = dualPrice;
        this.dualCount = dualCount;
        this.quadPrice = quadPrice;
        this.quadCount = quadCount;
    }

    // checking function of data
    @Override
    public String toString() {
        return "" + this.hotelID + " " + this.hotelStar + " " + this.locality + " " +
                this.streetAddress + " " + this.singlePrice + " " + this.singleCount + " " +
                this.dualPrice + " " + this.dualCount + " " + this.quadPrice + " " +
                this.quadCount;
    }
}
