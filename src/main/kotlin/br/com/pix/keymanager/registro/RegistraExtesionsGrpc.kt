package br.com.pix.keymanager.registro

import br.com.pix.grpc.RegistraChavePixRequest
import br.com.pix.grpc.TipoDeConta
import br.com.pix.keymanager.ChavePixRequest
import br.com.pix.keymanager.TipoDeChave

fun RegistraChavePixRequest.toModel(): ChavePixRequest {
    return ChavePixRequest(
        clienteId = clientId,
        tipoDeChave = when(tipoDeChave){
            br.com.pix.grpc.TipoDeChave.UNKNOWN_TIPO_CHAVE -> null
            else -> TipoDeChave.valueOf(tipoDeChave.name)
        },
        chave = chave,
        tipoDeConta = when(tipoDeConta){
            TipoDeConta.UNKNOWN_TIPO_CONTA -> null
            else -> TipoDeConta.valueOf(tipoDeConta.name)
        }
    )
}