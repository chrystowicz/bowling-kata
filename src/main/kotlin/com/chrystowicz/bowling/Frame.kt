package com.chrystowicz.bowling

class Frame(val frameNumber: Int) {
    private var firstRoll: Int? = null
    private var secondRoll: Int? = null
    private var thirdRoll: Int? = null

    fun roll(pins: Int, previousFrame: Frame? = null) {
        require(pins <= 10) { "Knocked down pins can't be higher than 10" }
        require(pins >= 0) { "Knocked down pins can't be lower than 0" }
        val first = firstRoll
        if (first == null) {
            firstRoll = pins
        } else {

            if (frameNumber != 10) {
                if (secondRoll == null) {
                    require(first + pins <= 10) { "Total number of rolls in a frame can't be higher than 10" }
                    secondRoll = pins
                }
            } else {
                if (secondRoll == null) {
                    secondRoll = pins
                } else {
                    if (previousFrame != null) {
                        if (previousFrame.hasStrike() || previousFrame.hasSpare()) {
                            thirdRoll = pins
                        }
                    }

                }
            }
        }

    }

    fun score(): Int = (firstRoll ?: 0) + (secondRoll ?: 0)

    fun isFinished(): Boolean = firstRoll != null && secondRoll != null

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