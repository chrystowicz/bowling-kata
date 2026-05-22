package com.chrystowicz.bowling

class Game {
    private val frames = mutableListOf(Frame(1))

    fun roll(pins: Int) {
        require(!isFinished()) { "The game is already finished" }

        val frame = currentFrame()
        frame.roll(pins)

        if (frame.isFinished() && currentFrameNumber() < 10) {
            frames.add(Frame(currentFrameNumber() + 1))
        }
    }

    fun currentScore(): Int = frames.sumOf { frame ->
        val next = nextFrame(frame.frameNumber)
        when {
            next == null -> frame.totalScore(0, 0)
            next.hasStrike() && next.frameNumber == 10 -> frame.totalScore(10, next.secondRoll)
            next.hasStrike() -> frame.totalScore(10, nextFrame(frame.frameNumber + 1)?.firstRoll ?: 0)
            else -> frame.totalScore(next.firstRoll, next.secondRoll)
        }
    }

    private fun nextFrame(frameNumber: Int): Frame? = frames.getOrNull(frameNumber)

    fun currentFrameNumber(): Int = currentFrame().frameNumber

    fun isFinished(): Boolean = currentFrameNumber() == 10 && currentFrame().isFinished()

    private fun currentFrame(): Frame = frames.last()
}