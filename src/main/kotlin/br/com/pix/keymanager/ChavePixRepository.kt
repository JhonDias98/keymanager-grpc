package br.com.pix.keymanager

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*
import java.util.List

@Repository
interface ChavePixRepository: JpaRepository<ChavePix, Long> {
    fun existsByChave(chave: String?): Boolean
}