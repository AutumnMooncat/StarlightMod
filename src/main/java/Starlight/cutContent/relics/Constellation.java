package Starlight.cutContent.relics;

import Starlight.characters.StarlightSisters;
import Starlight.relics.AbstractEasyRelic;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class Constellation extends AbstractEasyRelic {
    public static final String ID = makeID(Constellation.class.getSimpleName());

    public Constellation() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        //this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
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

    /*public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new ConstellationOption(this.counter < 3 && !CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).isEmpty()));
    }

    public void atBattleStart() {
        if (this.counter != 0) {
            this.flash();
            if (str > 0) {
                Wiz.applyToSelfTop(new StrengthPower(Wiz.adp(), str));
            }
            if (dex > 0) {
                Wiz.applyToSelfTop(new DexterityPower(Wiz.adp(), dex));
            }
            if (art > 0) {
                Wiz.applyToSelfTop(new ArtifactPower(Wiz.adp(), art));
            }
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public void addStr(int amount) {
        str += amount;
    }

    public void addDex(int amount) {
        dex += amount;
    }

    public void addArt(int amount) {
        art += amount;
    }

    @Override
    public Integer onSave() {
        return str + (OFFSET * dex) + (OFFSET * OFFSET * art);
    }

    @Override
    public void onLoad(Integer integer) {
        this.str = integer % OFFSET;
        integer /= OFFSET;
        this.dex = integer % OFFSET;
        integer /= OFFSET;
        this.art = integer % OFFSET;
    }*/
}
