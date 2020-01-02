package br.com.sicredi.assembleia.support

import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer

fun createAndStartMockServer(host: String, port: Int): MockServerClient {
    val mockServerClient = MockServerClient(host, port)
    ClientAndServer.startClientAndServer(port)
    return mockServerClient
}
