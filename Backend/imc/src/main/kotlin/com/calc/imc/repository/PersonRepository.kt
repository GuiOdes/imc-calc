package com.calc.imc.repository

import com.calc.imc.model.Person
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<Person, Long> {
    fun findAll(pageRequest: Pageable): List<Person>
    override fun findAll(): List<Person>
}
