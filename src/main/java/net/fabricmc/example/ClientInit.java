package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("dsffsa");
        BlockEntityRendererRegistry.INSTANCE.register(ExampleMod.CLOCKWORK_BLOCK_ENTITY, SpinnyBlockEntityRenderer::new);
    }
}
