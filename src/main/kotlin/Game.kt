package com.chrystowicz.bowling

class Game {
    private val rolls = mutableListOf<Int>()

    fun roll(knockedDownPins: Int) {
        require(knockedDownPins <= 10) { "Knocked down pins can't be higher than 10" }
        require(knockedDownPins >= 0) { "Knocked down pins can't be lower than 0" }
        rolls.add(knockedDownPins)
    }

    fun currentScore(): Int = rolls.sum()
}