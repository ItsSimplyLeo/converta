package dev.converta.bot.converter

import net.dv8tion.jda.api.interactions.commands.Command.Choice

object SpeedConverter : Converter {

    private val choiceUnits = listOf(
        Choice("Miles per hour (mph)", "mph"),
        Choice("Kilometers per hour (kph)", "km/h"),
        Choice("Meters per second (m/s)", "m/s"),
        Choice("Feet per second (ft/s)", "ft/s"),
        Choice("Knots", "knots")
    )

    private val toMetersPerSecond = mapOf(
        "m/s" to 1.0,               // Meters per second
        "km/h" to 1000.0 / 3600,    // Kilometers per hour
        "mph" to 1609.34 / 3600,   // Miles per hour
        "ft/s" to 0.3048,           // Feet per second
        "knots" to 1852.0 / 3600    // Knots
    )

    override fun name(): String {
        return "Speed"
    }

    override fun convert(value: Double, from: String, to: String): Double? {
        val f = from.trim().lowercase()
        val t = to.trim().lowercase()
        val fromFactor = toMetersPerSecond[f] ?: return null
        val toFactor = toMetersPerSecond[t] ?: return null
        val valueInMetersPerSecond = value * fromFactor
        return valueInMetersPerSecond / toFactor
    }

    override fun getChoices(): List<Choice> {
        return choiceUnits
    }
}