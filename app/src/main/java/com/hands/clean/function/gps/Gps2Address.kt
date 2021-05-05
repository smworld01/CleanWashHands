package com.hands.clean.function.gps

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import java.io.IOException
import java.util.*


fun Gps2Address(
    context: Context,
    latitude: Double, longitude: Double
): String {
    //지오코더... GPS를 주소로 변환
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address?>
    addresses = try {
        geocoder.getFromLocation(
            latitude,
            longitude,
            7
        )
    } catch (ioException: IOException) {
        return "지오코더 서비스 사용불가"
    } catch (illegalArgumentException: IllegalArgumentException) {
        return "잘못된 GPS 좌표"
    }
    if (addresses == null || addresses.size == 0) {
        return "주소 미발견"
    }
    val address: Address? = addresses[0]
    return address?.getAddressLine(0).toString()
}