package Starlight.cards.bookOfSpace;

import Starlight.actions.ProjectTopCardAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.red.Havoc;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import static Starlight.TheStarlightMod.makeID;

public class Eclipse extends AbstractMagickCard {
    public final static String ID = makeID(Eclipse.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int EFFECT = 1;
    private static final int BLK = 7;
    private static final int UP_BLK = 3;

    public Eclipse() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_SPACE);
        selfRetain = true;
        exhaust = true;
        tags.add(CustomTags.STARLIGHT_PROJECTS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BorderFlashEffect(Color.BLACK.cpy(), false)));
        Wiz.atb(new ProjectTopCardAction(magicNumber));
    }

    public void upp() {
        exhaust = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Havoc.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.20f, 0.5f, 0.4f, 0.5f, false);
    }
}