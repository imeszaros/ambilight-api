package com.github.imeszaros.ambilight.model

/**
 * Provides information about the Ambilight device including the number of layers and the number of LEDs on each side.
 *
 * @property layers The number of layers.
 * @property left The number of LEDs on the left side.
 * @property top The number of LEDs on the top side.
 * @property right The number of LEDs on the right side.
 * @property bottom The number of LEDs on the bottom side.
 */
data class Topology (
        val layers: Int,
        val left: Int,
        val top: Int,
        val right: Int,
        val bottom: Int)