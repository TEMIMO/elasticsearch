package com.temimo.elasticsearch.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temimo.elasticsearch.document.Book;
import com.temimo.elasticsearch.document.Car;
import com.temimo.elasticsearch.dto.BookPaginationRequest;
import com.temimo.elasticsearch.dto.CarDatesQuery;
import com.temimo.elasticsearch.dto.CarModelQuery;
import com.temimo.elasticsearch.service.BookService;
import com.temimo.elasticsearch.service.CarService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CarController(CarService carService, ElasticsearchTemplate elasticsearchTemplate, ObjectMapper om) {
        this.carService = carService;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.objectMapper = om;
    }

    @Value("classpath:static/es-car-settings.json")
    private Resource carSettings;

    @Value("classpath:static/es-car-mapping.json")
    private Resource carMapping;

    @PostMapping
    public void saveCar(@RequestBody Car car) {
        carService.save(car);
    }

    @PostMapping("/findById")
    public Car findById(@RequestBody Car car) {
        String id = car.getId();
        return carService.searchById(id);
    }

    @GetMapping("/findAll")
    public List<Car> findAll() {
        return carService.findAll();
    }

    @GetMapping("/delete")
    public void deleteIndex() {
        elasticsearchTemplate.indexOps(IndexCoordinates.of("car")).delete();
    }

    @GetMapping("/create")
    public void createIndex() throws IOException {
        String indexName = "car";
        String settings = IOUtils.toString(carSettings.getInputStream(), StandardCharsets.UTF_8);
        String mappings = IOUtils.toString(carMapping.getInputStream(), StandardCharsets.UTF_8);
        Document parsed = Document.parse(mappings);
        elasticsearchTemplate
                .indexOps(IndexCoordinates.of(indexName))
                .create(objectMapper.readValue(settings, Map.class), parsed);
    }

    @GetMapping("/findBetweenTwoDates")
    public List<Car> findBetweenTwoDates(@RequestBody CarDatesQuery query) {
        Date dateFrom = query.getDateFrom();
        Date dateTo = query.getDateTo();
        return carService.findBetweenTwoDates(dateFrom, dateTo);
    }

    @PostMapping("/findByModel")
    public List<Car>  findByModel(@RequestBody CarModelQuery model) {
        String carModel = model.getModel();
        return carService.findByModel(carModel);
    }
}
