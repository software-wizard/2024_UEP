package pl.psi.gui;

import lombok.Getter;
import pl.psi.EconomyHero;
import pl.psi.Resources;
import pl.psi.blacksmith.BlacksmithShop;
import pl.psi.blacksmith.ShopItem;

public class BlacksmithController {

    private EconomyHero economyHero;
    private EcoController ecoController;
    @Getter
    private BlacksmithShop shop;

    public BlacksmithController(EconomyHero economyHero, EcoController ecoController) {
        this.economyHero = economyHero;
        this.ecoController = ecoController;
        this.shop = new BlacksmithShop();
    }

    public void buyItem(ShopItem item) {
        Resources requiredResources = item.getRequiredResources();

        if (economyHero.getResources().hasEnough(requiredResources)) {
            economyHero.setResources(economyHero.getResources().subtract(requiredResources));
            ecoController.updateAllResourcesLabel();
            System.out.println(economyHero.getResources().getGold());
        } else {
            System.out.println("Nie masz wystarczająco zasobów do zakupu " + item.getName());
        }
    }

}
