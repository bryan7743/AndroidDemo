package com.example.demo.data;

public class Data {

    private String time;

    private String price;

    private String number;

    private boolean sell;

    public Data(String time, String price, String number, boolean sell) {
        this.time = time;
        this.price = price;
        this.number = number;
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "Data{" +
                "time='" + time + '\'' +
                ", price='" + price + '\'' +
                ", number='" + number + '\'' +
                ", sell=" + sell +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }
}
