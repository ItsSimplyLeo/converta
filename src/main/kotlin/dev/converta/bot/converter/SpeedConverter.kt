package dev.converta.bot.converter

object SpeedConverter {
    private val toMetersPerSecond = mapOf(
        "m/s" to 1.0,               // Meters per second
        "km/h" to 1000.0 / 3600,    // Kilometers per hour
        "mi/h" to 1609.34 / 3600,   // Miles per hour
        "ft/s" to 0.3048,           // Feet per second
        "knots" to 1852.0 / 3600    // Knots
    )

    fun convert(value: Double, from: String, to: String): Double? {
        val f = from.trim().lowercase()
        val t = to.trim().lowercase()
        val fromFactor = toMetersPerSecond[f] ?: return null
        val toFactor = toMetersPerSecond[t] ?: return null
        val valueInMetersPerSecond = value * fromFactor
        return valueInMetersPerSecond / toFactor
    }
}