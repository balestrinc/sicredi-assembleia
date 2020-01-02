package br.com.sicredi.assembleia.messaging

import java.io.IOException
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class VotacaoResultedConsumer {

    companion object {
        private val logger = LoggerFactory.getLogger(VotacaoResultedConsumer::class.java)
    }

    @KafkaListener(topics = [AssembleiaTopics.VOTACAO_RESULTED])
    @Throws(IOException::class)
    fun consume(message: String) {
        logger.info("#### Consumed message: $message")
    }
}
