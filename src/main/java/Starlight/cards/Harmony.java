package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.HarmonyPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.LikeWater;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Harmony extends AbstractEasyCard {
    public final static String ID = makeID(Harmony.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Harmony() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelfTop(new HarmonyPower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return LikeWater.ID;
    }
}