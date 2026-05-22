package com.chrystowicz.bowling

class Frame private constructor(
    private val frameNumber: Int = 1
) {

    companion object {
        fun firstFrame() : Frame = Frame()
    }

     private var firstRoll: Int? = null
     private var secondRoll: Int? = null
     private var thirdRoll: Int? = null

    fun roll(knockedDownPins: Int) {
        require(knockedDownPins <= 10) { "Knocked down pins can't be higher than 10" }
        require(knockedDownPins >= 0) { "Knocked down pins can't be lower than 0" }
        val first = firstRoll
        when {
            first == null -> firstRoll = knockedDownPins
            secondRoll == null && frameNumber != 10 -> {
                require(first + knockedDownPins <= 10) { "Total number of rolls in a frame can't be higher than 10" }
                secondRoll = knockedDownPins
            }
            secondRoll == null -> secondRoll = knockedDownPins
            bonusRollInLastFrameAllowed() -> thirdRoll = knockedDownPins
        }
    }

    fun basicScore(): Int = (firstRoll ?: 0) + (secondRoll ?: 0) + (thirdRoll ?: 0)

    fun isFinished(): Boolean = when {
        isLastFrame() && bonusRollInLastFrameAllowed() -> thirdRoll != null
        isLastFrame() -> secondRoll != null
        else -> hasStrike() || secondRoll != null
    }

    fun hasSpare(): Boolean = basicScore() == 10 && !hasStrike()

    fun hasStrike(): Boolean = firstRoll == 10

    fun bonusScore(nextFirstRoll: Int? = null, nextSecondRoll: Int? = null): Int = when {
        hasSpare() -> nextFirstRoll ?: 0
        hasStrike() -> (nextFirstRoll ?: 0) + (nextSecondRoll ?: 0)
        else -> 0
    }

    fun firstRollScore() : Int? = firstRoll
    fun secondRollScore() : Int? = secondRoll

    fun isLastFrame(): Boolean = frameNumber == 10

    fun totalScoreOfFrame(nextFirstRoll: Int? = null, nextSecondRoll: Int? = null): Int =
        basicScore() + bonusScore(nextFirstRoll, nextSecondRoll)

    private fun bonusRollInLastFrameAllowed(): Boolean = hasStrike() || hasSpare()

    fun createNextFrame(): Frame = Frame(frameNumber+1)
}