package com.chrystowicz.bowling

class Game {
    private val frames = mutableListOf(Frame(1))

    fun roll(knockedDownPins: Int) {
        require(!isFinished()) { "The game is already finished" }

        val currentFrame = currentFrame()
        currentFrame.roll(knockedDownPins)

        if (currentFrame.isFinished() && currentFrameNumber() < 10) {
            frames.add(Frame(currentFrameNumber() + 1, currentFrame))
        }
    }

    fun currentScore(): Int = frames.sumOf(Frame::score)
    fun currentFrameNumber(): Int = currentFrame().frameNumber

    fun isFinished(): Boolean = currentFrameNumber() == 10 && currentFrame().isFinished()

    private fun currentFrame(): Frame = frames.last()
}