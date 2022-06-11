package me.meowcher.silivia.core

import me.meowcher.silivia.impl.*
import meteordevelopment.meteorclient.MeteorClient
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.systems.modules.Modules
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method

class Balthasar
{
    object Init : Melchior
    {
        private fun modsInitialize(vararg Features: Module?)
        {
            for (modules in Features)
            {
                Modules.get().add(modules)
            }
        }

        fun modsInitialize()
        {
            modsInitialize(
                KillEffects(),
                NoInteract(),
                NightMode(),
                AutoLogin(),
                MultiTask(),
                TickShift(),
                CartBomb(),
                FakeKick(),
                NoSwing(),
                Changer(),
                AutoKit(),
                Prefix()
            )

            logger.info("Registration of modules is completed!")
        }

        fun eventBusInitialize()
        {
            MeteorClient.EVENT_BUS.registerLambdaFactory("me.meowcher.silivia")
            { lookupInMethod: Method, klass: Class<*>? ->
                lookupInMethod.invoke(null, klass, MethodHandles.lookup()) as MethodHandles.Lookup
            }

            logger.info("Event Bus registration is completed!")
        }
    }
}
