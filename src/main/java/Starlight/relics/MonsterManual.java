package Starlight.relics;

import Starlight.characters.StarlightSisters;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class MonsterManual extends AbstractEasyRelic {
    public static final String ID = makeID(MonsterManual.class.getSimpleName());
    private static final int CARDS = 3;

    public MonsterManual() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth == 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            addToBot(new RelicAboveCreatureAction(m, this));
            addToBot(new DrawCardAction(CARDS, new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c : DrawCardAction.drawnCards) {
                        if (c.canUpgrade()) {
                            c.upgrade();
                        }
                    }
                    this.isDone = true;
                }
            }));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARDS + DESCRIPTIONS[1];
    }
}
