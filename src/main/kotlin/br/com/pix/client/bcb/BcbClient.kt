package br.com.pix.client.bcb

import br.com.pix.client.bcb.registra.RegistraChavePixBcbRequest
import br.com.pix.client.bcb.registra.RegistraChavePixBcbResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${bcb.pix.url}")
interface BcbClient {

    //Dados serão enviados e consumidos em XML
    @Post(
        "/api/v1/pix/keys",
        produces = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML]
    )
    fun registraNoBcb(@Body request: RegistraChavePixBcbRequest): HttpResponse<RegistraChavePixBcbResponse>
}