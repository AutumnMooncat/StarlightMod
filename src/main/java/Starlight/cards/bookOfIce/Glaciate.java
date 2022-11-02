package Starlight.cards.bookOfIce;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.ReflectPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.blue.AutoShields;
import com.megacrit.cardcrawl.cards.blue.Glacier;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.RetainCardPower;

import static Starlight.TheStarlightMod.makeID;

public class Glaciate extends AbstractMagickCard {
    public final static String ID = makeID(Glaciate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Glaciate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_ICE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new DrawCardAction(magicNumber));
        Wiz.applyToSelf(new EquilibriumPower(p, 1));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Glacier.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.6f, 0.45f, 0.5f, false);
    }
}