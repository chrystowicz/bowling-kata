package com.chrystowicz.bowling

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class FrameTest {

    @Nested
    @DisplayName("a roll can take from 0 to 10 pins")
    inner class Pins {

        @Test
        fun `knocked down pins can't be higher than 10`() {
            val frame = Frame()

            assertThatThrownBy { frame.roll(11) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Knocked down pins can't be higher than 10")
        }

        @Test
        fun `knocked down pins can't be lower than 0`() {
            val frame = Frame()

            assertThatThrownBy { frame.roll(-1) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Knocked down pins can't be lower than 0")
        }

        @Test
        fun `sum of two rolls can't be bigger than 10`() {
            val frame = Frame()

            frame.roll(5)

            assertThatThrownBy { frame.roll(6) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Total number of rolls in a frame can't be higher than 10")
        }

    }

    @Test
    fun `score is the sum of both rolls`() {
        val frame = Frame()

        frame.roll(5)
        frame.roll(5)

        assertThat(frame.score()).isEqualTo(10)
    }

    @Nested
    @DisplayName("finishing frame")
    inner class Finished {

        @Test
        fun `empty frame is not finished`() {
            val frame = Frame()

            assertThat(frame.isFinished()).isFalse()
        }

        @Test
        fun `frame is finished after two rolls`() {
            val frame = Frame()

            frame.roll(5)
            frame.roll(5)

            assertThat(frame.isFinished()).isTrue()
        }

    }

    @Test
    fun `spare is when two rolls in a single frame sum to 10`() {
        val frame = Frame()

        frame.roll(5)
        frame.roll(5)

        assertThat(frame.hasSpare()).isTrue()
    }

    @Test
    fun `strike is not a spare`() {
        val frame = Frame()

        frame.roll(10)

        assertThat(frame.hasSpare()).isFalse()
    }

    @Test
    fun `strike is when 10 pins are knocked down in first roll`() {
        val frame = Frame()

        frame.roll(10)

        assertThat(frame.hasStrike()).isTrue()
    }

    @Nested
    @DisplayName("bonus score")
    inner class Bonus {

        @Test
        fun `bonus is by default zero`() {
            val frame = Frame()

            assertThat(frame.bonusScore(null)).isZero()
        }

        @Test
        fun `when current frame has a spare, bonus is next roll`() {
            val currentFrame = Frame()
            currentFrame.roll(5)
            currentFrame.roll(5)

            val nextRoll = 5

            assertThat(currentFrame.bonusScore(nextRoll)).isEqualTo(5)
        }

        @Test
        fun `total score includes bonus and score`() {
            val currentFrame = Frame()
            currentFrame.roll(5)
            currentFrame.roll(5)

            val nextRoll = 5

            assertThat(currentFrame.totalScore(nextRoll)).isEqualTo(15)
        }

    }
}