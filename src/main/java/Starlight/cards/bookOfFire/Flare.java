package Starlight.cards.bookOfFire;

import Starlight.actions.ExhaustByPredAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.BurnPower;
import Starlight.powers.ChillPower;
import Starlight.powers.VulcanizePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.purple.Worship;
import com.megacrit.cardcrawl.cards.red.SeeingRed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Starlight.TheStarlightMod.makeID;

public class Flare extends AbstractMagickCard {
    public final static String ID = makeID(Flare.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int EFFECT = 5;
    private static final int UP_EFFECT = 3;
    private static final int VULN = 1;

    public Flare() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = VULN;
        exhaust = true;
        tags.add(CustomTags.STARLIGHT_FIRE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new VigorPower(p, magicNumber));
        Wiz.forAllMonstersLiving(mon -> {
            Wiz.applyToEnemy(mon, new VulnerablePower(mon, secondMagic, false));
        });
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return SeeingRed.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.55f, 0.35f, 0.45f, false);
    }

}