package com.sts.feature.navigation

import androidx.annotation.StringRes
import com.sts.feature.R
import timber.log.Timber
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

sealed class Route(private val name: String, @StringRes val title: Int) {

    var route = ""

    companion object {
        private lateinit var map: Map<String, Route>

        /**
         * This method create a static route map Map<String, Route>.
         * Should call on onCreate() of Application
         *
         */
        fun initAppRoute() {
            map = Route::class.getAllRoutes()
                .associateBy { value ->
                    value.route
                }
        }

        /**
         *  Get all nested Route Object
         */
        private fun <T : Any> KClass<T>.getAllRoutes(
            level: Int = 0,
            parentName: String = ""
        ): List<Route> {
            if (this.nestedClasses.isEmpty()) {
                return if (level > 0) {
                    val route = this.routeObject(parentName)
                    route?.run { listOf(this) } ?: listOf()
                } else listOf()
            }
            val list = mutableListOf<Route>()
            this.nestedClasses.forEach {
                val route = this.routeObject(parentName)
                route?.run {
                    list.add(this)
                }
                list.addAll(it.getAllRoutes(level + 1, route?.route ?: ""))
            }
            return list
        }

        private fun <T : Any> KClass<T>.routeObject(parentName: String = ""): Route? {
            if (this.isSubclassOf(Route::class)) {
                return (this.objectInstance as Route?)?.apply {
                    this.route = if (parentName.isEmpty()) this.name else "$parentName/${this.name}"
                }
            }
            return null
        }

        fun valueOf(value: String): Route? {
            Timber.d(map.values.joinToString {
                "${it.title} - ${it.route}\n"
            })
            return map[value]
        }

        fun values() = map.values.toTypedArray()
    }

    object Home : Route(name = "home", title = R.string.home)

    object Search : Route(name = "search", title = R.string.search)

    object Articles : Route(name = "articles?mode={mode}&id={keyword}", title = R.string.articles)

    object DialogLoading : Route(name = "loading", title = R.string.close)
}