package com.starmicronics.starxpandsdk

import com.starmicronics.stario10.InterfaceType

class DefaultConnectionSettings {
    companion object {
        var interfaceType: InterfaceType = InterfaceType.Lan
        var identifier: String = "00:11:62:00:00:00"
//        var identifier: String = StarConnectionSettings.FIRST_FOUND_DEVICE
    }
}
