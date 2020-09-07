package org.si.simple_login.domain;

public class Car {

    private int id;
    private String car_brand;
    private String car_yom;
    private String description;

    public Car() {

    }

    public Car( String car_brand, String car_yom, String description) {
        this.car_brand = car_brand;
        this.car_yom = car_yom;
        this.description = description;
    }

    public Car(int id, String car_brand, String car_yom, String description) {
        this.id = id;
        this.car_brand = car_brand;
        this.car_yom = car_yom;
        this.description = description;
    }

    public Car(Car car) {
        this.car_brand = car.car_brand;
        this.car_yom = car.car_yom;
        this.description = car.description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_yom() {
        return car_yom;
    }

    public void setCar_yom(String car_yom) {
        this.car_yom = car_yom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
