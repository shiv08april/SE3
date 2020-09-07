package org.si.simple_login.repository.impl;

import com.vaadin.server.VaadinSession;
import org.si.simple_login.domain.Car;
import org.si.simple_login.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {

    private final JdbcTemplate jdbcTemplate;

    public CarRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Car> findAll() {
        List<Car> cars = jdbcTemplate.query(
                "SELECT * FROM car_details",
                new String[]{},
                (rs, rowNum) -> new Car(rs.getInt("id"),rs.getString("car_brand"),
                        rs.getString("car_yom"), rs.getString("description"))
        );

        return cars;
    }

    public long count() {
        return findAll().size();
    }

    public void saveAll(List<Car> cars) {
        System.out.println(cars.size());
        for(Car car: cars){
            System.out.println(car.getCar_brand()+" "+car.getCar_yom()+" "+car.getDescription());
            jdbcTemplate.update(

                    "INSERT INTO car_details(car_brand, car_yom, description) VALUES(?, ?, ?)",
                    car.getCar_brand(), car.getCar_yom(), car.getDescription()
            );
        }
    }

    public List<Car> search(String stringFilter) {
        System.out.println(stringFilter);
        List<Car> cars = jdbcTemplate.query(
                "SELECT * FROM car_details c " +
                        "where lower(c.car_brand) like lower(concat('%', ?, '%')) or " +
                        "lower(c.description) like lower(concat('%', ?, '%')) or " +
                        "lower(c.car_yom) like lower(concat('%', ?, '%'))",
                new String[]{stringFilter, stringFilter, stringFilter},
                (rs, rowNum) -> new Car(rs.getInt("id"),rs.getString("car_brand"),
                        rs.getString("car_yom"), rs.getString("description"))
        );

        return cars;/*
        @Query("select c from Contact c " +
                "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
                "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))") //


        List<Contact> search(@Param("searchTerm") String searchTerm); //*/
    }
}
