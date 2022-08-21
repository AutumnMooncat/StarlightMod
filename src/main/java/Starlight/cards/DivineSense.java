package Starlight.cards;

import Starlight.actions.ForetellAction;
import Starlight.actions.ProjectCardsInHandAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.ThirdEye;
import com.megacrit.cardcrawl.cards.red.LimitBreak;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static Starlight.TheStarlightMod.makeID;

public class DivineSense extends AbstractEasyCard {
    public final static String ID = makeID(DivineSense.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int CARDS = 3;

    public DivineSense() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = CARDS;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ForetellAction(p.discardPile, magicNumber, c -> true, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : ForetellAction.foretoldCards) {
                    card.freeToPlayOnce = true;
                }
                this.isDone = true;
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return ThirdEye.ID;
    }
}