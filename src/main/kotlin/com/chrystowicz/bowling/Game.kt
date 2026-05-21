package com.chrystowicz.bowling

class Game {
    private val frames = mutableListOf(Frame())

    fun roll(knockedDownPins: Int) {
        require(!isFinished()) { "The game is already finished" }

        val frame = currentFrame()
        frame.roll(knockedDownPins)

        if (frame.isFinished() && currentFrameNumber() < 10) {
            frames.add(Frame())
        }
    }

    fun currentScore(): Int = frames.sumOf(Frame::score)
    fun currentFrameNumber(): Int = frames.size

    fun isFinished(): Boolean = currentFrameNumber() == 10 && currentFrame().isFinished()

    private fun currentFrame(): Frame = frames.last()
}