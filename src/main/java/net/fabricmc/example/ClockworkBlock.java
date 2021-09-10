package net.fabricmc.example;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class ClockworkBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public ClockworkBlock() {
        super(AbstractBlock.Settings.of(Material.STONE).nonOpaque());
        setDefaultState(getStateManager().getDefaultState().with(ON, false));
    }
    static int ticker = 0;
    static int sounder = 0;
    static int tickerFinal = 50;
    static float randomAngelOffset = 0.0f;
    public static BooleanProperty ON = BooleanProperty.of("on");

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }


    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ON);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState();
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        Direction direction = hit.getSide();
//        Direction direction1 = state.get(HORIZONTAL_FACING);
//        if(direction == direction1){
        Random random = new Random();
        tickerFinal = (random.nextInt(200)+80);
        randomAngelOffset = random.nextFloat()*360;
        SpinnyBlockEntityRenderer.multiply = random.nextInt(100)+10;
            world.setBlockState(pos, state.with(ON, true));
            return ActionResult.SUCCESS;
    }
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return !world.isClient ? ClockworkBlock::tick : null;
    }

    private static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {
        SoundEvent soundEventClick = SoundEvents.BLOCK_LEVER_CLICK;
        sounder++;
        if(sounder >= 10){
            //world.playSound(null, blockPos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.4F, 1.75F);
            sounder=0;
        }
        Random random = new Random();
        if(blockState.get(ON)){
            ticker ++;
            if(ticker+random.nextInt(30)+3 >= tickerFinal){
                ticker=0;
                world.setBlockState(blockPos, blockState.with(ON, false));
            }
        }
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ExampleMod.CLOCKWORK_BLOCK_ENTITY.instantiate(pos, state);
    }
}