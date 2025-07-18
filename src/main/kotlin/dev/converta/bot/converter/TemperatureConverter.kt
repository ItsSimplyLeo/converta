package dev.converta.bot.converter

import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.Command.Choice

object TemperatureConverter : Converter {

    private val choiceUnits = listOf(
        Choice("Celsius (°C)", "c"),
        Choice("Fahrenheit (°F)", "f"),
        Choice("Kelvin (K)", "k")
    )

    override fun name(): String {
        return "Temperature"
    }

    override fun convert(value: Double, from: String, to: String): Double? {
        if (from == to) return value

        return when (from to to) {
            "c" to "f" -> celsiusToFahrenheit(value)
            "c" to "k" -> celsiusToKelvin(value)
            "f" to "c" -> fahrenheitToCelsius(value)
            "f" to "k" -> fahrenheitToKelvin(value)
            "k" to "c" -> kelvinToCelsius(value)
            "k" to "f" -> kelvinToFahrenheit(value)
            else -> null
        }

    }

    override fun getChoices(): List<Command.Choice> {
        return choiceUnits
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