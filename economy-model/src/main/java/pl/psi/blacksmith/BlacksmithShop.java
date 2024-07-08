package pl.psi.blacksmith;

import lombok.Getter;
import pl.psi.Resources;
import pl.psi.creatures.*;
import pl.psi.EconomyHero;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.enums.ItemType;
import pl.psi.enums.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class BlacksmithShop {

    private List<ShopItem> items;

    public BlacksmithShop() {
        items = new ArrayList<>();
        initializeItems();
    }

    private void initializeItems() {
        items.add(ShopItem.builder()
                .name("Rib Cage")
                .type(ItemType.MAGIC_ITEM)
                .requiredResources(pl.psi.Resources.builder().gold(200).build())
                .build());

        items.add(ShopItem.builder()
                .name("Skull Helmet")
                .type(ItemType.MAGIC_ITEM)
                .requiredResources(pl.psi.Resources.builder().gold(150).build())
                .build());

        items.add(ShopItem.builder()
                .name("Angel Wings")
                .type(ItemType.MAGIC_ITEM)
                .requiredResources(pl.psi.Resources.builder().gold(300).build())
                .build());

        items.add(ShopItem.builder()
                .name("First Aid Tent")
                .type(ItemType.MILITARY_UNIT)
                .requiredResources(pl.psi.Resources.builder().gold(50).build())
                .build());

        items.add(ShopItem.builder()
                .name("Ballista")
                .type(ItemType.MILITARY_UNIT)
                .requiredResources(pl.psi.Resources.builder().gold(30).build())
                .build());

        items.add(ShopItem.builder()
                .name("Catapult")
                .type(ItemType.MILITARY_UNIT)
                .requiredResources(pl.psi.Resources.builder().gold(100).build())
                .build());

        items.add(ShopItem.builder()
                .name("Lightning")
                .type(ItemType.SPELL)
                .requiredResources(pl.psi.Resources.builder().gold(80).build())
                .build());

        items.add(ShopItem.builder()
                .name("Fireball")
                .type(ItemType.SPELL)
                .requiredResources(pl.psi.Resources.builder().gold(120).build())
                .build());

        items.add(ShopItem.builder()
                .name("Healing")
                .type(ItemType.SPELL)
                .requiredResources(pl.psi.Resources.builder().gold(50).build())
                .build());
    }

    public List<ShopItem> getItemsByType(ItemType type) {
        List<ShopItem> filteredItems = new ArrayList<>();
        for (ShopItem item : items) {
            if (item.getType() == type) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public void addCreatureToHero(EconomyHero economyHero, ShopItem item) {
        EconomyCreature newCreature = createEconomyCreature(item);
        if (newCreature != null) {
            economyHero.addCreature(newCreature);
        }
    }

    private EconomyCreature createEconomyCreature(ShopItem item) {
        switch (item.getName()) {
            case "Ballista":
                Ballista ballista = new Ballista.Builder()
                        .statistic(CreatureStatistic.BALLISTA)
                        .calculator(new DefaultDamageCalculator(new Random()))
                        .amount(1)
                        .creatureType(CreatureTypeEnum.GROUND)
                        .attackType(AttackTypeEnum.RANGE)
                        .build();
                return convertToEconomyCreature(ballista);
            case "Catapult":
                Catapult catapult = new Catapult.Builder()
                        .statistic(CreatureStatistic.CATAPULT)
                        .calculator(new DefaultDamageCalculator(new Random()))
                        .amount(1)
                        .creatureType(CreatureTypeEnum.GROUND)
                        .attackType(AttackTypeEnum.MELEE)
                        .build();
                return convertToEconomyCreature(catapult);
            case "First Aid Tent":
                FirstAidTent firstAidTent = new FirstAidTent.Builder()
                        .statistic(CreatureStatistic.FIRST_AID_TENT)
                        .calculator(new DefaultDamageCalculator(new Random()))
                        .amount(1)
                        .creatureType(CreatureTypeEnum.GROUND)
                        .attackType(AttackTypeEnum.MELEE)
                        .build();
                return convertToEconomyCreature(firstAidTent);
            default:
                System.err.println("Unknown item: " + item.getName());
                return null;
        }
    }

    private EconomyCreature convertToEconomyCreature(Creature creature) {
        Random random = new Random();
        int randomMoraleValue = random.nextInt(6);
        return new EconomyCreature(
                (CreatureStatistic) creature.getStats(),
                false,
                1,
                100,
                randomMoraleValue
        );
    }

    public String updateCreaturesStatus(EconomyHero economyHero) {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of units: " + economyHero.getCreatures().size() + "\n\n");
        for (EconomyCreature creature : economyHero.getCreatures()) {
            sb.append("Name: ").append(creature.getStats().getName()).append("\n")
                    .append("Tier: ").append(creature.getTier()).append("\n")
                    .append("Range: ").append(creature.getStats().getDamage()).append("\n")
                    .append("Damage: ").append(creature.getStats().getAttack()).append("\n")
                    .append("Armor: ").append(creature.getStats().getArmor()).append("\n")
                    .append("Morals: ").append(creature.getMoraleValue()).append("\n")
                    .append("-----------------\n");
        }
        return sb.toString();
    }
}
