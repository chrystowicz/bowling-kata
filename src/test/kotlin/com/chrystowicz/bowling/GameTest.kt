package com.chrystowicz.bowling

import org.assertj.core.api.Assertions.assertThat
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

        assertThat(game.currentFrame()).isEqualTo(1)

        game.roll(5)
        game.roll(5)
    }

    @Test
    fun `completing two rolls advances to the next frame`() {
        val game = Game()

        game.roll(5)
        game.roll(5)

        assertThat(game.currentFrame()).isEqualTo(2)
    }


}