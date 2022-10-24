package Starlight.cards.bookOfStorm;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.blue.Sunder;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.scene.CeilingDustEffect;

import static Starlight.TheStarlightMod.makeID;

public class Quake extends AbstractMagickCard {
    public final static String ID = makeID(Quake.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 10;
    private static final int UP_DMG = 4;
    private static final int REDUCE = 1;

    public Quake() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = REDUCE;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_STORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.MED, false);
                for (int i = 0 ; i < 8 ; i ++) {
                    AbstractDungeon.effectsQueue.add(new CeilingDustEffect());
                }
                this.isDone = true;
            }
        });
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.atb(new ReduceCostAction(this.uuid, magicNumber));
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return Sunder.ID;
    }
}