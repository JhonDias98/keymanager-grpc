package br.com.pix.config.interceptor

import br.com.pix.config.exceptions.ChavePixExistenteException
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import io.micronaut.http.client.exceptions.HttpClientResponseException
import java.lang.Exception
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
@InterceptorBean(ErrorAdvice::class)
class ErrorAdviceInterceptor : MethodInterceptor<Any, Any> {

    override fun intercept(context: MethodInvocationContext<Any, Any>): Any? {

        return try {
            return context.proceed()
        } catch (e: Exception) {
            val responseObserver = context.parameterValues[1] as StreamObserver<*>
            val status = when(e) {
                is ConstraintViolationException -> Status.INVALID_ARGUMENT.withCause(e).withDescription(e.message)
                is ChavePixExistenteException -> Status.ALREADY_EXISTS.withDescription(e.message)
                is IllegalStateException -> Status.FAILED_PRECONDITION.withDescription(e.message)
                is ConstraintViolationException -> Status.INVALID_ARGUMENT.withDescription(e.message)
                //is HttpClientResponseException -> Status.INTERNAL.withDescription("Um erro inesperado aconteceu, verifique se os dados estão corretos, se não tente novamente")

                else -> Status.UNKNOWN.withCause(e).withDescription("Um erro inesperado aconteceu")
            }
            val statusRuntimeException = StatusRuntimeException(status)

            responseObserver.onError(statusRuntimeException)
        }
    }
}