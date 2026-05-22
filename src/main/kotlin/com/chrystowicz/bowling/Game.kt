package com.chrystowicz.bowling

class Game {
    private val frames = mutableListOf(Frame(1))

    fun roll(pins: Int) {
        require(!isFinished()) { "The game is already finished" }

        val frame = currentFrame()
        frame.roll(pins)

        if (frame.isFinished() && !frame.isLastFrame()) {
            frames.add(Frame(currentFrameNumber() + 1))
        }
    }

    fun currentScore(): Int = frames.sumOf { frame ->
        val next = getFrame(frame.frameNumber)
        when {
            next == null -> frame.totalScore(0, 0)
            next.hasStrike() && next.isLastFrame() -> frame.totalScore(10, next.secondRollScore())
            next.hasStrike() -> frame.totalScore(10, getFrame(frame.frameNumber + 1)?.firstRollScore() ?: 0)
            else -> frame.totalScore(next.firstRollScore(), next.secondRollScore())
        }
    }

    private fun getFrame(frameNumber: Int): Frame? = frames.getOrNull(frameNumber)

    fun currentFrameNumber(): Int = currentFrame().frameNumber

    fun isFinished(): Boolean = currentFrame().isLastFrame() && currentFrame().isFinished()

    private fun currentFrame(): Frame = frames.last()
}