package br.com.pix.client.bcb.registra.dados

import br.com.pix.grpc.TipoDeConta
import br.com.pix.grpc.TipoDeConta.*

enum class AccountType {
    CACC, SVGS;
    companion object {
        fun by(domainType: TipoDeConta): AccountType {
            return when (domainType) {
                CONTA_CORRENTE -> CACC
                CONTA_POUPANCA -> SVGS
                UNKNOWN_TIPO_CONTA -> TODO()
                UNRECOGNIZED -> TODO()
            }
        }
    }
}