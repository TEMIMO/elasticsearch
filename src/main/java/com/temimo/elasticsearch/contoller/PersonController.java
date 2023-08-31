package com.temimo.elasticsearch.contoller;

import com.temimo.elasticsearch.document.Person;
import com.temimo.elasticsearch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void savePerson(@RequestBody Person person) {
        personService.save(person);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable String id) {
        return personService.searchById(id);
    }
}
