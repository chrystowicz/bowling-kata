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
    fun `a strike adds the next two rolls as bonus to the frame score`() {

        val game = Game()

        game.roll(10)
        game.roll(5)
        game.roll(4)

        assertThat(game.currentScore()).isEqualTo(28)

    }

    @Test
    fun `12 consecutive strikes results in a score of exactly 300`() {

        val game = Game()

        repeat(12) {
            game.roll(10)
        }

        assertThat(game.currentScore()).isEqualTo(300)
    }

    @Test
    fun `game starts on frame 1`() {
        val game = Game()

        assertThat(game.currentFrameNumber()).isEqualTo(1)
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
    fun `game is finished after completing all 10 frames`() {
        val game = Game()

        repeat(10) {
            game.roll(5)
            game.roll(4)
        }

        assertThat(game.currentFrameNumber()).isEqualTo(10)
        assertThat(game.isFinished()).isTrue()
    }

    @Test
    fun `rolls can't be performed when game is finished`() {

        val game = Game()

        repeat(10) {
            game.roll(5)
            game.roll(4)
        }

        assertThatThrownBy { game.roll(5) }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("The game is already finished")

    }

    @Test
    fun `last frame allows three rolls after a strike`() {
        val game = Game()

        repeat(9) {
            game.roll(10)
        }

        game.roll(10)
        game.roll(10)
        game.roll(10)


        assertThat(game.isFinished()).isTrue()
    }


}