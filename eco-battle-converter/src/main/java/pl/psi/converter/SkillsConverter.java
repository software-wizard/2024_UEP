package pl.psi.converter;

import pl.psi.enums.SkillEnum;
import pl.psi.skills.EcoSkill;
import skills.*;

import java.util.HashMap;
import java.util.Map;

public class SkillsConverter {
    public HashMap ecoBattleSkillMap;
    // Skille mozna dostac z mapy tylko te, ktorych jeszcze nie ma Hero i tylko na 1lvl, wiec narazie tylko tak jest w tej klasie,
    // https://mightandmagic.fandom.com/wiki/List_of_adventure_map_structures_in_Heroes_III
    // Jak dodamy nowe skille do projektu to trzeba tu dolozyc do hashmapy.
    public SkillsConverter() {
        Map<SkillEnum, BattleSkill> ecoBattleSkillMap = new HashMap<>();
        ecoBattleSkillMap.put(SkillEnum.ARCHERY, new ArcherySkill(1));
        ecoBattleSkillMap.put(SkillEnum.OFFENSE, new OffenseSkill(1));
        ecoBattleSkillMap.put(SkillEnum.ARMORER, new ArmorerSkill(1));
        ecoBattleSkillMap.put(SkillEnum.LEADERSHIP, new LeadershipSkill(1));
    }
    public BattleSkill convert(EcoSkill aEcoSkill) {
        return (BattleSkill) ecoBattleSkillMap.get(aEcoSkill.getSkillName());
    }

}
