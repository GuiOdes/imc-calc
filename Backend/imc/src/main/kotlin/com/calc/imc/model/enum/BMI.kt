package com.calc.imc.model.enum

import com.calc.imc.model.langs.Languages

enum class BMI: Languages {
    UNDERWEIGHT {
        override fun toPortuguese(): String {
            return "Abaixo do Peso"
        }

        override fun toEnglish(): String {
            return "Underweight"
        }
    },
    NORMAL {
        override fun toPortuguese(): String {
            return "Dentro da Normalidade"
        }

        override fun toEnglish(): String {
            return "Normal"
        }
    },

    OVERWEIGHT {
        override fun toPortuguese(): String {
            return "Acima do Peso"
        }

        override fun toEnglish(): String {
            return "Overweight"
        }
    },

    OBESE_ONE {
        override fun toPortuguese(): String {
            return "Obesidade Grau I"
        }

        override fun toEnglish(): String {
            return "Severe Obesity"
        }
    },

    OBESE_TWO {
        override fun toPortuguese(): String {
            return "Obesidade Grau II"
        }

        override fun toEnglish(): String {
            return "Morbid Obesity"
        }
    },

    OBESE_THREE {
        override fun toPortuguese(): String {
            return "Obesidade Grau III"
        }

        override fun toEnglish(): String {
            return "Super Obesity"
        }
    }
}