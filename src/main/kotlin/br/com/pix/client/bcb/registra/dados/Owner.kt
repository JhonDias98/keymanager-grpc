package br.com.pix.client.bcb.registra.dados

data class Owner(
    val type: OwnerType,
    val name: String,
    val taxIdNumber: String
)