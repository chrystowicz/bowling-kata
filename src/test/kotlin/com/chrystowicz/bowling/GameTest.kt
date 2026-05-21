package com.chrystowicz.bowling

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `a roll should be able to knocked down pins and that's the current score`() {
        val game = Game()

        game.roll(5)

        assertThat(game.currentScore()).isEqualTo(5)
    }

    @Test
    fun `rolls should be per frame, initially should be frame 1`() {
        val game = Game()

        assertThat(game.currentFrame()).isEqualTo(1)

        game.roll(5)
        game.roll(5)
    }

//    @Test
//    fun `new frame should be created when a frame is finished`() {
//        val game = Game()
//
//        game.roll(5)
//        game.roll(5)
//
//        assertThat(game.currentFrame()).isEqualTo(2)
//    }


}