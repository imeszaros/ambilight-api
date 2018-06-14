package com.github.imeszaros.ambilight.model

/**
 * Simple wrapper class that makes possible to wrap the Ambilight mode to be usable with the JSON protocol.
 *
 * @property current The current Ambilight mode. Possible values are:
 *  - `internal`: The internal ambilight algorithm is used to calculate the ambilight colors.
 *  - `manual`: The cached ambilight colors are shown.
 *  - `expert`: The cached ambilight colors are used as input for the internal ambilight algorithm.
 */
data class Mode (val current: String)