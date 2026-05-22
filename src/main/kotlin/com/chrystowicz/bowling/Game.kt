package com.chrystowicz.bowling

class Game {
    private val frames = mutableListOf(Frame.firstFrame())

    fun roll(knockedDownPins: Int) {
        require(!isFinished()) { "The game is already finished" }

        val frame = currentFrame()
        frame.roll(knockedDownPins)

        if (frame.isFinished() && !frame.isLastFrame()) {
            frames.add(frame.createNextFrame())
        }
    }

    fun currentScore(): Int = frames.withIndex().sumOf { (index, frame) ->
        frame.totalScoreOfFrame(
            nextFrame = frames.getOrNull(index + 1),
            nextNextFrame = frames.getOrNull(index + 2)
        )
    }

    fun isFinished(): Boolean = currentFrame().isLastFrame() && currentFrame().isFinished()

    private fun currentFrame(): Frame = frames.last()

    fun currentFrameNumber(): Int = frames.size
}