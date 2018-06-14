package com.github.imeszaros.ambilight.model

/**
 * Represents an Ambilight layer, which has four sides matching to the sides
 * of the Ambilight device. Due to the various device configurations, all
 * sides are marked as optional.
 *
 * The sides are represented by maps where the keys are `Int`s denoting the
 * order of the corresponding LED within the side, and the values are `RGB`
 * instances carrying the color information of the mapped LED.
 *
 * @property left A map containing the color information of the LEDs on the left side of the device.
 * @property top A map containing the color information of the LEDs on the top side of the device.
 * @property right A map containing the color information of the LEDs on the right side of the device.
 * @property bottom A map containing the color information of the LEDs on the bottom side of the device.
 */
data class Layer (
        val left: Map<Int, RGB>?,
        val top: Map<Int, RGB>?,
        val right: Map<Int, RGB>?,
        val bottom: Map<Int, RGB>?)