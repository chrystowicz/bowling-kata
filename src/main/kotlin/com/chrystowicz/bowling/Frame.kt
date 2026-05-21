package com.chrystowicz.bowling

data class Frame(
    private var firstRollKnockedPins: Int? = null,
    private var secondRollKnockedPins: Int? = null,
) {

    fun roll(knockedDownPins: Int) {
        require(knockedDownPins <= 10) { "Knocked down pins can't be higher than 10" }
        require(knockedDownPins >= 0) { "Knocked down pins can't be lower than 0" }
        if (firstRollKnockedPins == null) {
            firstRollKnockedPins = knockedDownPins
        }
        else {
            secondRollKnockedPins = knockedDownPins
        }
    }

    fun score(): Int = (firstRollKnockedPins ?: 0) + (secondRollKnockedPins ?: 0)

}
