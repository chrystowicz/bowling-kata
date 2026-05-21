package com.chrystowicz.bowling

class Game {
    private val frames = mutableListOf(Frame(1))

    fun roll(knockedDownPins: Int) {
        require(!isFinished()) { "The game is already finished" }

        val frame = currentFrame()
        val previousFrame = previousFrame()
        frame.roll(knockedDownPins, previousFrame)

        if (frame.isFinished() && currentFrameNumber() < 10) {
            frames.add(Frame(currentFrameNumber() + 1))
        }
    }

    private fun previousFrame() : Frame? {
        if(frames.size > 1) {
            return frames[frames.size - 1]
        }

        return null
    }

    fun currentScore(): Int = frames.sumOf(Frame::score)
    fun currentFrameNumber(): Int = frames.size

    fun isFinished(): Boolean = currentFrameNumber() == 10 && currentFrame().isFinished()

    private fun currentFrame(): Frame = frames.last()
}