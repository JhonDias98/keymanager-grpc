package br.com.pix.config.interceptor

import io.micronaut.aop.Around


@MustBeDocumented //Entra na documentação do Javadoc
@Retention(AnnotationRetention.RUNTIME) //Disponivel em tempo de execução
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION) //Alvos da anotação
@Around //Vai possuir um interceptor
annotation class ErrorAdvice()
