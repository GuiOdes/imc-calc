package com.calc.imc.model

import com.calc.imc.model.dto.PersonDto
import com.calc.imc.model.enum.BMI
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_person")
class Person(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    @Column
    var name: String,

    @Column
    var height: Double,

    @Column
    var weight: Double,

    var bmi: BMI?
) {
    fun toDto(bmi: String): PersonDto {
        return PersonDto(this, bmi)
    }
}