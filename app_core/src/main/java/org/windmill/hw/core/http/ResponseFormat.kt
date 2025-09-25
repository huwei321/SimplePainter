package org.windmill.hw.core.http

/**
 * File description.
 *
 * @author hw
 * @date 2025/1/13
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ResponseFormat(
    val format: String = "json"
)