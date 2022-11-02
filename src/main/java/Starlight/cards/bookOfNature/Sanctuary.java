package Starlight.cards.bookOfNature;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.BarbPower;
import Starlight.powers.SanctuaryPower;
import Starlight.powers.TanglePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import Starlight.vfx.AngledFlashAtkImgEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.blue.HelloWorld;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Starlight.TheStarlightMod.makeID;

public class Sanctuary extends AbstractMagickCard {
    public final static String ID = makeID(Sanctuary.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 1;

    public Sanctuary() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_NATURE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SanctuaryPower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return LiveForever.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.55f, 0.55f, 0.55f, false);
    }
}