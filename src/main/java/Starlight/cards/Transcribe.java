package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.FormatHelper;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Scrawl;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static Starlight.TheStarlightMod.makeID;

public class Transcribe extends AbstractEasyCard /*implements TagTeamCard*/ {
    public final static String ID = makeID(Transcribe.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    private AbstractCard lastCard;
    //private UUID lastUUID;
    public Transcribe() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //lastUUID = null;
        Wiz.atb(new SFXAction("ORB_DARK_EVOKE", 0.05F));
        ArrayList<AbstractCard> magicks = Wiz.cardsPlayedThisCombat().stream().filter(Wiz::isMagic).collect(Collectors.toCollection(ArrayList::new));
        if (!magicks.isEmpty()) {
            AbstractCard card = magicks.get(magicks.size()-1).makeStatEquivalentCopy();
            card.setCostForTurn(0);
            //lastUUID = card.uuid;
            Wiz.atb(new MakeTempCardInHandAction(card, true, true));
            Wiz.atb(new AbstractGameAction() {
                @Override
                public void update() {
                    purgeOnUse = true;
                    this.isDone = true;
                }
            });
        }
        this.rawDescription = cardStrings.DESCRIPTION;
        lastCard = null;
        this.initializeDescription();
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().noneMatch(Wiz::isMagic)) {
            this.glowColor = Settings.RED_TEXT_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().anyMatch(Wiz::isMagic)) {
            ArrayList<AbstractCard> magicks = AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(Wiz::isMagic).collect(Collectors.toCollection(ArrayList::new));
            AbstractCard preview = magicks.get(magicks.size()-1);
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
        return Scrawl.ID;
    }

    /*@Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (lastUUID != null) {
                    for (AbstractCard card : Wiz.getAllCardsInCardGroups(true, false)) {
                        if (lastUUID.equals(card.uuid)) {
                            card.setCostForTurn(0);
                            break;
                        }
                    }
                }
                this.isDone = true;
            }
        });
    }*/
}