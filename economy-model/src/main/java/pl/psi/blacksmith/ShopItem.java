package pl.psi.blacksmith;

import lombok.Builder;
import lombok.Value;
import pl.psi.Resources;
import pl.psi.enums.ItemType;

@Value
@Builder
public class ShopItem {
    public static ItemType ItemType;
    private final String name;
    private final ItemType type;
    private final Resources requiredResources;

    @Override
    public String toString() {
        return name + " (" + type + ") - " + requiredResources.getGold() + " gold";
    }

    public static ShopItemBuilder builder() {
        return new ShopItemBuilder();
    }

    public static class ShopItemBuilder {
        private String name;
        private ItemType type;
        private Resources requiredResources;

        ShopItemBuilder() {}

        public ShopItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ShopItemBuilder type(ItemType type) {
            this.type = type;
            return this;
        }

        public ShopItemBuilder requiredResources(Resources requiredResources) {
            this.requiredResources = requiredResources;
            return this;
        }

        public ShopItem build() {
            return new ShopItem(name, type, requiredResources);
        }
    }
}
