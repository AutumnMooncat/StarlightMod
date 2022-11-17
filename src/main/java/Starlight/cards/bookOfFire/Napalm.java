
package Starlight.cards.bookOfFire;

import Starlight.actions.NapalmAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.cards.red.BurningPact;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Starlight.TheStarlightMod.makeID;

public class Napalm extends AbstractMagickCard {
    public final static String ID = makeID(Napalm.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;
    private static final int TIMES = 3;

    public Napalm() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = TIMES;
        tags.add(CustomTags.STARLIGHT_FIRE);
        tags.add(CustomTags.STARLIGHT_APPLIES_BURN);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (randomMonster != null) {
            Wiz.atb(new NapalmAction(randomMonster, magicNumber, secondMagic));
        }
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return BurningPact.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.65f, 0.35f, 0.45f, false);
    }
}