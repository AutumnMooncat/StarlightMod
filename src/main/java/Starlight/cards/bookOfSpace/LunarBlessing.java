package Starlight.cards.bookOfSpace;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.cards.interfaces.OnProjectCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.purple.EmptyBody;
import com.megacrit.cardcrawl.cards.purple.Wish;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static Starlight.TheStarlightMod.makeID;

public class LunarBlessing extends AbstractMagickCard implements OnProjectCard {
    public final static String ID = makeID(LunarBlessing.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 9;
    private static final int UP_BLK = 3;
    private static final int SCALE = 6;
    private static final int UP_SCALE = 3;

    public LunarBlessing() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SCALE;
        baseBlock = block = BLK;
        tags.add(CustomTags.STARLIGHT_SPACE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        //upgradeBlock(UP_BLK);
        upgradeMagicNumber(UP_SCALE);
    }

    @Override
    public void onProject() {
        superFlash(Settings.BLUE_RELIC_COLOR.cpy());
        Wiz.atb(new ModifyBlockAction(this.uuid, magicNumber));
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.05f, 0.15f, 0.35f, 0.4f, false);
    }
}