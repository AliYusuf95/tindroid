package com.example.testapp.client.models

public val User.initials: String
    get() = name.initials()

public val Channel.initials: String
    get() = name.initials()

//public fun Message.getTranslation(language: String): String {
//    return i18n.get("${language}_text", "")
//}
//
//public val Message.originalLanguage: String
//    get() = i18n.get("language", "")
//
//public fun Channel.getUnreadMessagesCount(forUserId: String = ""): Int {
//    return if (forUserId.isEmpty()) {
//        read.sumOf { it.unreadMessages }
//    } else {
//        read
//            .filter { it.user.id == forUserId }
//            .sumOf { it.unreadMessages }
//    }
//}
//
//internal fun getExternalField(obj: CustomObject, key: String): String {
//
//    val value = obj.extraData[key]
//    val emptyResult = ""
//
//    return if (value == null) {
//        emptyResult
//    } else {
//        if (value is String) {
//            value
//        } else {
//            emptyResult
//        }
//    }
//}

internal fun <A, B> Map<A, B>.get(key: A, default: B): B {
    return get(key) ?: default
}

internal fun String.initials(): String {
    return trim()
        .split("\\s+".toRegex())
        .take(2)
        .joinToString(separator = "") { it.take(1).uppercase() }
}
