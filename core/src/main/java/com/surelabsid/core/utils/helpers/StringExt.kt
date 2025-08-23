package com.surelabsid.core.utils.helpers


fun String.getPokeId(): String {
    val id = this.trimEnd('/').substringAfterLast('/')
    return id
}