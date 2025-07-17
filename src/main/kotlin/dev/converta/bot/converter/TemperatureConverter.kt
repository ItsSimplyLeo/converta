package dev.converta.bot.converter

object TemperatureConverter {

    private val unitAliases = mapOf(
        "c" to "c",
        "celsius" to "c",
        "f" to "f",
        "fahrenheit" to "f",
        "k" to "k",
        "kelvin" to "k"
    )

    fun convert(value: Double, from: String, to: String): Double? {
        val normalizedFrom = unitAliases[from.lowercase()] ?: return null
        val normalizedTo = unitAliases[to.lowercase()] ?: return null

        if (normalizedFrom == normalizedTo) return value

        return when (normalizedFrom to normalizedTo) {
            "c" to "f" -> celsiusToFahrenheit(value)
            "c" to "k" -> celsiusToKelvin(value)
            "f" to "c" -> fahrenheitToCelsius(value)
            "f" to "k" -> fahrenheitToKelvin(value)
            "k" to "c" -> kelvinToCelsius(value)
            "k" to "f" -> kelvinToFahrenheit(value)
            else -> null
        }
    }

    private fun celsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }

    private fun fahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }

    private fun celsiusToKelvin(celsius: Double): Double {
        return celsius + 273.15
    }

    private fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }

    private fun fahrenheitToKelvin(fahrenheit: Double): Double {
        return celsiusToKelvin(fahrenheitToCelsius(fahrenheit))
    }

    private fun kelvinToFahrenheit(kelvin: Double): Double {
        return celsiusToFahrenheit(kelvinToCelsius(kelvin))
    }
}
