package com.calc.imc.service

import com.calc.imc.model.Person
import com.calc.imc.model.dto.PersonDto
import com.calc.imc.model.enum.BMI
import com.calc.imc.repository.PersonRepository
import org.apache.commons.math3.util.Precision
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import kotlin.math.pow

@Service
class PersonService (
    val repository: PersonRepository
) {

    fun create(person: Person, lang: String): ResponseEntity<PersonDto> {
        person.bmi = bmiClassify(bmiCalcNum(person))
        val personDto: PersonDto = repository.save(person).toDto(
            bmiDict( person.bmi!!, lang)
        )
        return ResponseEntity(personDto, HttpStatus.CREATED)
    }

    fun listAll(pageSize: Int, page: Int, lang: String): List<PersonDto> {
        val pageable = PageRequest.of(page, pageSize)
        return repository.findAll(pageable)
            .map { it.toDto(bmiDict(it.bmi!!, lang)) }
    }

    fun update(person: Person, lang: String): ResponseEntity<PersonDto> {
        return repository.findById(person.id!!)
            .map {
                it.height = person.height
                it.weight = person.weight
                it.name = person.name
                it.bmi = bmiClassify(bmiCalcNum(person))

                ResponseEntity(repository.save(it).toDto(bmiDict(it.bmi!!, lang)), HttpStatus.CREATED)
            }
            .orElse(
                ResponseEntity.notFound().build()
            )
    }

    fun deleteBy(id: Long): ResponseEntity<Unit> {
        return repository.findById(id)
            .map { ResponseEntity.ok(repository.delete(it)) }
            .orElse(ResponseEntity.notFound().build())
    }

    fun bmiClassify(bmi: Double): BMI {
        return when (bmi) {
            in 0.0..18.49 -> BMI.UNDERWEIGHT
            in 18.5..24.99 -> BMI.NORMAL
            in 25.0..29.99 -> BMI.OVERWEIGHT
            in 30.0..34.99 -> BMI.OBESE_ONE
            in 35.0..39.99 -> BMI.OBESE_TWO
            else -> BMI.OBESE_THREE
        }
    }

    fun bmiDict(bmi: BMI, lang: String): String {
        return when (lang) {
            "pt-br" -> bmi.toPortuguese()
            else -> bmi.toEnglish()
        }
    }

    fun bmiCalcNum(person: Person): Double {
        return (person.weight)/(person.height/100).pow(2)
    }

    fun bmiAvg(): Double {
        var sum = 0.0
        var countPerson = 0
        repository.findAll().map {
            sum += bmiCalcNum(it)
            countPerson++
        }

        return Precision.round((sum/countPerson),2)
    }

    fun findBy(id: Long, lang: String): ResponseEntity<PersonDto> {
        return repository.findById(id).
                map {
                    ResponseEntity.ok(
                        PersonDto(
                            person = it,
                            bmiDict(it.bmi!!, lang)
                        )
                    )
                }
            .orElse(ResponseEntity.notFound().build())
    }

    fun bmiAvgClassify(lang: String): String {
        return bmiDict(
            bmiClassify(bmiAvg()), lang
        )
    }
}