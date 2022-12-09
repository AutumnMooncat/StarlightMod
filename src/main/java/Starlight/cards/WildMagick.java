package Starlight.cards;

import Starlight.actions.PlayRandomCardAction;
import Starlight.actions.ProjectSpecificCardAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.FormatHelper;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.WaveOfTheHand;
import com.megacrit.cardcrawl.cards.red.DualWield;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static Starlight.TheStarlightMod.makeID;

public class WildMagick extends AbstractEasyCard {
    public final static String ID = makeID(WildMagick.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    public WildMagick() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new PlayRandomCardAction(p.drawPile, 1, Wiz::isMagic));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    public void triggerOnGlowCheck() {
        if (Wiz.adp().drawPile.group.stream().noneMatch(Wiz::isMagic)) {
            this.glowColor = Settings.RED_TEXT_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public String cardArtCopy() {
        return WaveOfTheHand.ID;
    }
}