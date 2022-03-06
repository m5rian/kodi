package net.pringles.kodi.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class JsonData(private var node: JsonNode?) {
    operator fun get(field: String): JsonData = JsonData(node?.path(field))
    operator fun get(index: Int): JsonData = JsonData(node?.path(index))

    fun text(): String = node!!.asText()
    fun int(): Int = node!!.intValue()

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

        fun empty() = JsonData(null)
        fun create(text: String) = JsonData(mapper.readTree(text))

        fun text(value: String?) = if (value == null) empty() else JsonData(TextNode(value))
        fun int(value: Int?) = if (value == null) empty() else JsonData(IntNode(value))
    }
}
