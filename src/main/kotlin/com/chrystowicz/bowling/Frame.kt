package com.chrystowicz.bowling

class Frame(val frameNumber: Int,
            val previousFrame: Frame? = null) {
    private var firstRoll: Int? = null
    private var secondRoll: Int? = null
    private var thirdRoll: Int? = null


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
            previousFrame?.hasStrike() == true || previousFrame?.hasSpare() == true -> thirdRoll = pins
        }
    }

    fun score(): Int = (firstRoll ?: 0) + (secondRoll ?: 0)

    fun isFinished(): Boolean = when(frameNumber) {
        10 -> {
            if(previousFrame?.hasStrike() == true || previousFrame?.hasSpare() == true) {
                thirdRoll != null
            } else {
                secondRoll != null
            }
        }
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
}