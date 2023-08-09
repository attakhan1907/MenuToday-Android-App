package com.example.menutoday.data.order

sealed class OrderStatus(val status: String) {

    object Ordered: OrderStatus("Ordered")
    object Canceled: OrderStatus("Canceled")
    object Confirmed: OrderStatus("Confirmed")
    object Delivering: OrderStatus("Delivering")
    object Delivered: OrderStatus("Delivered")
    object Returned: OrderStatus("Returned")

}

fun getOrderStatus(status: String): OrderStatus {
    return when (status) {
        "Ordered" -> {
            OrderStatus.Ordered
        }
        "Canceled" -> {
            OrderStatus.Canceled
        }
        "Confirmed" -> {
            OrderStatus.Confirmed
        }
        "Delivering" -> {
            OrderStatus.Delivering
        }
        "Delivered" -> {
            OrderStatus.Delivered
        }
        else -> OrderStatus.Returned
    }
}