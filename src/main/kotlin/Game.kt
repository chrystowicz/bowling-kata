package com.chrystowicz.bowling

import java.util.LinkedList
import java.util.stream.Collectors

class Game(
    private val rolls: LinkedList<Int> = LinkedList()
) {

    fun roll(knockedDownPins: Int) {
        if(knockedDownPins > 10) {
            throw IllegalArgumentException("Knocked down pins can't be higher than 10")
        }
        rolls.addFirst(knockedDownPins)
    }

    fun currentScore() : Int {
        return rolls.stream().collect(
            Collectors.summingInt {
                it
            }
        )
    }

}