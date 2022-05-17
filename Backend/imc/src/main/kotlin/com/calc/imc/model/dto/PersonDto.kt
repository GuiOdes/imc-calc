package com.calc.imc.model.dto

import com.calc.imc.model.Person

class PersonDto (
    val name: String,
    val weight: Double,
    val height: Double,
    val bmi: String,
    val id: Long?
){

    constructor(person: Person, bmi: String) : this(
        name = person.name,
        weight = person.weight,
        height = person.height,
        bmi = bmi,
        id = person.id
    )
}