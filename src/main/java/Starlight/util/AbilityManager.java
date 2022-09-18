package Starlight.util;

import Starlight.powers.abilities.*;
import Starlight.relics.interfaces.AbilityProvidingRelic;
import Starlight.ui.AbilityButton;
import Starlight.util.abilityCards.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.HashMap;

public class AbilityManager {
    public enum AbilityType {
        FISTICUFFS(1, 2),
        BRUISER(1, 20),
        PILE_BUNKER(1, 25),
        FIRED_UP(1, 1),
        BLACK_MAGIC(1, 1),
        TEMPERANCE(-1, 2),
        EPHEMERAL(-1, 3),
        DIVINATION(-1, 1),
        COVENANT(-1, 1),
        ASTRAL_BODY(-1, 3);

        private final int polarity;
        private final int scale;

        AbilityType(int polarity, int scale) {
            this.polarity = polarity;
            this.scale = scale;
        }

        public int scale() {
            return scale;
        }

        public int isPolarity() {
            return polarity;
        }
    }

    public static HashMap<AbilityType, Integer> investedAbilityLevels = new HashMap<>();
    public static int abilityPoints;
    public static int timesProvided;

    public static void reset() {
        abilityPoints = 2;
        timesProvided = 1;
        investedAbilityLevels.clear();
        for (AbilityType t : AbilityType.values()) {
            investedAbilityLevels.put(t, 0);
        }
    }

    public static void load(AbilitySaveData data) {
        reset();
        if (data != null) {
            abilityPoints = data.abilityPoints;
            for (AbilityType t : data.investedLevels.keySet()) {
                investedAbilityLevels.put(t, data.investedLevels.get(t));
            }
        }
    }

    public static int getAbilityLevel(AbilityType t) {
        int i = investedAbilityLevels.getOrDefault(t, 0);
        /*for (AbstractRelic r : Wiz.adp().relics) {
            if (r instanceof AbilityProvidingRelic) {
                i += ((AbilityProvidingRelic) r).levelBoost(t);
            }
        }*/
        return i;
    }

    public static void addAbilityLevel(AbilityType t, int i) {
        investedAbilityLevels.put(t, investedAbilityLevels.get(t) + i);
    }

    public static void actStartGainPoint() {
        abilityPoints += 1;
        timesProvided += 1;
        AbilityButton.flash();
    }

    public static AbstractPower getAbilityPower(AbilityType type) {
        switch (type) {
            case FIRED_UP:
                return new FiredUpPower(Wiz.adp(), type.scale * getAbilityLevel(type));
            case FISTICUFFS:
                return new FisticuffsPower(Wiz.adp(), type.scale * getAbilityLevel(type));
            case BRUISER:
                return new BruiserPower(Wiz.adp(), type.scale * getAbilityLevel(type));
            case PILE_BUNKER:
                return new PileBunkerPower(Wiz.adp(), type.scale * getAbilityLevel(type));
            case TEMPERANCE:
                return new TemperancePower(Wiz.adp(), type.scale * getAbilityLevel(type));
            case EPHEMERAL:
                return new EphemeralPower(Wiz.adp(), type.scale * getAbilityLevel(type));
            case COVENANT:
                return new CovenantPower(Wiz.adp(), type.scale * getAbilityLevel(type));
            case DIVINATION:
                return new DivinationPower(Wiz.adp(), type.scale * getAbilityLevel(type));
            case ASTRAL_BODY:
                return new AstralBodyPower(Wiz.adp(), type.scale * getAbilityLevel(type));
            case BLACK_MAGIC:
                return new BlackMagicPower(Wiz.adp(), type.scale * getAbilityLevel(type));
        }
        return null;
    }

    public static CardGroup getAbilityCards() {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.addToTop(new FisticuffsAbility());
        group.addToTop(new PileBunkerAbility());
        group.addToTop(new BruiserAbility());
        group.addToTop(new FiredUpAbility());
        group.addToTop(new BlackMagicAbility());
        group.addToTop(new TemperanceAbility());
        group.addToTop(new EphemeralAbility());
        group.addToTop(new DivinationAbility());
        group.addToTop(new AstralBodyAbility());
        group.addToTop(new CovenantAbility());
        return group;
    }

    public static CardGroup getUpgradeableAbilityCards() {
        CardGroup group = getAbilityCards();
        group.group.removeIf(c -> c.upgraded);
        return group;
    }

    public static class AbilitySaveData {
        public HashMap<AbilityType, Integer> investedLevels = new HashMap<>();
        public int abilityPoints;
        public int timesProvided;

        public AbilitySaveData(HashMap<AbilityType, Integer> map, int points, int timesProvided) {
            this.abilityPoints = points;
            this.timesProvided = timesProvided;
            for (AbilityType t : map.keySet()) {
                investedLevels.put(t, map.get(t));
            }
        }
    }
}
