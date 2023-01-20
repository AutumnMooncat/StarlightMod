package Starlight.cards.bookOfWater;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.colorless.Chrysalis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Hydration extends AbstractMagickCard {
    public final static String ID = makeID(Hydration.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 5;
    private static final int UP_BLK = 3;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Hydration() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        //baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_WATER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        //Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new WetPower(mon, magicNumber)));
        //Wiz.atb(new DrawCardAction(magicNumber));
        //Wiz.atb(new DiscardAction(p, p, magicNumber, false));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
        //upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public void triggerWhenDrawn() {
        Wiz.atb(new DrawCardAction(1));
    }

    @Override
    public String cardArtCopy() {
        return Chrysalis.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.46f, 0.63f, 0.6f, 0.32f, false);
    }
}