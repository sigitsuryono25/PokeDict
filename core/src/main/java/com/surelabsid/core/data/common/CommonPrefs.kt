package com.surelabsid.core.data.common

import com.chibatching.kotpref.KotprefModel

object CommonPrefs : KotprefModel() {
    var lastSync by stringPref()
}