package com.github.imeszaros.ambilight.model

/**
 * A simple data class that can carry up to four Ambilight layers.
 *
 * @property layer1 First layer of the Ambilight device.
 * @property layer2 Second layer of the Ambilight device.
 * @property layer3 Third layer of the Ambilight device.
 * @property layer4 Fourth layer of the Ambilight device.
 */
data class Layers (
        val layer1: Layer?,
        val layer2: Layer?,
        val layer3: Layer?,
        val layer4: Layer?)