package Starlight.relics;

import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class StarWand extends AbstractEasyRelic {
    public static final String ID = makeID(StarWand.class.getSimpleName());
    private static final int SP = 8;

    public StarWand() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    @Override
    public void atTurnStart() {
        flash();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int draw = 0;
                if (!Wiz.adp().drawPile.getCardsOfType(AbstractCard.CardType.SKILL).isEmpty()) {
                    AbstractCard c = Wiz.adp().drawPile.getCardsOfType(AbstractCard.CardType.SKILL).getRandomCard(true);
                    Wiz.adp().drawPile.removeCard(c);
                    Wiz.adp().drawPile.addToTop(c);
                    draw++;
                }
                if (!Wiz.adp().drawPile.getCardsOfType(AbstractCard.CardType.ATTACK).isEmpty()) {
                    AbstractCard c = Wiz.adp().drawPile.getCardsOfType(AbstractCard.CardType.ATTACK).getRandomCard(true);
                    Wiz.adp().drawPile.removeCard(c);
                    Wiz.adp().drawPile.addToTop(c);
                    draw++;
                }
                Wiz.att(new DrawCardAction(draw));
                this.isDone = true;
            }
        });
    }

    @Override //Should replace default relic. Big thanks papa kio
    public void obtain() {
        //Grab the player
        AbstractPlayer p = AbstractDungeon.player;
        //If we have the starter relic...
        if (p.hasRelic(MagicWand.ID)) {
            //Grab its data for relic stats if you want to carry the stats over to the boss relic
            //DualCore r = (DualCore) p.getRelic(DualCore.ID);
            //stats.put(ATTACKS_CLOCKED, r.getAttacks());
            //stats.put(SKILLS_CLOCKED, r.getSkills());
            //stats.put(POWERS_CLOCKED, r.getPowers());
            //Find it...
            for (int i = 0; i < p.relics.size(); ++i) {
                if (p.relics.get(i).relicId.equals(MagicWand.ID)) {
                    //Replace it
                    instantObtain(p, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    //Only spawn if we have the starter relic
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(MagicWand.ID);
    }
}
