package net.mofusya.curry_skyrocketing_limits.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.curry_skyrocketing_limits.C;
import net.mofusya.curry_skyrocketing_limits.accessor.FoodPropertiesAccessor;
import net.mofusya.curry_skyrocketing_limits.curryingredient.CurryIngredient;
import net.mofusya.curry_skyrocketing_limits.curryingredient.CurryIngredientManager;
import net.mofusya.curry_skyrocketing_limits.items.render.CurryRenderer;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class CurryItem extends Item {
    public CurryItem(Properties build) {
        super(build.food(defaultFoodProperties()));
    }

    private static void addItemIngredient(ItemStack itemStack, ItemStack ingredient) {
        if (itemStack.getItem() instanceof CurryItem) {
            ResourceLocation ingredientId = ForgeRegistries.ITEMS.getKey(ingredient.getItem());
            if (ingredientId == null) return;

            CompoundTag ingredientTag = new CompoundTag();
            ingredientTag.putString(C.ITEM, ingredientId.toString());
            ingredientTag.put(C.TAGS, ingredient.getOrCreateTag());

            ListTag ingredients = itemStack.getOrCreateTag().getList(C.ITEM_INGREDIENTS, Tag.TAG_COMPOUND);
            ingredients.add(ingredientTag);
            itemStack.getOrCreateTag().put(C.ITEM_INGREDIENTS, ingredients);
        }
    }

    private static void addFluidIngredient(ItemStack itemStack, ResourceLocation ingredient) {
        if (itemStack.getItem() instanceof CurryItem) {
            ListTag ingredients = itemStack.getOrCreateTag().getList(C.FLUID_INGREDIENTS, Tag.TAG_STRING);
            ingredients.add(StringTag.valueOf(ingredient.toString()));
            itemStack.getOrCreateTag().put(C.FLUID_INGREDIENTS, ingredients);
        }
    }

    public static List<ItemStack> getItemIngredients(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CurryItem) {
            return itemStack.getOrCreateTag().getList(C.ITEM_INGREDIENTS, Tag.TAG_COMPOUND).stream().map(tag -> (CompoundTag) tag).map(ingredientTag -> {
                Item ingredientItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ingredientTag.getString(C.ITEM)));
                if (ingredientItem == null) return ItemStack.EMPTY;

                ItemStack ingredientItemStack = new ItemStack(ingredientItem);
                ingredientItemStack.setTag(ingredientTag.getCompound(C.TAGS));
                return ingredientItemStack;
            }).toList();
        } else {
            return List.of();
        }
    }

    public static List<ResourceLocation> getItemIngredientsRL(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CurryItem) {
            return itemStack.getOrCreateTag().getList(C.ITEM_INGREDIENTS, Tag.TAG_COMPOUND).stream().map(tag -> (CompoundTag) tag).map(ingredientTag -> {
                return new ResourceLocation(ingredientTag.getString(C.ITEM));
            }).toList();
        } else {
            return List.of();
        }
    }

    public static List<ResourceLocation> getFluidIngredients(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CurryItem) {
            return itemStack.getOrCreateTag().getList(C.FLUID_INGREDIENTS, Tag.TAG_STRING).stream().map(Tag::getAsString).map(ResourceLocation::new).toList();
        } else {
            return List.of();
        }
    }

    public static FoodProperties defaultFoodProperties() {
        return new FoodProperties.Builder().nutrition(0).build();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);

        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new CurryRenderer();
            }
        });;
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack itemStack, @Nullable LivingEntity entity) {
        FoodProperties foodProperties = defaultFoodProperties();
        FoodPropertiesAccessor foodPropertiesAccessor = (FoodPropertiesAccessor) foodProperties;

        getItemIngredientsRL(itemStack).stream().map(ingredientId -> CurryIngredientManager.get(ingredientId, CurryIngredientManager.Type.ITEMS)).forEach(ingredient -> {
            if (ingredient == null) return;
            foodPropertiesAccessor.addNutrition(ingredient.nutrition()).addSaturationModifier(ingredient.saturationModifier());
        });

        getFluidIngredients(itemStack).stream().map(ingredientId -> CurryIngredientManager.get(ingredientId, CurryIngredientManager.Type.FLUID)).forEach(ingredient -> {
            if (ingredient == null) return;
            foodPropertiesAccessor.addNutrition(ingredient.nutrition()).addSaturationModifier(ingredient.saturationModifier());
        });

        if (entity != null && entity.level().isClientSide) {
            getItemIngredients(itemStack).forEach(ingredient -> {
                if (!ingredient.isEdible()) return;
                FoodProperties ingredientFoodProperties = ingredient.getFoodProperties(entity);
                if (ingredientFoodProperties == null) return;

                foodPropertiesAccessor.addNutrition(ingredientFoodProperties.getNutrition());
            });
        }

        return foodPropertiesAccessor.cast$currySL();
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (level instanceof ServerLevel server) {
            for (ResourceLocation itemIngredient : getItemIngredientsRL(itemStack)) {
                CurryIngredient ingredient = CurryIngredientManager.get(itemIngredient, CurryIngredientManager.Type.ITEMS);
                if (ingredient == null) continue;
                ingredient.onEat(entity, server, itemStack);
            }

            for (ItemStack itemIngredient : getItemIngredients(itemStack)) {
                if (!itemIngredient.isEdible()) continue;
                itemIngredient.finishUsingItem(level, entity);
            }

            for (ResourceLocation fluidIngredient : getFluidIngredients(itemStack)) {
                CurryIngredient ingredient = CurryIngredientManager.get(fluidIngredient, CurryIngredientManager.Type.FLUID);
                if (ingredient == null) continue;
                ingredient.onEat(entity, server, itemStack);
            }
        }
        return super.finishUsingItem(itemStack, level, entity);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack itemStack, ItemEntity entity) {
        Level level = entity.level();
        if (level.isClientSide) return super.onEntityItemUpdate(itemStack, entity);
        FluidType fluidType = level.getFluidState(entity.blockPosition()).getType().getFluidType();
        if (fluidType.isAir()) return super.onEntityItemUpdate(itemStack, entity);

        ResourceLocation fluidIngredient = ForgeRegistries.FLUID_TYPES.get().getKey(fluidType);
        if (!getFluidIngredients(itemStack).contains(fluidIngredient)) {
            addFluidIngredient(itemStack, fluidIngredient);
        } else {
            List<ItemEntity> pItemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(entity.blockPosition()).inflate(0.2f));
            for (ItemEntity pItemEntity : pItemEntities) {
                if (pItemEntity == null || pItemEntity.is(entity)) continue;
                ItemStack pItemStack = pItemEntity.getItem();
                if (pItemStack.isEmpty()) continue;

                addItemIngredient(itemStack, pItemStack);
                pItemStack.shrink(1);
                pItemEntity.setItem(pItemStack);
                break;
            }
        }

        return super.onEntityItemUpdate(itemStack, entity);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);

        var itemIngredients = getItemIngredients(itemStack);
        if (!itemIngredients.isEmpty()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("item.curry_skyrocketing_limits.curry.ingredients"));
            for (ItemStack ingredient : itemIngredients) {
                MutableComponent ingredientTooltip = Component.literal(" - ").append(ingredient.getItem().getName(ingredient));
                if (ingredient.hasCustomHoverName()) {
                    ingredientTooltip.append(" ").append(ingredient.getHoverName());
                }
                tooltip.add(ingredientTooltip.withStyle(ChatFormatting.DARK_GRAY));
            }
        }

        var fluidIngredients = getFluidIngredients(itemStack);
        if (!fluidIngredients.isEmpty()){
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("item.curry_skyrocketing_limits.curry.roux_ingredients"));
            for (ResourceLocation ingredient : fluidIngredients) {
                tooltip.add(Component.literal(" - ").append(Component.translatable(ingredient.toLanguageKey("block"))).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }
}