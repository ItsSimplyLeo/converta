package dev.converta.bot.converter

object LengthConverter {

    private val toMeters = mapOf(
        "m" to 1.0,
        "meter" to 1.0,
        "meters" to 1.0,

        "km" to 1000.0,
        "kilometer" to 1000.0,
        "kilometers" to 1000.0,

        "cm" to 0.01,
        "centimeter" to 0.01,
        "centimeters" to 0.01,

        "mm" to 0.001,
        "millimeter" to 0.001,
        "millimeters" to 0.001,

        "mi" to 1609.344,
        "mile" to 1609.344,
        "miles" to 1609.344,

        "ft" to 0.3048,
        "foot" to 0.3048,
        "feet" to 0.3048,

        "in" to 0.0254,
        "inch" to 0.0254,
        "inches" to 0.0254
    )

    fun convert(value: Double, from: String, to: String): Double? {
        val fromFactor = toMeters[from.lowercase()]
        val toFactor = toMeters[to.lowercase()]

        if (fromFactor == null || toFactor == null) return null

        val valueInMeters = value * fromFactor
        return valueInMeters / toFactor
    }
}