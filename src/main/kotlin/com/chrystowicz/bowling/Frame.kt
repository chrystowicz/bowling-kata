package com.chrystowicz.bowling

class Frame {
    private var firstRoll: Int? = null
    private var secondRoll: Int? = null

    fun roll(pins: Int) {
        require(pins <= 10) { "Knocked down pins can't be higher than 10" }
        require(pins >= 0) { "Knocked down pins can't be lower than 0" }
        val first = firstRoll
        if (first == null) {
            firstRoll = pins
        } else {
            require(first + pins <= 10) { "Total number of rolls in a frame can't be higher than 10" }
            secondRoll = pins
        }
    }

    fun score(): Int = (firstRoll ?: 0) + (secondRoll ?: 0)

    fun isFinished(): Boolean = firstRoll != null && secondRoll != null

    fun hasSpare(): Boolean = score() == 10 && !hasStrike()

    fun hasStrike(): Boolean = firstRoll == 10
}