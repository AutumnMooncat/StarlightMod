package Starlight.cards;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

import static Starlight.TheStarlightMod.makeID;

public class Vanish extends AbstractMagickCard {
    public final static String ID = makeID(Vanish.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 6;
    private static final int UP_BLK = 3;

    public Vanish() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new VFXAction(new SmokeBombEffect(p.hb.cX, p.hb.cY)));
        Wiz.applyToSelf(new IntangiblePlayerPower(p, 1));
        Wiz.applyToSelf(new NextTurnBlockPower(p, block));
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
    }

    public void upp() {
        upgradeBlock(UP_BLK);
    }
}