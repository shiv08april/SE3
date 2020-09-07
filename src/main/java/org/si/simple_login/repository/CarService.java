package org.si.simple_login.repository;

import org.si.simple_login.domain.Car;
import org.si.simple_login.repository.impl.CarRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*public class CarService {
}
package com.vaadin.tutorial.crm.backend.service;

        import com.vaadin.tutorial.crm.backend.entity.Company;
        import com.vaadin.tutorial.crm.backend.entity.Contact;
        import com.vaadin.tutorial.crm.backend.repository.CompanyRepository;
        import com.vaadin.tutorial.crm.backend.repository.ContactRepository;
        import org.springframework.stereotype.Service;

        import javax.annotation.PostConstruct;
        import java.util.List;
        import java.util.Random;
        import java.util.logging.Level;
        import java.util.logging.Logger;
        import java.util.stream.Collectors;
        import java.util.stream.Stream;*/

@Service
public class CarService {
    private static final Logger LOGGER = Logger.getLogger(CarService.class.getName());
    private CarRepository carRepository;
    //private CompanyRepository companyRepository;

    /*public ContactService(ContactRepository contactRepository,
                          CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }*/

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<Car> findAll() {

        return carRepository.findAll();
    }

    public List<Car> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return carRepository.findAll();
        } else {
            return carRepository.search(stringFilter);
        }
    }

    public long count() {
        return carRepository.count();
    }

    /*public void delete(Car car) {
        carRepository.delete(car);
    }

    public void save(Car car) {
        if (car == null) {
            LOGGER.log(Level.SEVERE,
                    "Car is null. Are you sure you have connected your form to the application?");
            return;
        }
        carRepository.save(car);
    }*/

    @PostConstruct
    public void populateTestData() {
        if (carRepository.count() == 0) {
            carRepository.saveAll(
                    Stream.of(new Car("Audi", "2006", "perfect for passionate for Audi"),
                            new Car("ford", "2008", "perfect for passionate for Ford"),
                            new Car("Maruti", "2010", "perfect for passionate for Maruti"))
                            //.map(Car::new)
                            .map(car-> {
                                return car;
                            })
                            .collect(Collectors.toList()));
        }

        /*if (contactRepository.count() == 0) {
            Random r = new Random(0);
            List<Car> companies = companyRepository.findAll();
            contactRepository.saveAll(
                    Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                            "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                            "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                            "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                            "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                            "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                            "Jaydan Jackson", "Bernard Nilsen")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Contact contact = new Contact();
                                contact.setFirstName(split[0]);
                                contact.setLastName(split[1]);
                                contact.setCompany(companies.get(r.nextInt(companies.size())));
                                contact.setStatus(Contact.Status.values()[r.nextInt(Contact.Status.values().length)]);
                                String email = (contact.getFirstName() + "." + contact.getLastName() + "@" + contact.getCompany().getName().replaceAll("[\\s-]", "") + ".com").toLowerCase();
                                contact.setEmail(email);
                                return contact;
                            }).collect(Collectors.toList()));
        }*/
    }
}
