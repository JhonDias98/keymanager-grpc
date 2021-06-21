package br.com.pix.client.bcb.registra

import br.com.pix.client.bcb.registra.dados.AccountType
import br.com.pix.client.bcb.registra.dados.BankAccount
import br.com.pix.client.bcb.registra.dados.Owner
import br.com.pix.client.bcb.registra.dados.OwnerType
import br.com.pix.keymanager.ChavePix
import br.com.pix.keymanager.ContaAssociada
import br.com.pix.keymanager.TipoDeChave

data class RegistraChavePixBcbRequest(
    val keyType: TipoDeChave,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner
) {
    companion object {

        /*
        * Pega os dados da chave Pix para enviar para o Banco Central do Brasil (BCB)
         */
        fun of(chave: ChavePix): RegistraChavePixBcbRequest {
            return RegistraChavePixBcbRequest(
                keyType = chave.tipoDeChaveRegex,
                key = chave.chave,
                bankAccount = BankAccount(
                    participant = ContaAssociada.ITAU_UNIBANCO_ISPB,
                    branch = chave.conta.agencia,
                    accountNumber = chave.conta.numeroDaConta,
                    accountType = AccountType.by(chave.tipoDeConta)
                ),
                owner = Owner(
                    type = OwnerType.NATURAL_PERSON,
                    name = chave.conta.nomeDoTitular,
                    taxIdNumber = chave.conta.cpfDoTitular
                )
            )
        }
    }
}