package br.com.pix.client.bcb.registra

import br.com.pix.client.bcb.registra.dados.BankAccount
import br.com.pix.client.bcb.registra.dados.Owner
import br.com.pix.keymanager.TipoDeChave
import java.time.LocalDateTime

data class RegistraChavePixBcbResponse(
    val keyType: TipoDeChave,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner,
    val createdAt: LocalDateTime
)
