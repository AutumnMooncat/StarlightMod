package Starlight.cards;

import Starlight.cardmods.ResonantMod;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.blue.Stack;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Fortify extends AbstractEasyCard {
    public final static String ID = makeID(Fortify.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 6;
    private static final int UP_BLK = 2;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Fortify() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = EFFECT;
        selfRetain = true;
        //CardModifierManager.addModifier(this, new ResonantMod(true));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
        upgradeMagicNumber(UP_EFFECT);
    }

    /*@Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new DrawCardAction(magicNumber));
    }*/

    @Override
    public String cardArtCopy() {
        return Stack.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.15f, 0.5f, 0.5f, 0.5f, false);
    }
}