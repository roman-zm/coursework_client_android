package com.namor.coursework

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

class CalendarDeserializer: JsonDeserializer<Calendar> {
    override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
    ): Calendar {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
