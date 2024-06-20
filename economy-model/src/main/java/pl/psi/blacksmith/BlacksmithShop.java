package pl.psi.blacksmith;


import lombok.Getter;
import pl.psi.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BlacksmithShop {

    private List<ShopItem> items;

    public BlacksmithShop() {
        items = new ArrayList<>();
        initializeItems();
    }

    private void initializeItems() {
        items.add(ShopItem.builder()
                .name("Amulet Mocy")
                .type(ItemType.MAGIC_ITEM)
                .requiredResources(pl.psi.Resources.builder().gold(200).build())
                .build());

        items.add(ShopItem.builder()
                .name("Szata Mędrcy")
                .type(ItemType.MAGIC_ITEM)
                .requiredResources(pl.psi.Resources.builder().gold(150).build())
                .build());

        items.add(ShopItem.builder()
                .name("Miecz Ognia")
                .type(ItemType.MAGIC_ITEM)
                .requiredResources(pl.psi.Resources.builder().gold(300).build())
                .build());

        items.add(ShopItem.builder()
                .name("Łucznicy")
                .type(ItemType.MILITARY_UNIT)
                .requiredResources(pl.psi.Resources.builder().gold(50).build())
                .build());

        items.add(ShopItem.builder()
                .name("Piechurzy")
                .type(ItemType.MILITARY_UNIT)
                .requiredResources(pl.psi.Resources.builder().gold(30).build())
                .build());

        items.add(ShopItem.builder()
                .name("Kawaleria")
                .type(ItemType.MILITARY_UNIT)
                .requiredResources(pl.psi.Resources.builder().gold(100).build())
                .build());

        items.add(ShopItem.builder()
                .name("Piorun")
                .type(ItemType.SPELL)
                .requiredResources(pl.psi.Resources.builder().gold(80).build())
                .build());

        items.add(ShopItem.builder()
                .name("Ognista Kula")
                .type(ItemType.SPELL)
                .requiredResources(pl.psi.Resources.builder().gold(120).build())
                .build());

        items.add(ShopItem.builder()
                .name("Leczenie")
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
}

