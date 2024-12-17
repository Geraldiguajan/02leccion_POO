
package com.example.bancarioapp

import java.time.LocalDateTime

// Clase que representa una cuenta bancaria
class CuentaBancaria(
    val numeroCuenta: String,
    val titular: String,
    val tipoCuenta: String,
    private var saldo: Double = 0.0
) {

    private val transacciones = mutableListOf<Transaccion>()

    // Consultar saldo
    fun consultarSaldo(): String {
        return "Saldo actual: $${"%.2f".format(saldo)}"
    }

    // Depositar dinero
    fun depositar(monto: Double): String {
        return if (monto > 0) {
            saldo += monto
            registrarTransaccion("Depósito", monto)
            "Depósito de $${"%.2f".format(monto)} realizado con éxito."
        } else {
            "El monto debe ser positivo."
        }
    }

    // Retirar dinero
    fun retirar(monto: Double): String {
        return if (monto > 0 && monto <= saldo) {
            saldo -= monto
            registrarTransaccion("Retiro", monto)
            "Retiro de $${"%.2f".format(monto)} realizado con éxito."
        } else if (monto > saldo) {
            "Saldo insuficiente para realizar el retiro."
        } else {
            "El monto debe ser positivo."
        }
    }

    // Transferir dinero
    fun transferir(monto: Double, cuentaDestino: CuentaBancaria): String {
        return if (monto > 0 && monto <= saldo) {
            saldo -= monto
            cuentaDestino.saldo += monto
            registrarTransaccion("Transferencia a ${cuentaDestino.numeroCuenta}", monto)
            cuentaDestino.registrarTransaccion("Transferencia de ${this.numeroCuenta}", monto)
            "Transferencia de $${"%.2f".format(monto)} realizada con éxito."
        } else if (monto > saldo) {
            "Saldo insuficiente para realizar la transferencia."
        } else {
            "El monto debe ser positivo."
        }
    }

    // Mostrar historial de transacciones
    fun mostrarHistorial(): String {
        return if (transacciones.isNotEmpty()) {
            transacciones.joinToString("\n") { it.toString() }
        } else {
            "No hay transacciones registradas."
        }
    }

    // Registrar una transacción
    private fun registrarTransaccion(tipo: String, monto: Double) {
        val fecha = LocalDateTime.now()
        transacciones.add(Transaccion(tipo, monto, fecha))
    }

    // Clase interna para representar transacciones
    private data class Transaccion(
        val tipo: String,
        val monto: Double,
        val fechaHora: LocalDateTime
    ) {
        override fun toString(): String {
            return "$tipo de $${"%.2f".format(monto)} el ${fechaHora.toLocalDate()} a las ${fechaHora.toLocalTime()}"
        }
    }
}



