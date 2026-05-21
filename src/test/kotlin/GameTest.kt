import com.chrystowicz.bowling.Game
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

    @Nested
    @DisplayName("a roll can take from 0 to 10 pins")
    inner class Pins {

        @Test
        fun `knocked down pins can't be higher than 10`() {

            val game = Game()

            assertThatThrownBy { game.roll(11) }.isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Knocked down pins can't be higher than 10")
        }

    }


}