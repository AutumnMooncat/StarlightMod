package Starlight.cards;

import Starlight.actions.SwapAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.LunaCard;
import Starlight.cards.interfaces.TagTeamCard;
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

public class Ambush extends AbstractEasyCard implements LunaCard {
    public final static String ID = makeID(Ambush.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 10;
    private static final int UP_DMG = 3;

    public Ambush() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void upp() {
        upgradeDamage(UP_DMG);
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

    @Override
    public void onLunaTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainEnergyAction(1));
        if (p instanceof StarlightSisters) {
            Wiz.atb(new SwapAction());
        }
    }
}