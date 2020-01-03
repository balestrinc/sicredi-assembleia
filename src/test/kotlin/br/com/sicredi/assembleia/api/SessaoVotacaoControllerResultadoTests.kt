package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.messaging.AssembleiaTopics
import br.com.sicredi.assembleia.support.AssembleiaApiTestSetup
import br.com.sicredi.assembleia.support.TestConstants
import br.com.sicredi.assembleia.ws.model.VotoRequest
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotlintest.should
import io.kotlintest.shouldBe
import java.time.Duration
import java.time.LocalDateTime
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.test.annotation.DirtiesContext

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
internal class SessaoVotacaoControllerResultadoTests : AssembleiaApiTestSetup() {

    @Autowired
    private lateinit var kafkaProperties: KafkaProperties

    private lateinit var consumer: KafkaConsumer<String, String>

    @AfterAll
    fun tearDown() {
        consumer.close()
    }

    @Test
    fun `returns results`() {

        val createdPauta = createPauta()
        existentPautaId = createdPauta.id

        val createdSessao = createSessao()
        existentSessaoId = createdSessao.id

        val votoSim1 = VotoRequest(
            associadoCPF = "62289608068",
            votoOpcao = "Sim"
        )
        val votoSim2 = votoSim1.copy(associadoCPF = "91029574006")

        val votoNao = VotoRequest(
            associadoCPF = "88532257003",
            votoOpcao = "Não"
        )

        addVoto(votoSim1)
        addVoto(votoSim2)
        addVoto(votoNao)

        val responseBody = webClient
            .put()
            .uri("/api/pauta/$existentPautaId/sessao/$existentSessaoId")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .returnResult().responseBody

        mapper.readValue<SessaoVotacao>(responseBody!!) should {
            it.totalVotosFavoravel shouldBe 2
            it.totalVotosContrario shouldBe 1
            it.totalVotos shouldBe 3
            it.pautaAprovada shouldBe false
            it.votacaoEncerrada shouldBe false
        }
    }

    @Test
    fun `publish results`() {
        val createdPauta = createPauta()
        existentPautaId = createdPauta.id

        val startDateTime = LocalDateTime.parse(TestConstants.CURRENT_TIME).minusDays(10)
        val endDateTime = startDateTime.plusDays(1)

        val createdSessao = createSessao(startDateTime, endDateTime)
        existentSessaoId = createdSessao.id

        val responseBody = webClient
            .put()
            .uri("/api/pauta/$existentPautaId/sessao/$existentSessaoId")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .returnResult().responseBody

        val expectedSessaoVotacao = createdSessao.copy(
            votacaoEncerrada = true
        )

        mapper.readValue<SessaoVotacao>(responseBody!!) shouldBe expectedSessaoVotacao

        getMessages().iterator().forEach {
            mapper.readValue<SessaoVotacao>(it.value()) shouldBe expectedSessaoVotacao
        }
    }

    private fun getMessages(): MutableIterator<ConsumerRecord<String, String>> {
        consumer = KafkaConsumer(kafkaProperties.buildConsumerProperties())
        val topicPartition = TopicPartition(AssembleiaTopics.VOTACAO_RESULTED, 0)
        val partitions = listOf(topicPartition)
        consumer.assign(partitions)
        consumer.seekToBeginning(partitions)

        val records = consumer.poll(Duration.ofSeconds(1))
        return records.iterator()
    }
}
