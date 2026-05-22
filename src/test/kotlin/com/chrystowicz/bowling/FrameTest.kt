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
            val frame = Frame(1)

            assertThatThrownBy { frame.roll(11) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Knocked down pins can't be higher than 10")
        }

        @Test
        fun `knocked down pins can't be lower than 0`() {
            val frame = Frame(1)

            assertThatThrownBy { frame.roll(-1) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Knocked down pins can't be lower than 0")
        }

        @Test
        fun `sum of two rolls can't be bigger than 10`() {
            val frame = Frame(1)

            frame.roll(5)

            assertThatThrownBy { frame.roll(6) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Total number of rolls in a frame can't be higher than 10")
        }

    }

    @Test
    fun `score is the sum of both rolls`() {
        val frame = Frame(1)

        frame.roll(5)
        frame.roll(5)

        assertThat(frame.score()).isEqualTo(10)
    }

    @Nested
    @DisplayName("finishing frame")
    inner class Finished {

        @Test
        fun `empty frame is not finished`() {
            val frame = Frame(1)

            assertThat(frame.isFinished()).isFalse()
        }

        @Test
        fun `frame 1 to 9 is finished after two rolls`() {
            val frame = Frame(1)

            frame.roll(5)
            frame.roll(5)

            assertThat(frame.isFinished()).isTrue()
        }

        @Test
        fun `frame 10 can have 3 rolls if first roll has strike`() {

            val frame = Frame(10)

            frame.roll(10)
            frame.roll(5)
            frame.roll(4)

            assertThat(frame.isFinished()).isTrue()
        }

        @Test
        fun `frame 10 can have 3 rolls if both rolls have spare`() {

            val frame = Frame(10)

            frame.roll(5)
            frame.roll(5)
            frame.roll(6)

            assertThat(frame.isFinished()).isTrue()
        }

    }

    @Test
    fun `spare is when two rolls in a single frame sum to 10`() {
        val frame = Frame(1)

        frame.roll(5)
        frame.roll(5)

        assertThat(frame.hasSpare()).isTrue()
    }

    @Test
    fun `frame number should be between 1 and 10`() {
        assertThatThrownBy { Frame(-1) }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Frame number should be between 1 and 10")

        assertThatThrownBy { Frame(11) }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Frame number should be between 1 and 10")
    }

    @Test
    fun `strike is not a spare`() {
        val frame = Frame(1)

        frame.roll(10)

        assertThat(frame.hasSpare()).isFalse()
    }

    @Test
    fun `strike is when 10 pins are knocked down in first roll`() {
        val frame = Frame(1)

        frame.roll(10)

        assertThat(frame.hasStrike()).isTrue()
    }

    @Nested
    @DisplayName("bonus score")
    inner class Bonus {

        @Test
        fun `bonus is by default zero`() {
            val frame = Frame(1)

            assertThat(frame.bonusScore(null)).isZero()
        }

        @Test
        fun `when current frame has a spare, bonus is next roll`() {
            val currentFrame = Frame(1)
            currentFrame.roll(5)
            currentFrame.roll(5)

            val nextRoll = 5

            assertThat(currentFrame.bonusScore(nextRoll)).isEqualTo(5)
        }

        @Test
        fun `total score includes spare bonus from next roll`() {
            val currentFrame = Frame(1)
            currentFrame.roll(5)
            currentFrame.roll(5)

            val nextRoll = 5

            assertThat(currentFrame.totalScore(nextRoll)).isEqualTo(15)
        }

        @Test
        fun `when current frame has strike, bonus is next two rolls`() {
            val currentFrame = Frame(1)
            currentFrame.roll(10)

            val nextFirstRoll = 5
            val nextSecondRoll = 7

            assertThat(currentFrame.bonusScore(nextFirstRoll, nextSecondRoll)).isEqualTo(12)
        }

    }
}