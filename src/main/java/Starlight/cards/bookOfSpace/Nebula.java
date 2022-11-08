package Starlight.cards.bookOfSpace;

import Starlight.actions.ForetellAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.colorless.Metamorphosis;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import static Starlight.TheStarlightMod.makeID;

public class Nebula extends AbstractMagickCard {
    public final static String ID = makeID(Nebula.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 1;
    private static final int BLK = 7;
    private static final int UP_BLK = 3;

    public Nebula() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = EFFECT;
        baseBlock = block = BLK;
        tags.add(CustomTags.STARLIGHT_SPACE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.atb(new ForetellAction(Wiz.adp().discardPile, magicNumber));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
    }

    @Override
    public String cardArtCopy() {
        return Metamorphosis.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.5f, 0.54f, 0.5f, false);
    }
}