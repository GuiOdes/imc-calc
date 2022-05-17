package com.calc.imc.controller

import com.calc.imc.model.Person
import com.calc.imc.model.dto.PersonDto
import com.calc.imc.model.enum.BMI
import com.calc.imc.service.PersonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController (
    val service: PersonService
) {
    @GetMapping("/avg")
    fun bmiAvg(@RequestParam lang: String): String {
        return service.bmiAvgClassify(lang)
    }

    @PostMapping("/create")
    fun create(@RequestBody person: Person, @RequestParam lang: String): ResponseEntity<PersonDto> {
        return service.create(person, lang)
    }

    @GetMapping("/list")
    fun listAll(@RequestParam pageSize: Int, @RequestParam page: Int, @RequestParam lang: String): List<PersonDto> {
        return service.listAll(pageSize, page, lang)
    }

    @GetMapping("/list/{id}")
    fun listBy(@PathVariable id: Long, @RequestParam lang: String): ResponseEntity<PersonDto> {
        return service.findBy(id, lang)
    }

    @PutMapping("/update")
    fun update(@RequestBody person: Person, @RequestParam lang: String): ResponseEntity<PersonDto> {
        return service.update(person, lang)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteBy(@PathVariable id: Long): ResponseEntity<Unit> {
        return service.deleteBy(id)
    }
}