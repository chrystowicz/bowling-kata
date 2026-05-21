package com.chrystowicz.bowling

import org.assertj.core.api.Assertions
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

            Assertions.assertThatThrownBy { frame.roll(11) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Knocked down pins can't be higher than 10")
        }

        @Test
        fun `knocked down pins can't be lower than 0`() {
            val frame = Frame()

            Assertions.assertThatThrownBy { frame.roll(-1) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Knocked down pins can't be lower than 0")

        }

    }

    @Test
    fun `multiple rolls can be recorded in sequence, up to two per frame`() {
        val frame = Frame()

        frame.roll(5)
        frame.roll(5)

        Assertions.assertThat(frame.score()).isEqualTo(10)
    }

}