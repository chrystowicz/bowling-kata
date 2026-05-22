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
            val frame = Frame.firstFrame()

            assertThatThrownBy { frame.roll(11) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Knocked down pins can't be higher than 10")
        }

        @Test
        fun `knocked down pins can't be lower than 0`() {
            val frame = Frame.firstFrame()

            assertThatThrownBy { frame.roll(-1) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Knocked down pins can't be lower than 0")
        }

        @Test
        fun `sum of two rolls can't be bigger than 10`() {
            val frame = Frame.firstFrame()

            frame.roll(5)

            assertThatThrownBy { frame.roll(6) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Total number of rolls in a frame can't be higher than 10")
        }

    }

    @Test
    fun `score is the sum of both rolls`() {
        val frame = Frame.firstFrame()

        frame.roll(5)
        frame.roll(5)

        assertThat(frame.basicScore()).isEqualTo(10)
    }

    @Nested
    @DisplayName("finishing frame")
    inner class Finished {

        @Test
        fun `empty frame is not finished`() {
            val frame = Frame.firstFrame()

            assertThat(frame.isFinished()).isFalse()
        }

        @Test
        fun `frame 1 to 9 is finished after two rolls`() {
            val frame = Frame.firstFrame()

            frame.roll(5)
            frame.roll(5)

            assertThat(frame.isFinished()).isTrue()
        }

        @Test
        fun `frame 10 can have 3 rolls if first roll has strike`() {

            val frame = createLastFrame()

            frame.roll(10)
            frame.roll(5)
            frame.roll(4)

            assertThat(frame.isFinished()).isTrue()
        }

        private fun createLastFrame() : Frame {
            var frame: Frame = Frame.firstFrame()
            repeat(9) {
                frame = frame.createNextFrame()
            }

            return frame
        }

        @Test
        fun `frame 10 can have 3 rolls if both rolls have spare`() {

            val frame = createLastFrame()

            frame.roll(5)
            frame.roll(5)
            frame.roll(6)

            assertThat(frame.isFinished()).isTrue()
        }

    }

    @Test
    fun `spare is when two rolls in a single frame sum to 10`() {
        val frame = Frame.firstFrame()

        frame.roll(5)
        frame.roll(5)

        assertThat(frame.hasSpare()).isTrue()
    }

    @Test
    fun `strike is not a spare`() {
        val frame = Frame.firstFrame()

        frame.roll(10)

        assertThat(frame.hasSpare()).isFalse()
    }

    @Test
    fun `strike is when 10 pins are knocked down in first roll`() {
        val frame = Frame.firstFrame()

        frame.roll(10)

        assertThat(frame.hasStrike()).isTrue()
    }
}