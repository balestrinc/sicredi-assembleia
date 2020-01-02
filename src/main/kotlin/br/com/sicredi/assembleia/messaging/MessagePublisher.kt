package br.com.sicredi.assembleia.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class MessagePublisher(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val mapper: ObjectMapper
) {

    fun publish(topic: String, payload: Any) {
        val stringPayload = mapper.writeValueAsString(payload)
        logger.info("#### Publishing message: $stringPayload")
        kafkaTemplate.send(topic, stringPayload)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(MessagePublisher::class.java)
    }
}
