package Starlight.powers;

import Starlight.TheStarlightMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PolymorphPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(PolymorphPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private AbstractMonster replaced;

    public PolymorphPower(AbstractCreature owner, int amount, AbstractMonster replaced) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.loadRegion("minion");
        this.replaced = replaced;
        updateDescription();
    }

    @Override
    public void onDeath() {
        int index = AbstractDungeon.getMonsters().monsters.indexOf(owner);
        if (index != -1) {
            AbstractDungeon.getMonsters().monsters.remove(index);
            AbstractDungeon.getMonsters().monsters.add(replaced);
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + formatName(replaced.name) + DESCRIPTIONS[1];
    }

    public String formatName(String name) {
        StringBuilder output = new StringBuilder();
        for (String word : name.split(" ")) {
            output.append("#y").append(word).append(' ');
        }

        return output.toString().trim();
    }

}
