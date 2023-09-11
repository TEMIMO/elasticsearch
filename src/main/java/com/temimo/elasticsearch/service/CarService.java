package com.temimo.elasticsearch.service;

import com.temimo.elasticsearch.document.Car;
import com.temimo.elasticsearch.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;


    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void save(Car car) {
        carRepository.save(car);
    }

    public Car searchById(String id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        carRepository.findAll().forEach(cars::add);
        return cars;
    }

    public List<Car> findBetweenTwoDates(Date dateFrom, Date dateTo) {
        return carRepository.findByCreationDateBetween(dateFrom, dateTo);
    }

    public List<Car> findByModel(String model) {
        return carRepository.findByModel(model);
    }
}
