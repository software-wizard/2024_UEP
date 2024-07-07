package pl.psi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.psi.creatures.EconomyCreature;
import pl.psi.enums.SkillEnum;
import pl.psi.skills.Skill;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
@Getter
@AllArgsConstructor
public class EconomyHero implements PropertyChangeListener {
    private final String name;
    private final List< EconomyCreature > creatures;
    private int maxMovePoints;
    private int currentMovePoints;
    private Resources resources;
    private final Map<SkillEnum, Skill> skills;

    public EconomyHero( String aName )
    {
        name = aName;
        creatures = new ArrayList<>();
        maxMovePoints = 10;
        currentMovePoints = maxMovePoints;
        resources = Resources.startRes();
        skills = new HashMap<>();

        assert creatures != null : "Creatures list should not be null in constructor";
        System.out.println("EconomyHero constructor: creatures initialized with size " + creatures.size());
    }

    @Override
    public void propertyChange( PropertyChangeEvent aEvent )
    {
        if( aEvent.getPropertyName()
            .equals( EconomyEngine.TURN_END ) )
        {
            currentMovePoints = maxMovePoints;
        }
    }

    void retrieveMovePoints( double aDistance )
    {
        currentMovePoints = (int)Math.ceil( aDistance );
    }

    public void addCreature(EconomyCreature aEconomyCreature) {
        creatures.add(aEconomyCreature);
    }

    public void changeResources(Resources resources) {
        this.resources = this.resources.addResources(resources);
    }

    public void addSkill(Skill aSkill) {
        SkillEnum skillName = aSkill.getSkillName();
        if(skills.containsKey(skillName)) {
            skills.get(skillName).upgradeLevel(aSkill.getLevel());
            return;
        }
        skills.put(aSkill.getSkillName(), aSkill);
    }

    public String skillsAsList() {
        StringBuilder sb = new StringBuilder();
        int size = skills.size();
        int i = 0;
        List<String> skillStrings = skills.values().stream().map(Skill::toString).sorted().collect(Collectors.toList());

        for (String skill : skillStrings) {
            sb.append(skill);
            if (i < size - 1) {
                sb.append(System.lineSeparator());
            }
            i++;
        }
        return sb.toString();
    }
    public void setResources(Resources resources) {
        this.resources = resources;
    }
}
