package com.nubank.shortener.observability.logging.logger

import com.nubank.shortener.observability.logging.model.LogEvent
import com.nubank.shortener.observability.logging.model.LogLevel
import com.nubank.shortener.observability.logging.sink.LogSink
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class CompositeAppLoggerTest {
    @Test
    fun `given info log when logger receives message then sends event to every sink`() {
        // Given
        val firstSink = FakeLogSink()
        val secondSink = FakeLogSink()
        val logger = CompositeAppLogger(listOf(firstSink, secondSink))

        // When
        logger.info(
            message = "Remote URL shortening succeeded",
            attributes = mapOf("alias" to "123"),
        )

        // Then
        val expectedEvent = LogEvent(
            level = LogLevel.Info,
            message = "Remote URL shortening succeeded",
            attributes = mapOf("alias" to "123"),
        )
        assertEquals(listOf(expectedEvent), firstSink.events)
        assertEquals(listOf(expectedEvent), secondSink.events)
    }

    @Test
    fun `given error log when logger receives throwable then keeps throwable and attributes`() {
        // Given
        val sink = FakeLogSink()
        val logger = CompositeAppLogger(listOf(sink))
        val throwable = IllegalStateException("API unavailable")

        // When
        logger.error(
            message = "Remote URL shortening failed",
            throwable = throwable,
            attributes = mapOf("errorType" to "IllegalStateException"),
        )

        // Then
        val event = sink.events.single()
        assertEquals(LogLevel.Error, event.level)
        assertEquals("Remote URL shortening failed", event.message)
        assertEquals(mapOf("errorType" to "IllegalStateException"), event.attributes)
        assertSame(throwable, event.throwable)
    }

    @Test
    fun `given one sink fails when logger receives message then sends event to remaining sinks`() {
        // Given
        val workingSink = FakeLogSink()
        val logger = CompositeAppLogger(
            listOf(
                FailingLogSink(),
                workingSink,
            ),
        )

        // When
        logger.info("User requested shortening")

        // Then
        assertEquals(
            listOf(
                LogEvent(
                    level = LogLevel.Info,
                    message = "User requested shortening",
                ),
            ),
            workingSink.events,
        )
    }
}

private class FakeLogSink : LogSink {
    val events = mutableListOf<LogEvent>()

    override fun log(event: LogEvent) {
        events += event
    }
}

private class FailingLogSink : LogSink {
    override fun log(event: LogEvent) {
        error("Sink unavailable")
    }
}
