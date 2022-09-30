package Starlight.cards.token;

import Starlight.cardmods.ResonantMod;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.CardArtRoller;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class StarShard extends AbstractEasyCard {
    public final static String ID = makeID(StarShard.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int AMT = 7;
    private static final int UP_AMT = 3;

    public StarShard() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        baseBlock = block = AMT;
        baseDamage = damage = AMT;
        selfRetain = true;
        exhaust = true;
        //CardModifierManager.addModifier(this, new ResonantMod(true));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    public void upp() {
        upgradeBlock(UP_AMT);
        upgradeDamage(UP_AMT);
    }

    @Override
    public String cardArtCopy() {
        return Collect.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.5f, 0.54f, 0.5f, false);
    }
}