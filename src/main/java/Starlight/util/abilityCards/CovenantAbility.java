/*
package Starlight.util.abilityCards;

import Starlight.cards.abstracts.AbstractAbilityCard;
import Starlight.util.AbilityManager;

import static Starlight.TheStarlightMod.makeID;

public class CovenantAbility extends AbstractAbilityCard {
    public final static String ID = makeID(CovenantAbility.class.getSimpleName());
    public static final AbilityManager.AbilityType TYPE = AbilityManager.AbilityType.COVENANT;

    public CovenantAbility() {
        super(ID, TYPE);
        baseInfo = info = TYPE.scale();
        baseSecondMagic = secondMagic = AbilityManager.getAbilityLevel(TYPE) * TYPE.scale();
    }

    @Override
    public void upp() {
        upgradeSecondMagic(TYPE.scale());
    }

    @Override
    public void upgradeAbility() {
        AbilityManager.addAbilityLevel(TYPE, 1);
    }
}
*/
