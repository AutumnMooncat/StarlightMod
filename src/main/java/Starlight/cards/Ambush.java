package Starlight.cards;

import Starlight.actions.SwapAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.LunaCard;
import Starlight.characters.StarlightSisters;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Ambush extends AbstractEasyCard {
    public final static String ID = makeID(Ambush.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int AMT = 4;
    private static final int UP_AMT = 2;

    public Ambush() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = AMT;
        baseBlock = block = AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void upp() {
        upgradeDamage(UP_AMT);
        upgradeBlock(UP_AMT);
    }

    /*@Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainEnergyAction(1));
    }*/

    @Override
    public String cardArtCopy() {
        return Shiv.ID; //Sneaky Strike
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.1f, 0.5f, 0.6f, 1, false);
    }

    /*@Override
    public void onLunaTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainEnergyAction(1));
        if (p instanceof StarlightSisters) {
            Wiz.atb(new SwapAction());
        }
    }*/
}