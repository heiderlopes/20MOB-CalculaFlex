package br.com.calculaflex.domain.entity

data class Car(
    val vehicle: String = "",
    val kmGasolinePerLiter: Double = 0.0,
    val kmEthanolPerLiter: Double = 0.0,
    val priceGasolinePerLiter: Double = 0.0,
    val priceEthanolPerLiter: Double = 0.0,
    var userId: String = ""
)
