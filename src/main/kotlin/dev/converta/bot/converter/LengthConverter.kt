package dev.converta.bot.converter

object LengthConverter {

    private val toMeters = mapOf(
        "m" to 1.0, // original meter unit
        "km" to 1000.0, // kilometers to meters
        "cm" to 0.01, // centimeters to meters
        "mm" to 0.001, // millimeters to meters
        "mi" to 1609.344, // miles to meters
        "ft" to 0.3048, // feet to meters
        "in" to 0.0254 // inches to meters
    )

    fun convert(value: Double, from: String, to: String): Double? {
        val fromFactor = toMeters[from.lowercase()]
        val toFactor = toMeters[to.lowercase()]

        if (fromFactor == null || toFactor == null) return null

        val valueInMeters = value * fromFactor
        return valueInMeters / toFactor
    }
}