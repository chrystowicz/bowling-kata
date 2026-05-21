package com.chrystowicz.bowling

class Frame {
    private var firstRollKnockedPins: Int? = null
    private var secondRollKnockedPins: Int? = null

    fun roll(knockedDownPins: Int) {
        require(knockedDownPins <= 10) { "Knocked down pins can't be higher than 10" }
        require(knockedDownPins >= 0) { "Knocked down pins can't be lower than 0" }
        if (firstRollKnockedPins == null) {
            firstRollKnockedPins = knockedDownPins
        } else {
            secondRollKnockedPins = knockedDownPins
        }
    }

    fun score(): Int = (firstRollKnockedPins ?: 0) + (secondRollKnockedPins ?: 0)

    fun isFinished(): Boolean = firstRollKnockedPins != null && secondRollKnockedPins != null

    fun hasSpare(): Boolean {
        return score() == 10 && !hasStrike()
    }

    fun hasStrike(): Boolean{
        return firstRollKnockedPins == 10
    }

}
