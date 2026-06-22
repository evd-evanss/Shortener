package com.shortener.navigation.api

import androidx.compose.runtime.Composable
import kotlin.reflect.KClass

/**
 * Ponto de entrada de navegação exposto por um módulo de feature.
 *
 * Cada feature informa quais entradas de navegação ela oferece. A feature não
 * precisa testar manualmente se uma key pertence a ela; esse detalhe fica
 * encapsulado em [FeatureEntry].
 */
interface Feature {
    val entries: List<FeatureEntry<out AppNavKey>>
}

/**
 * Liga uma key tipada a uma tela da feature.
 *
 * O app usa [accepts] para encontrar a entrada correta a partir da key que
 * está no back stack do Navigation 3. Depois disso, [Content] renderiza a tela
 * usando a key já tipada internamente.
 */
class FeatureEntry<K : AppNavKey>(
    private val keyClass: KClass<K>,
    private val content: @Composable (K, AppNavigator) -> Unit,
) {
    fun accepts(key: AppNavKey): Boolean = keyClass.isInstance(key)

    @Composable
    fun Content(
        key: AppNavKey,
        navigator: AppNavigator,
    ) {
        @Suppress("UNCHECKED_CAST")
        content(key as K, navigator)
    }
}

/**
 * Cria uma entrada de navegação para a key [K].
 *
 * Usando esse helper, a feature declara diretamente qual key renderiza
 */
inline fun <reified K : AppNavKey> featureEntry(
    noinline content: @Composable (K, AppNavigator) -> Unit,
): FeatureEntry<K> = FeatureEntry(K::class, content)
