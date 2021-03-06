package me.nullium21.autorail.mixin;

import me.nullium21.autorail.base.interfaces.ARIdentifiable;
import me.nullium21.autorail.mod.Autorail;
import me.nullium21.autorail.mod.registry.ARRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;

import static me.nullium21.autorail.mod.Autorail.REGISTRIES;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin {

    private static final Map<String, String> MODEL_PATHS = Map.of(
            "rail_signal", "block/rail_signal/base",
            "chain_signal", "block/rail_signal/base",
            "manual_signal", "block/rail_signal/base"
    );

    @Inject(method = "loadModelFromJson",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/resource/ResourceManager;getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"),
            cancellable = true)
    public void loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> ret) {
        if (!id.getNamespace().equals(Autorail.MODID)) return;

        MinecraftClient minecraft = MinecraftClient.getInstance();
        ResourceManager resman = minecraft.getResourceManager();

        String name = id.getPath().split("/")[id.getPath().split("/").length-1]; // last

        for (ARRegistry<?> r : REGISTRIES) {
            Collection<? extends ARIdentifiable> all = r.getAll();

            r.getAll().stream()
                    .filter(e -> e.getIdentifier().equals(name))
                    .filter(e -> MODEL_PATHS.containsKey(e.getIdentifier()))
                    .forEach(entry -> {
                        String modelPath = "models/" + MODEL_PATHS.get(entry.getIdentifier()) + ".json";

                        try {
                            Resource res = resman.getResource(new Identifier(Autorail.MODID, modelPath));
                            InputStreamReader isr = new InputStreamReader(res.getInputStream());

                            JsonUnbakedModel jum = JsonUnbakedModel.deserialize(isr);
                            jum.id = id.toString();

                            ret.setReturnValue(jum);
                            ret.cancel();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
}
