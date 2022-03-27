package silivia

import silivia.impl.*
import silivia.impl.fakekick.FakeKick
import silivia.impl.killeffects.KillEffects
import silivia.impl.nightmode.NightMode
import silivia.utils.Reference
import meteordevelopment.meteorclient.MeteorClient
import meteordevelopment.meteorclient.addons.MeteorAddon
import meteordevelopment.meteorclient.systems.modules.Category
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.systems.modules.Modules
import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method

class Silivia : MeteorAddon()
{
    object Special
    {
        val Category : Category = Category(Reference.Name)
        val logger : Logger = LoggerFactory.getLogger(Reference.Name)
        val minecraft : MinecraftClient = MinecraftClient.getInstance()
    }
    override fun onInitialize()
    {
        Special.logger.info("Initializing " + Reference.Name + " " + Reference.Version + "!")

        MeteorClient.EVENT_BUS.registerLambdaFactory("silivia") { lookupInMethod : Method, klass :
        Class<*>? -> lookupInMethod.invoke(null, klass, MethodHandles.lookup()) as MethodHandles.Lookup }

        registerModules(MultiTask(), FakeKick(), NightMode(), AutoLogin(), AutoKit(), KillEffects())
    }
    override fun onRegisterCategories()
    {
        Modules.registerCategory(Special.Category)
    }
    private fun registerModules(vararg Features : Module?)
    {
        for (modules in Features)
        {
            Modules.get().add(modules)
        }
    }
}
