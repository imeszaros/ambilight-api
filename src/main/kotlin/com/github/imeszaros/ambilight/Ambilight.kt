package com.github.imeszaros.ambilight

import com.google.gson.GsonBuilder
import okhttp3.*
import com.github.imeszaros.ambilight.model.Layers
import com.github.imeszaros.ambilight.model.Mode
import com.github.imeszaros.ambilight.model.Topology
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Represents an Ambilight capable device. At least the device hostname must be passed
 * to the constructor to be able to access the device.
 *
 * The default port is `1925` and the default API version is `1`.
 *
 * @param host The hostname of the Ambilight device.
 * @param port The port of the Ambilight device.
 * @param apiVersion The API version to use.
 * @param https If `true`, the API is accessed through HTTPS.
 * @param clientBuilder A custom `OkHttpClient.Builder` instance to create the HTTP client used
 * to access the JointSPACE interface.
 */
class Ambilight(
        host: String,
        port: Int = 1925,
        apiVersion: String = "1",
        https: Boolean = false,
        clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
                .connectTimeout(1, SECONDS)
                .readTimeout(1, SECONDS)
                .writeTimeout(1, SECONDS)) {

    private val baseUrl =  (if (https) "https" else "http") + "://$host:$port/$apiVersion/ambilight/"

    private val jsonType = MediaType.parse("application/json; charset=utf-8")

    private val gson = GsonBuilder().create()

    private val client = clientBuilder.build()

    private val topologyRequest = buildRequest("topology").build()
    private val modeRequest = buildRequest("mode").build()

    private val setModeInternalRequest = buildRequest("mode").postJson(Mode("internal")).build()
    private val setModeManualRequest = buildRequest("mode").postJson(Mode("manual")).build()
    private val setModeExpertRequest = buildRequest("mode").postJson(Mode("expert")).build()

    private val getAmbilightMeasuredRequest = buildRequest("measured").build()
    private val getAmbilightProcessedRequest = buildRequest("processed").build()
    private val getAmbilightCachedRequest = buildRequest("cached").build()

    private fun buildRequest(path: String) = Request.Builder().url(baseUrl + path)

    private fun <T> String.parseJson(type: Class<T>) = gson.fromJson(this, type)

    private fun Request.newCall(): Call = client.newCall(this)

    private fun Request.Builder.postJson(body: Any) = this
            .post(RequestBody.create(jsonType, gson.toJson(body)))

    private fun Request.call() = this
            .newCall()
            .execute()
            .close()

    private fun <T> Request.callAndParseBody(type: Class<T>): T = this
            .newCall()
            .execute()
            .body()
            ?.string()
            ?.parseJson(type)!!

    /**
     * Returns the topology information about the Ambilight device.
     *
     * @see Topology
     */
    fun getTopology(): Topology = topologyRequest.callAndParseBody(Topology::class.java)

    /**
     * Returns the current operation mode of the Ambilight device.
     *
     * @see Mode
     */
    fun getMode(): String = modeRequest.callAndParseBody(Mode::class.java).current

    /**
     * Sets the operation mode of the Ambilight device to `internal`. In this mode
     * the internal ambilight algorithm is used to calculate the ambilight colors.
     */
    fun setModeInternal() = setModeInternalRequest.call()

    /**
     * Sets the operation mode of the Ambilight device to `manual`. In this mode
     * cached ambilight colors are shown.
     */
    fun setModeManual() = setModeManualRequest.call()

    /**
     * Sets the operation mode of the Ambilight device to `expert`. In this mode
     * the cached ambilight colors are used as input for the internal ambilight algorithm.
     */
    fun setModeExpert() = setModeExpertRequest.call()

    /**
     * Returns the measured ambilight colors before processing by the internal ambilight algorithm.
     *
     * @see Layers
     */
    fun getMeasured(): Layers = getAmbilightMeasuredRequest.callAndParseBody(Layers::class.java)

    /**
     * Returns the ambilight colors calculated by the internal ambilight algorithm.
     *
     * @see Layers
     */
    fun getProcessed(): Layers = getAmbilightProcessedRequest.callAndParseBody(Layers::class.java)

    /**
     * Returns the ambilight colors stored in the cache.
     *
     * @see Layers
     */
    fun getCached(): Layers = getAmbilightCachedRequest.callAndParseBody(Layers::class.java)

    /**
     * Changes the ambilight colors in the cache. When changing part of the pixels, the other pixels keep their value.
     *
     * @see Layers
     */
    fun setCached(layers: Layers) = buildRequest("cached").postJson(layers).build().call()
}