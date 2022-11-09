package Starlight.cards;

import Starlight.actions.ProjectCardsInHandAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.purple.CarveReality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Star extends AbstractMagickCard {
    public final static String ID = makeID(Star.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int DMG = 5;
    private static final int UP_DMG = 3;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Star() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_PROJECTS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Wiz.atb(new ProjectCardsInHandAction(magicNumber));
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return CarveReality.ID;
    }
}