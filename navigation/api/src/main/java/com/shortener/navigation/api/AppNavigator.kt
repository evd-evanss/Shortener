package com.shortener.navigation.api

/**
 * Contrato simples de navegação exposto para as telas das features.
 *
 * As features usam essa interface para pedir mudanças de navegação sem
 * conhecer como o app guarda ou renderiza o back stack do Navigation 3.
 * A implementação concreta fica no módulo `:app`.
 */
interface AppNavigator {
    /**
     * Adiciona [key] no topo do back stack atual.
     */
    fun navigate(key: AppNavKey)

    /**
     * Limpa o back stack atual e inicia novamente a partir de [key].
     */
    fun replace(key: AppNavKey)

    /**
     * Remove a tela atual quando existe uma tela anterior para voltar.
     */
    fun pop()
}
