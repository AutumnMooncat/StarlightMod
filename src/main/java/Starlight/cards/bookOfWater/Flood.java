package Starlight.cards.bookOfWater;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.purple.Meditate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Starlight.TheStarlightMod.makeID;

public class Flood extends AbstractMagickCard {
    public final static String ID = makeID(Flood.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 2;

    public Flood() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_WATER);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ExpertiseAction(p, BaseMod.MAX_HAND_SIZE));
        Wiz.forAllMonstersLiving(mon -> {
            //Wiz.applyToEnemy(mon, new WetPower(mon, magicNumber));
            //Wiz.applyToEnemy(mon, new WeakPower(mon, magicNumber, false));
            Wiz.applyToEnemy(mon, new VulnerablePower(mon, magicNumber, false));
        });
    }

    public void upp() {
        //upgradeMagicNumber(UP_EFFECT);
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return Meditate.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.55f, 0.35f, 0.45f, false);
    }
}