package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.client.SpiritBasedWorldVFXBuilder;
import com.sammy.malum.common.block.mana_mote.MoteOfManaBlockEntity;
import com.sammy.malum.common.block.mana_mote.SpiritMoteBlock;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;

import static com.sammy.malum.client.RenderUtils.drawCube;


public class MoteOfManaRenderer implements BlockEntityRenderer<MoteOfManaBlockEntity> {

    public static final ResourceLocation MOTE_OF_MANA_TEXTURE = MalumMod.malumPath("textures/block/mote_of_mana.png");

    public MoteOfManaRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MoteOfManaBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderQuad(blockEntityIn, poseStack);
    }

    public void renderQuad(MoteOfManaBlockEntity blockEntityIn, PoseStack poseStack) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        MalumSpiritType spiritType = ((SpiritMoteBlock) blockEntityIn.getBlockState().getBlock()).spiritType;

        var builder = SpiritBasedWorldVFXBuilder.create(spiritType)
                .setRenderType(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(MOTE_OF_MANA_TEXTURE));

        RenderUtils.CubeVertexData cubeVertexData = RenderUtils.makeCubePositions(1f);
        cubeVertexData.applyWobble(0, 0.5f, 0.015f);
        drawCube(poseStack, builder.setColor(spiritType.getPrimaryColor(), 0.86f), 1f, cubeVertexData);
        drawCube(poseStack, builder.setColor(spiritType.getSecondaryColor(), 0.6f), -0.92f, cubeVertexData);
        drawCube(poseStack, builder.setColor(spiritType.getPrimaryColor(), 0.5f), 1.12f, cubeVertexData);
        poseStack.popPose();
    }
}