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

    fun currentScore(): Int = frames.sumOf {
        val frame = it

        val nextFrame = nextFrame(frame.frameNumber)

        var score = 0
        if(nextFrame != null) {
            if(nextFrame.hasStrike()) {
                if(nextFrame.frameNumber == 10) {
                    score = frame.totalScore(10, nextFrame.secondRoll)
                } else {
                    val nextTwoFrame = nextFrame(frame.frameNumber+1)
                    score = frame.totalScore(10, nextTwoFrame?.firstRoll ?: 0)
                }
            } else {
                score = frame.totalScore(nextFrame.firstRoll, nextFrame.secondRoll)
            }
        } else {
             score = frame.totalScore(0, 0)
        }

        score
    }

    private fun nextFrame(currentFrameNumber: Int) : Frame? {
        if (currentFrameNumber < frames.size) {
            return frames[currentFrameNumber]
        }
        else {
            return null
        }
    }

    fun currentFrameNumber(): Int = currentFrame().frameNumber

    fun isFinished(): Boolean = currentFrameNumber() == 10 && currentFrame().isFinished()

    private fun currentFrame(): Frame = frames.last()
}