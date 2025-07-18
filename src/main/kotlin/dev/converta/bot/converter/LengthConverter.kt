package dev.converta.bot.converter

import net.dv8tion.jda.api.interactions.commands.Command.Choice

object LengthConverter : Converter {

    private val choiceUnits = listOf(
        Choice("Meters (m)", "m"),
        Choice("Kilometers (km)", "km"),
        Choice("Miles (mi)", "mi"),
        Choice("Feet (ft)", "ft"),
        Choice("Inches (in)", "in"),
        Choice("Centimeters (cm)", "cm"),
        Choice("Millimeters (mm)", "mm")
    )

    private val toMeters = mapOf(
        "m" to 1.0, // original meter unit
        "km" to 1000.0, // kilometers to meters
        "cm" to 0.01, // centimeters to meters
        "mm" to 0.001, // millimeters to meters
        "mi" to 1609.344, // miles to meters
        "ft" to 0.3048, // feet to meters
        "in" to 0.0254 // inches to meters
    )

    override fun name(): String {
        return "Length"
    }

    override fun convert(value: Double, from: String, to: String): Double? {
        val fromFactor = toMeters[from.lowercase()]
        val toFactor = toMeters[to.lowercase()]

        if (fromFactor == null || toFactor == null) return null

        val valueInMeters = value * fromFactor
        return valueInMeters / toFactor
    }

    override fun getChoices(): List<Choice> {
        return choiceUnits
    }
}