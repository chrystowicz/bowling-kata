package com.chrystowicz.bowling

class Game {
    private val frames = mutableListOf(Frame())

    fun roll(knockedDownPins: Int) {

        if(isFinished()) {
            throw IllegalArgumentException("The game is already finished")
        }

        val currentFrame = frames.last()
        currentFrame.roll(knockedDownPins)

        if(currentFrame.isFinished() && currentFrame() < 10) {
            frames.add(Frame())
        }
    }

    fun currentScore(): Int = frames.sumOf(Frame::score)
    fun currentFrame(): Int = frames.size

    fun isFinished() : Boolean = currentFrame() == 10 && frames.last().isFinished()
}