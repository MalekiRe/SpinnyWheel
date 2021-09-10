package net.fabricmc.example;

import dev.monarkhes.myron.api.Myron;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

import java.util.Random;

public class SpinnyBlockEntityRenderer<T extends ClockworkBlockEntity> implements BlockEntityRenderer<ClockworkBlockEntity> {
    private static final Identifier MODEL = new Identifier("spinny_wheel", "models/misc/fortune_wheel");

    public SpinnyBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }
    float myAngle = 0;
    float prevRandom = 0;
    Random random = new Random();
    static float multiply;
    @Override
    public void render(ClockworkBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        int r = random.nextInt(100);
        int tickerFinal = ClockworkBlock.tickerFinal;
        BakedModel model = Myron.getModel(MODEL);
        if(prevRandom != ClockworkBlock.randomAngelOffset) {
            prevRandom = ClockworkBlock.randomAngelOffset;
            myAngle = prevRandom;
        }
        if (model != null) {
            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        if(entity.getWorld().getBlockState(entity.getPos()).get(ClockworkBlock.ON)) {
            float time = (entity.getWorld() == null ? 0 : entity.getWorld().getTime()) + tickDelta + r;
            if(ClockworkBlock.ticker != 0 && (((float)tickerFinal) /(float)ClockworkBlock.ticker)!= 0) {
                myAngle = (multiply * (float)tickerFinal) /((float) ClockworkBlock.ticker) + myAngle * 0.8f;
            }


        }
        System.out.println(myAngle);
                matrices.push();
                matrices.translate(0, 0, 0.2f);
                matrices.push();
                Quaternion quaternion = Vec3f.NEGATIVE_Z.getDegreesQuaternion(myAngle);
                matrices.translate(0.5f, 0.5f, 0);
                matrices.multiply(quaternion);
                matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(180));
                //matrices.translate(0, -Math.sin(time), 0);
                MatrixStack.Entry entry = matrices.peek();

                model.getQuads(null, null, entity.getWorld().random).forEach(quad -> {
                    consumer.quad(entry, quad, 1F, 1F, 1F, light, overlay);
                });
                matrices.pop();
                matrices.pop();


        }
    }
    /*
    public void renderLayer(int layerNumber, float time, Identifier id, VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, int light, Direction facing)
    {
        matrixStack.push();
        if(layerNumber < 3)
            doRotationBasedOnFacing(matrixStack, facing, time);
        else
            doRotationBasedOnFacing(matrixStack, facing.getOpposite(), time);
        float x1 = 0;
        float y1 = 0;
        float z1 = 0;

        float x2 = 0, y2 = 0, z2 = 0;
        float shrinkSize = 0.0F;
        if(layerNumber >= 3)
        {
            shrinkSize = 0.2F;
        }
        float distanceSize = 0.001F;
        float nearBlock = 0.99F;
        switch (facing) {
            case NORTH : z1+=distanceSize; x2 = shrinkSize; y2 = shrinkSize; break;
            case SOUTH : z1-=distanceSize; x2 = shrinkSize; y2 = shrinkSize; break;
            case EAST : x1+=distanceSize; z2 = shrinkSize;  y2 = shrinkSize; break;
            case WEST: x1-=distanceSize; z2 = shrinkSize; y2 = shrinkSize; break;
            case UP : y1-=distanceSize; x2 = shrinkSize; z2 = shrinkSize; break;
            case DOWN: y1+=distanceSize; x2 = shrinkSize; z2 = shrinkSize; break;
        }

        x1 *= layerNumber; y1 *= layerNumber; z1 *= layerNumber;
        switch (facing) {
            case NORTH : z1 += nearBlock;break;
            case SOUTH : z1 -= nearBlock; break;
            case EAST: x1 -= nearBlock; break;
            case WEST: x1 += nearBlock; break;
            case UP : y1 -= nearBlock; break;
            case DOWN : y1 += nearBlock; break;
        }

        /*
        x2 *= layerNumber; x2 /= 1;
        y2 *= layerNumber; y2 /= 1;
        z2 *= layerNumber; z2 /= 1;*/
    /*
        if (!id.equals(NOTHING_ID)) {
            DRenderUtil.renderTexturedFace(facing, x2+x1, y2+y1, z2+z1, x1+1-x2, y1+1-y2, z1+1-z2, vertexConsumerProvider, matrixStack, id, light);
        }

        matrixStack.pop();
    }
    public void doRotationBasedOnFacing(MatrixStack matrixStack, Direction facing, float time) {
        switch(facing)
        {
            case SOUTH:
                doRotationAndTranslation(matrixStack, 0.5, 0.5, 0, DQuaternion.rotationByDegrees(new Vec3d(0, 0, 1), time).toMcQuaternion());
                break;
            case NORTH:
                doRotationAndTranslation(matrixStack, 0.5, 0.5, 0, DQuaternion.rotationByDegrees(new Vec3d(0, 0, -1), time).toMcQuaternion());
                break;
            case EAST:
                doRotationAndTranslation(matrixStack, 0, 0.5, 0.5, DQuaternion.rotationByDegrees(new Vec3d(1, 0, 0), time).toMcQuaternion());
                break;
            case WEST:
                doRotationAndTranslation(matrixStack, 0, 0.5, 0.5, DQuaternion.rotationByDegrees(new Vec3d(-1, 0, 0), time).toMcQuaternion());
                break;
            default: System.out.println("default");
        }
    }
    */
    public void doRotationBasedOnFacing(MatrixStack matrixStack, Direction facing, float time) {
//        switch(facing)
//        {
//            case SOUTH:
//                doRotationAndTranslation(matrixStack, 0.5, 0.5, 0, DQuaternion.rotationByDegrees(new Vec3d(0, 0, 1), time).toMcQuaternion());
//                break;
//            case NORTH:
//                doRotationAndTranslation(matrixStack, 0.5, 0.5, 0, DQuaternion.rotationByDegrees(new Vec3d(0, 0, -1), time).toMcQuaternion());
//                break;
//            case EAST:
//                doRotationAndTranslation(matrixStack, 0, 0.5, 0.5, DQuaternion.rotationByDegrees(new Vec3d(1, 0, 0), time).toMcQuaternion());
//                break;
//            case WEST:
//                doRotationAndTranslation(matrixStack, 0, 0.5, 0.5, DQuaternion.rotationByDegrees(new Vec3d(-1, 0, 0), time).toMcQuaternion());
//                break;
//            default: System.out.println("default");
//        }
        doRotationAndTranslation(matrixStack, 0.5, 0.5, 0, Quaternion.IDENTITY);
    }
    public void doRotationAndTranslation(MatrixStack matrixStack, double x, double y, double z, Quaternion quaternion)
    {
        matrixStack.translate(x, y, z);
        matrixStack.multiply(quaternion);
        matrixStack.translate(-x, -y, -z);
    }
    public float getXFromDegree(float x1, float x2, float y1, float y2, float time)
    {
        return (float) ((Math.cos(time)*(x1-x2)) - (Math.sin(time)*(y1-y2))+x2);
    }
    public float getYFromDegree(float x1, float x2, float y1, float y2, float time)
    {
        return (float) ((Math.sin(time)*(x1-x2)) + (Math.cos(time)*(y1-y2))+y2);
    }


}
