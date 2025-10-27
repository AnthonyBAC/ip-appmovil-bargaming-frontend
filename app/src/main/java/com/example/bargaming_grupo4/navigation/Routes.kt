package com.example.bargaming_grupo4.navigation

sealed class Route(val path: String) {
    data object Welcome : Route("welcome")
    data object Home : Route("home")
    data object Login : Route("login")
    data object Register : Route("register")
    data object Nosotros : Route("nosotros")
    data object Descripcion : Route("descripcion")
    data object Consolas : Route("consolas")
    data object Profile : Route("profile")

    data object AccountEntry : Route("account_entry")

}
