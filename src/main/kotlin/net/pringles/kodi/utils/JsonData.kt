package net.pringles.kodi.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter.ISO_INSTANT

class JsonData(private var node: JsonNode?) {
    operator fun get(field: String): JsonData = JsonData(node?.path(field))
    operator fun get(index: Int): JsonData = JsonData(node?.path(index))

    fun text(): String = textOrNull() ?: throw IllegalStateException("Cannot convert this data as text")
    fun textOrNull(): String? = node?.textValue()
    fun int(): Int = intOrNull() ?: throw IllegalStateException("Cannot convert this data as int")
    fun intOrNull(): Int? = node?.intValue()
    fun long(): Long = longOrNull() ?: throw IllegalStateException("Cannot convert this data as long")
    fun longOrNull(): Long? = if (node?.isLong == true) node?.longValue() else null
    fun bool(): Boolean = boolOrNull() ?: throw IllegalStateException("Cannot convert this data as bool")
    fun boolOrNull(): Boolean? = if (node?.isBoolean == true) node!!.booleanValue() else null

    fun list(): MutableList<JsonData> = listOrNull() ?: throw IllegalStateException("Cannot convert this data as list")
    fun listOrNull(): MutableList<JsonData>? = node?.toList()?.map { JsonData(it) }?.toMutableList()

    fun asInt(): Int = node?.asInt() ?: 0
    fun asLong(): Long = node?.asLong() ?: 0L
    fun time(): OffsetDateTime = timeOrNull() ?: throw IllegalStateException("Cannot convert this data as time")
    fun timeOrNull(): OffsetDateTime? = textOrNull()?.let { OffsetDateTime.from(DATE_TIME_PARSER.parse(it)) }

    fun hasValue(field: String) = node?.hasNonNull(field) != false

    fun with(field: String, value: String) = with(field, JsonData(TextNode(value)))
    fun with(field: String, value: Int) = with(field, JsonData(IntNode(value)))

    fun with(field: String, value: JsonData): JsonData {
        val data = node ?: mapper.createObjectNode()
        if (data !is ObjectNode) throw IllegalStateException("Cannot put field in ${node!!::class.java.canonicalName}")
        return JsonData(data.set<ObjectNode>(field, value.node))
    }

    fun format() = node?.toPrettyString() ?: "null"
    override fun toString() = node?.toString() ?: "null"

    companion object {
        private val mapper = jacksonObjectMapper()

        val DATE_TIME_PARSER = ISO_INSTANT.withZone(ZoneOffset.UTC)

        fun empty() = JsonData(null)
        fun create(text: String) = JsonData(mapper.readTree(text))

        fun text(value: String?) = if (value == null) empty() else JsonData(TextNode(value))
        fun int(value: Int?) = if (value == null) empty() else JsonData(IntNode(value))
    }
}
