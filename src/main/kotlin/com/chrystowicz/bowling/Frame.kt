package com.chrystowicz.bowling

class Frame(val frameNumber: Int) {
    init {
        require(frameNumber in 1..10) { "Frame number should be between 1 and 10" }
    }
     var firstRoll: Int? = null
     var secondRoll: Int? = null
     var thirdRoll: Int? = null

    fun roll(pins: Int) {
        require(pins <= 10) { "Knocked down pins can't be higher than 10" }
        require(pins >= 0) { "Knocked down pins can't be lower than 0" }
        val first = firstRoll
        when {
            first == null -> firstRoll = pins
            secondRoll == null && frameNumber != 10 -> {
                require(first + pins <= 10) { "Total number of rolls in a frame can't be higher than 10" }
                secondRoll = pins
            }
            secondRoll == null -> secondRoll = pins
            bonusRollAllowed() -> thirdRoll = pins
        }
    }

    fun score(): Int = (firstRoll ?: 0) + (secondRoll ?: 0) + (thirdRoll ?: 0)

    fun isFinished(): Boolean = when {
        frameNumber == 10 && bonusRollAllowed() -> thirdRoll != null
        frameNumber == 10 -> secondRoll != null
        else -> hasStrike() || secondRoll != null
    }

    fun hasSpare(): Boolean = score() == 10 && !hasStrike()

    fun hasStrike(): Boolean = firstRoll == 10

    fun bonusScore(nextFirstRoll: Int? = null, nextSecondRoll: Int? = null): Int = when {
        hasSpare() -> nextFirstRoll ?: 0
        hasStrike() -> (nextFirstRoll ?: 0) + (nextSecondRoll ?: 0)
        else -> 0
    }

    fun totalScore(nextFirstRoll: Int? = null, nextSecondRoll: Int? = null): Int =
        score() + bonusScore(nextFirstRoll, nextSecondRoll)

    private fun bonusRollAllowed(): Boolean = hasStrike() || hasSpare()
}