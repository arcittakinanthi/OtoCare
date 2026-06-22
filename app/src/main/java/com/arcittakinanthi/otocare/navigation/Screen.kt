sealed class Screen(val route: String) {

    data object Login : Screen("login")

    data object Home : Screen("home")

    data object Profile : Screen("profile")

    data object FormBaru : Screen("detail/0")

    data object FormUbah : Screen("detail/{id}") {
        fun withId(id: Long) = "detail/$id"
    }
}