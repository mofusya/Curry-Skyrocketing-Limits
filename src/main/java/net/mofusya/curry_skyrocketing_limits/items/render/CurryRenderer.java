package net.mofusya.curry_skyrocketing_limits.items.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.mofusya.curry_skyrocketing_limits.items.CslItems;
import net.mofusya.curry_skyrocketing_limits.items.item.CurryItem;

public class CurryRenderer extends BlockEntityWithoutLevelRenderer {
    public CurryRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        var itemIngredients = CurryItem.getItemIngredients(itemStack);

        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(CslItems.CURRY_BASE.get()), ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, null, 0);

        poseStack.pushPose();
        poseStack.mulPose(Axis.XN.rotationDegrees(90));
        poseStack.translate(0, 0, 0.2);
        for (int i = 0; i < itemIngredients.size(); i++) {
            ItemStack itemIngredient = itemIngredients.get(i);
            poseStack.translate(0, 0, 0.5);
            poseStack.mulPose(Axis.XN.rotationDegrees(-30));
            Minecraft.getInstance().getItemRenderer().renderStatic(itemIngredient, ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, null, 0);
            poseStack.mulPose(Axis.XN.rotationDegrees(30));
        }
        poseStack.popPose();
    }
}
