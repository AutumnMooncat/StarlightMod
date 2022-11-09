package Starlight.cards.bookOfSpace;

import Starlight.actions.ProjectTopCardAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.AstralProtectionPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.blue.Buffer;
import com.megacrit.cardcrawl.cards.green.BulletTime;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;

import static Starlight.TheStarlightMod.makeID;

public class DimensionLock extends AbstractMagickCard {
    public final static String ID = makeID(DimensionLock.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 1;

    public DimensionLock() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_SPACE);
        tags.add(CustomTags.STARLIGHT_PROJECTS);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ProjectTopCardAction(magicNumber));
        Wiz.applyToSelf(new NoDrawPower(p));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
        //upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return BulletTime.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.41f, 0.63f, 0.54f, 0.54f, false);
    }
}