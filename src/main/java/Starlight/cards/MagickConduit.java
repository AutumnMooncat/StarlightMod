package Starlight.cards;

import Starlight.actions.ProjectSpecificCardAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class MagickConduit extends AbstractEasyCard implements TagTeamCard {
    public final static String ID = makeID(MagickConduit.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 3;

    public MagickConduit() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                int cards = (int) Wiz.adp().drawPile.group.stream().filter(c -> c instanceof AbstractMagickCard).count();
                if (cards > 0) {
                    Wiz.applyToSelfTop(new SpellPower(p, magicNumber * cards));
                    Wiz.att(new SFXAction("ORB_PLASMA_CHANNEL", 0.1f));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void applyPowers() {
        int cards = (int) Wiz.adp().drawPile.group.stream().filter(c -> c instanceof AbstractMagickCard).count();
        baseSecondMagic = baseMagicNumber * cards;
        secondMagic = magicNumber * cards;
        isSecondDamageModified = baseSecondMagic != secondMagic;
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    @Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainEnergyAction(1));
    }

    public void triggerOnGlowCheck() {
        if (Wiz.adp().drawPile.group.stream().noneMatch(c -> c instanceof AbstractMagickCard)) {
            this.glowColor = Settings.RED_TEXT_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public String cardArtCopy() {
        return Blasphemy.ID;
    }
}