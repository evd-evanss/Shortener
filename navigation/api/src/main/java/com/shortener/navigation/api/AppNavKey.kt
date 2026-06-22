package com.shortener.navigation.api

import androidx.navigation3.runtime.NavKey

/**
 * Tipo base para todas as keys usadas no Navigation 3 do app.
 *
 * As keys concretas ficam no módulo da feature dona da tela. Por exemplo,
 * a splash declara a key da splash e a feature de encurtador declara a key
 * do encurtador. Assim o módulo `:navigation:api` continua sendo apenas
 * contrato, sem conhecer telas ou fluxos específicos.
 */
interface AppNavKey : NavKey
