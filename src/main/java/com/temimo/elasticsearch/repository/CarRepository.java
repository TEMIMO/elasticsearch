package com.temimo.elasticsearch.repository;

import com.temimo.elasticsearch.document.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Date;
import java.util.List;

public interface CarRepository extends ElasticsearchRepository<Car, String> {
    List<Car> findByCreationDateBetween(Date dateFrom, Date dateTo);

    List<Car> findByModel(String model);
}
