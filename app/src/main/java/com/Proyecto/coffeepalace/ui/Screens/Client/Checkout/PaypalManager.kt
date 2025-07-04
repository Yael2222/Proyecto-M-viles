package com.Proyecto.coffeepalace.ui.Screens.Client.Checkout;

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.paypal.android.corepayments.CoreConfig
import com.paypal.android.corepayments.Environment
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutClient
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutFundingSource
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object PayPalHolder {
    var manager: PayPalManager? = null
}

class PayPalManager(
    private val activity: ComponentActivity,
    clientId: String,
    isSandbox: Boolean,
    returnScheme: String,
    hostSchema: String,
    private val createOrder: suspend () -> String,
    private val captureOrder: suspend (String) -> Unit,
    private val onResult: (Result) -> Unit
) {

    sealed interface Result {
        data class Success(val orderId: String) : Result
        data class Failure(val cause: Throwable) : Result
        object Canceled : Result
    }

    private val config = CoreConfig(
        clientId,
        if (isSandbox) Environment.SANDBOX else Environment.LIVE
    )

    private var pendingOrderId: String? = null

    private val client = PayPalWebCheckoutClient(
        activity.applicationContext,
        config,
        "$returnScheme://$hostSchema"
    )

    private var pendingAuthState: String? = null

    fun startCheckout() {
        activity.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val orderId = createOrder()
                pendingOrderId = orderId // Almacenar para verificación posterior

                val request = PayPalWebCheckoutRequest(
                    orderId,
                    fundingSource = PayPalWebCheckoutFundingSource.PAYPAL,
                )

                withContext(Dispatchers.Main) {
                    client.start(activity, request)
                }
            } catch (e: Exception) {
                onResult(Result.Failure(e))
            }
        }
    }

    fun handleDeepLink(intent: Intent?) {
        val uri = intent?.data ?: run {
            onResult(Result.Failure(Exception("Intent sin datos")))
            return
        }

        println("🔍 Deep Link Recibido: $uri")

        when (uri.getQueryParameter("opType")) {
            "payment" -> handlePaymentSuccess(uri)
            "cancel" -> handlePaymentCancel()
            else -> handleUnknownDeepLink(uri)
        }
    }

    private fun handlePaymentSuccess(uri: Uri) {
        val orderId = pendingOrderId ?: run {
            onResult(Result.Failure(Exception("No hay orden pendiente")))
            return
        }

        activity.lifecycleScope.launch {
            try {
                // Verificación en dos pasos
                val isVerified = withContext(Dispatchers.IO) {
                    captureOrder(orderId)
                    true
                }

                if (isVerified) {
                    onResult(Result.Success(orderId))
                } else {
                    onResult(Result.Failure(Exception("Pago no verificado")))
                }
            } catch (e: Exception) {
                onResult(Result.Failure(e))
            }
        }
    }

    private fun handlePaymentCancel() {
        println("Pago cancelado por el usuario")
//        println("Estado limpio")
        pendingOrderId = null
        onResult(Result.Canceled)
    }

    private fun handleUnknownDeepLink(uri: Uri) {
        println(" Deep link no reconocido: $uri")
        onResult(Result.Failure(Exception("URL no soportada")))
    }

}