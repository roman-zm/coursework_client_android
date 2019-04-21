package com.namor.coursework.service

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.namor.coursework.domain.Film
import com.namor.coursework.domain.Page
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class PageDeserializer : JsonDeserializer<Page<*>> {
    override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
    ): Page<*> {
        val valueType = (typeOfT as ParameterizedType).actualTypeArguments[0]

        val page = json?.asJsonObject?.getAsJsonObject("page")
        val pageSize = page?.get("size")?.asInt ?: 0
        val pageNumber = page?.get("number")?.asInt ?: 0

        val jsonContent = json?.asJsonObject?.getAsJsonObject("_embedded")
                ?.getAsJsonArray("content")

        val list = jsonContent?.mapNotNull {
            context?.deserialize<Any>(it, valueType)
        } ?: listOf()

        return Page(list, pageSize, pageNumber)

    }
}