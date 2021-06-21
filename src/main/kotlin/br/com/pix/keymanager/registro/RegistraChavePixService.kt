package br.com.pix.keymanager.registro

import br.com.pix.client.bcb.BcbClient
import br.com.pix.client.bcb.registra.RegistraChavePixBcbRequest
import br.com.pix.client.itau.ItauClient
import br.com.pix.config.exceptions.ChavePixExistenteException
import br.com.pix.keymanager.ChavePix
import br.com.pix.keymanager.ChavePixRepository
import br.com.pix.keymanager.ChavePixRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class RegistraChavePixService( @Inject val repository: ChavePixRepository,
                              @Inject val itauClient: ItauClient,
                              @Inject val bcbClient: BcbClient
) {
    private val Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun registra(@Valid novaChavePix: ChavePixRequest):ChavePix {

        //Verifica se a chave ja existe no banco
        if(repository.existsByChave(novaChavePix.chave)) {
            throw ChavePixExistenteException("Chave ja cadastrada")
        }

        //Consulta no sistema do ERP do ITAU Client
        val itauClientResponse = itauClient.buscaContaPorTipo(novaChavePix.clienteId, novaChavePix.tipoDeConta.toString())
        check(itauClientResponse.status != HttpStatus.NOT_FOUND) { "Cliente não encontrado no Itaú" }
        check(itauClientResponse.status == HttpStatus.OK) { "Erro ao buscar dados da conta no Itaú" }

        val conta = itauClientResponse.body()!!.toModel()
        Logger.info("Busca pela conta concluído com sucesso")

        //Salva no banco de dados
        val novaChave = novaChavePix.toModel(conta)
        repository.save(novaChave)
        Logger.info("Chave Pix salva com sucesso no sistema")

        //Registrando a chave no Banco Central do Brasil (BCB)
        val bcbResponse = bcbClient.registraNoBcb(RegistraChavePixBcbRequest.of(chave = novaChave))
        check(bcbResponse.status != HttpStatus.UNPROCESSABLE_ENTITY) { "Chave Pix já cadastrada no BCB" }
        check(bcbResponse.status == HttpStatus.CREATED) { "Não foi possivel cadastrar chave no BCB" }
        Logger.info("Registrando chave Pix no Banco Central do Brasil")

        return novaChave
    }
}