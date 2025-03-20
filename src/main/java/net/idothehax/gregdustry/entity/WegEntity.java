package net.idothehax.gregdustry.entity;

import net.idothehax.gregdustry.init.GregdustryModEntities;
import net.mcreator.thediffyexperience.init.TheDiffyExperienceModItems;
import net.mcreator.thediffyexperience.procedures.CorruptionProcedureProcedure;
import net.mcreator.thediffyexperience.procedures.GregOnSpawnProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController.State;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WegEntity extends Monster implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT;
    public static final EntityDataAccessor<String> ANIMATION;
    public static final EntityDataAccessor<String> TEXTURE;
    private final AnimatableInstanceCache cache;
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    public String animationprocedure;
    String prevAnim;

    //public WegEntity(PlayMessages.SpawnEntity packet, Level world) {
    //    this(GregdustryModEntities.WEG.get(), world);
    //}

    public WegEntity(EntityType<WegEntity> type, Level world) {
        super(type, world);
        this.cache = GeckoLibUtil.createInstanceCache(this);
        this.animationprocedure = "empty";
        this.prevAnim = "empty";
        this.xpReward = 5;
        this.setNoAi(false);
        this.setMaxUpStep(0.6F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "weg");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return (String)this.entityData.get(TEXTURE);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0, false) {
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double)(this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth());
            }
        });
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
        this.goalSelector.addGoal(6, new FloatGoal(this));
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        this.spawnAtLocation(new ItemStack((ItemLike)TheDiffyExperienceModItems.GREG_SOUL.get()));
    }

    public SoundEvent getAmbientSound() {
        return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("the_diffy_experience:jumpscare1"));
    }

    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound((SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("the_diffy_experience:gregwalk")), 0.15F, 1.0F);
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("the_diffy_experience:greghit"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("the_diffy_experience:gregdeath"));
    }

    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_FIRE)) {
            return false;
        } else if (source.getDirectEntity() instanceof AbstractArrow) {
            return false;
        } else if (source.is(DamageTypes.FALL)) {
            return false;
        } else {
            return source.is(DamageTypes.EXPLOSION) ? false : super.hurt(source, amount);
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture")) {
            this.setTexture(compound.getString("Texture"));
        }

    }

    public void baseTick() {
        super.baseTick();
        CorruptionProcedureProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        this.refreshDimensions();
    }

    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale(1.0F);
    }

    public void playerTouch(Player sourceentity) {

    }

    public static void init() {
        SpawnPlacements.register((EntityType)GregdustryModEntities.WEG.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            return GregOnSpawnProcedure.execute(world, (double)x, (double)y, (double)z);
        });
        DungeonHooks.addDungeonMob((EntityType)GregdustryModEntities.WEG.get(), 180);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 1.1);
        builder = builder.add(Attributes.MAX_HEALTH, 35.0);
        builder = builder.add(Attributes.ARMOR, 4.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0);
        return builder;
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if (!event.isMoving() && event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F) {
                return this.isDeadOrDying() ? event.setAndContinue(RawAnimation.begin().thenPlay("animation.model.attack")) : event.setAndContinue(RawAnimation.begin().thenLoop("animation.model.idle"));
            } else {
                return event.setAndContinue(RawAnimation.begin().thenLoop("animation.model.walk"));
            }
        } else {
            return PlayState.STOP;
        }
    }

    private PlayState attackingPredicate(AnimationState event) {
        double d1 = this.getX() - this.xOld;
        double d0 = this.getZ() - this.zOld;
        float velocity = (float)Math.sqrt(d1 * d1 + d0 * d0);
        if (this.getAttackAnim(event.getPartialTick()) > 0.0F && !this.swinging) {
            this.swinging = true;
            this.lastSwing = this.level().getGameTime();
        }

        if (this.swinging && this.lastSwing + 7L <= this.level().getGameTime()) {
            this.swinging = false;
        }

        if (this.swinging && event.getController().getAnimationState() == State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("animation.model.attack"));
        } else {
            return PlayState.CONTINUE;
        }
    }

    private PlayState procedurePredicate(AnimationState event) {
        if ((this.animationprocedure.equals("empty") || event.getController().getAnimationState() != State.STOPPED) && (this.animationprocedure.equals(this.prevAnim) || this.animationprocedure.equals("empty"))) {
            if (this.animationprocedure.equals("empty")) {
                this.prevAnim = "empty";
                return PlayState.STOP;
            }
        } else {
            if (!this.animationprocedure.equals(this.prevAnim)) {
                event.getController().forceAnimationReset();
            }

            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        }

        this.prevAnim = this.animationprocedure;
        return PlayState.CONTINUE;
    }

    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
        }

    }

    public String getSyncedAnimation() {
        return (String)this.entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        this.entityData.set(ANIMATION, animation);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController[]{new AnimationController(this, "movement", 4, this::movementPredicate)});
        data.add(new AnimationController[]{new AnimationController(this, "attacking", 4, this::attackingPredicate)});
        data.add(new AnimationController[]{new AnimationController(this, "procedure", 4, this::procedurePredicate)});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    static {
        SHOOT = SynchedEntityData.defineId(WegEntity.class, EntityDataSerializers.BOOLEAN);
        ANIMATION = SynchedEntityData.defineId(WegEntity.class, EntityDataSerializers.STRING);
        TEXTURE = SynchedEntityData.defineId(WegEntity.class, EntityDataSerializers.STRING);
    }
}

