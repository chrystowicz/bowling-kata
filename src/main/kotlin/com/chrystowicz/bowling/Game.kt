package com.chrystowicz.bowling

class Game {
    private val rolls = mutableListOf<Frame>().apply {
        add(Frame())
    }

    fun roll(knockedDownPins: Int) {
        val currentFrame = rolls.last()

        currentFrame.roll(knockedDownPins)
    }

    fun currentScore(): Int = rolls.sumOf {
        it.score()
    }
    fun currentFrame(): Int {
        return rolls.size
    }
}