package br.com.pix.keymanager.registro

import br.com.pix.config.interceptor.ErrorAdvice
import br.com.pix.grpc.KeymanagerRegistraGrpcServiceGrpc
import br.com.pix.grpc.RegistraChavePixRequest
import br.com.pix.grpc.RegistraChavePixResponse
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErrorAdvice
@Singleton
class RegistraChavePixEndPoint(@Inject private val service: RegistraChavePixService)
    : KeymanagerRegistraGrpcServiceGrpc.KeymanagerRegistraGrpcServiceImplBase() {
    override fun registra(

        request: RegistraChavePixRequest?,
        responseObserver: StreamObserver<RegistraChavePixResponse>?

    ) {
        val novaChave = request!!.toModel()
        val service = service.registra(novaChave)

        responseObserver?.onNext(RegistraChavePixResponse.newBuilder()
            .setClientId(service.clientId.toString())
            .setPixId(service.pixId.toString())
            .build())
        responseObserver?.onCompleted()
    }
}