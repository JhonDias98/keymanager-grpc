package br.com.pix.keymanager

import br.com.pix.grpc.TipoDeConta
import br.com.pix.utils.validation.ValidPixKey
import br.com.pix.utils.validation.ValidUUID
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class ChavePixRequest(
    @ValidUUID @field:NotBlank val clienteId: String,
    @field:NotNull val tipoDeChave: TipoDeChave?,
    @field:NotNull val tipoDeConta: TipoDeConta?,
    @field:Size(max = 77) val chave: String
)  {
    fun toModel(conta: ContaAssociada): ChavePix{
        return ChavePix(
            clientId = UUID.fromString(this.clienteId),
            tipoDeChaveRegex = TipoDeChave.valueOf(this.tipoDeChave!!.name),
            tipoDeConta = TipoDeConta.valueOf(this.tipoDeConta!!.name),
            chave = if(this.tipoDeChave == TipoDeChave.RANDOM) UUID.randomUUID().toString() else this.chave,
            conta = conta
        )
    }
}