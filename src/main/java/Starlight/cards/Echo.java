package Starlight.cards;

import Starlight.actions.ProjectSpecificCardAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.SpellPower;
import Starlight.util.CustomTags;
import Starlight.util.FormatHelper;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.EscapePlan;
import com.megacrit.cardcrawl.cards.red.DualWield;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import static Starlight.TheStarlightMod.makeID;

public class Echo extends AbstractEasyCard {
    public final static String ID = makeID(Echo.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    private AbstractCard lastCard;

    public Echo() {
        super(ID, COST, TYPE, RARITY, TARGET);
        tags.add(CustomTags.STARLIGHT_PROJECTS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SFXAction("ORB_DARK_EVOKE", 0.05F));
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().anyMatch(c -> c.type == CardType.ATTACK)) {
            ArrayList<AbstractCard> attacks = AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(c -> c.type == CardType.ATTACK).collect(Collectors.toCollection(ArrayList::new));
            Wiz.atb(new ProjectSpecificCardAction(attacks.get(attacks.size()-1).makeStatEquivalentCopy()));
        }
        this.rawDescription = cardStrings.DESCRIPTION;
        lastCard = null;
        this.initializeDescription();
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().noneMatch(c -> c.type == CardType.ATTACK)) {
            this.glowColor = Settings.RED_TEXT_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().anyMatch(c -> c.type == CardType.ATTACK)) {
            ArrayList<AbstractCard> attacks = AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(c -> c.type == CardType.ATTACK).collect(Collectors.toCollection(ArrayList::new));
            AbstractCard preview = attacks.get(attacks.size()-1);
            if (preview != lastCard) {
                lastCard = preview;
                cardsToPreview = lastCard.makeStatEquivalentCopy();
                this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + FormatHelper.prefixWords(lastCard.name, "[#efc851]", "[]") + cardStrings.EXTENDED_DESCRIPTION[1];
                this.initializeDescription();
            }
        }
    }

    @Override
    public String cardArtCopy() {
        return DualWield.ID;
    }
}