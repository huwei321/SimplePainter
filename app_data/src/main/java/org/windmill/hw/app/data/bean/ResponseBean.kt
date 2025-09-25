package org.windmill.hw.app.data.bean

/**
 * File description.
 * status 与 code（与之前的confyer接口响应不同导致）
 * @author hw
 * @date 2024/10/31
 */
data class ResponseBean<T>(
    var status: Int = 0,
    var message: String? = null,
    var msgCode: String? = null,
    var data: T? = null,
    var code: Int = 0
)
