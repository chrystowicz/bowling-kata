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

    fun currentScore(): Int = frames.withIndex().sumOf { (index,frame) ->
        val nextFrame = getFrame(index + 1)
        when {
            nextFrame == null -> frame.totalScoreOfFrame(0, 0)
            nextFrame.hasStrike() && nextFrame.isLastFrame() -> frame.totalScoreOfFrame(10, nextFrame.secondRollScore())
            nextFrame.hasStrike() -> {
                val nextSecondFrame = getFrame(index + 2)
                frame.totalScoreOfFrame(10, nextSecondFrame?.firstRollScore() ?: 0)
            }
            else -> frame.totalScoreOfFrame(nextFrame.firstRollScore(), nextFrame.secondRollScore())
        }
    }

    private fun getFrame(frameNumber: Int): Frame? = frames.getOrNull(frameNumber)

    fun isFinished(): Boolean = currentFrame().isLastFrame() && currentFrame().isFinished()

    private fun currentFrame(): Frame = frames.last()

    fun currentFrameNumber() = frames.size
}