package com.zy.proyecto_final.repository

import com.zy.proyecto_final.pojo.Time
import com.zy.proyecto_final.retrofit.Respuesta
import com.zy.proyecto_final.retrofit.TimeAPI
import com.zy.proyecto_final.retrofit.TimeService

class TimeRepository {
    private var _service: TimeAPI

    init {
        _service = TimeService.apiService
    }

    suspend fun getTime(): Time {
        val item = _service.getTime()
        var time = Time()
        time.year = item?.year
        time.month = item?.month
        time.day = item?.day
        time.hour = item?.hour
        time.minute = item?.minute
        time.seconds = item?.seconds
        time.milliSeconds = item?.milliSeconds
        time.dateTime = item?.dateTime
        time.date = item?.date
        time.time = item?.time
        time.timeZone = item?.timeZone
        time.dayOfWeek = item?.dayOfWeek
        time.dstActive = item?.dstActive ?: false
        return time
    }
}