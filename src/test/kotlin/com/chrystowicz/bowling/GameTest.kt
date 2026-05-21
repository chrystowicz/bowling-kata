package com.chrystowicz.bowling

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `rolling pins adds to the current score`() {
        val game = Game()

        game.roll(5)

        assertThat(game.currentScore()).isEqualTo(5)
    }

    @Test
    fun `game starts on frame 1`() {
        val game = Game()

        assertThat(game.currentFrameNumber()).isEqualTo(1)

        game.roll(5)
        game.roll(5)
    }

    @Test
    fun `completing two rolls advances to the next frame`() {
        val game = Game()

        game.roll(5)
        game.roll(5)

        assertThat(game.currentFrameNumber()).isEqualTo(2)
    }

    @Test
    fun `game is not finished before frame number 10`() {
        val game = Game()

        assertThat(game.isFinished()).isFalse()

        repeat(8) {
            game.roll(5)
            game.roll(5)
        }

        assertThat(game.isFinished()).isFalse()
    }

    @Test
    fun `game is finished after frame number 10 is finished`() {
        val game = Game()

        repeat(10) {
            game.roll(5)
            game.roll(5)
        }

        assertThat(game.currentFrameNumber()).isEqualTo(10)
        assertThat(game.isFinished()).isTrue()
    }

    @Test
    fun `rolls can't be performed when game is finished`() {

        val game = Game()

        repeat(10) {
            game.roll(5)
            game.roll(5)
        }

        assertThatThrownBy { game.roll(5) }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("The game is already finished")

    }


}