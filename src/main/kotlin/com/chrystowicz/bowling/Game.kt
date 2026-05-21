package com.chrystowicz.bowling

class Game {
    private val rolls = mutableListOf(Frame())

    fun roll(knockedDownPins: Int) {
        val currentFrame = rolls.last()
        currentFrame.roll(knockedDownPins)

        if(currentFrame.isFinished()) {
            rolls.add(Frame())
        }
    }

    fun currentScore(): Int = rolls.sumOf(Frame::score)
    fun currentFrame(): Int {
        return rolls.size
    }
}