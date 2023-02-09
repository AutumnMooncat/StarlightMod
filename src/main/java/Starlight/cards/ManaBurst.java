package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Tempest;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static Starlight.TheStarlightMod.makeID;

public class ManaBurst extends AbstractEasyCard {
    public final static String ID = makeID(ManaBurst.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 4;
    private static final int DMG = 7;
    private static final int UP_DMG = 2;
    private static final int HITS = 3;

    public ManaBurst() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = HITS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            for (int i = 0 ; i < magicNumber ; i++) {
                addToBot(new SFXAction("THUNDERCLAP", 0.05F));
                addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.05F));
                dmg(m, AbstractGameAction.AttackEffect.NONE);
            }
        }
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    public int magickPlayedThisTurn() {
        return (int) Wiz.cardsPlayedThisTurn().stream().filter(Wiz::isMagic).count();
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        if (Wiz.isMagic(cardPlayed)) {
            this.setCostForTurn(this.costForTurn - 1);
        }
    }

    public void triggerWhenDrawn() {
        this.setCostForTurn(this.cost - magickPlayedThisTurn());
    }

    public void atTurnStart() {
        this.resetAttributes();
        this.applyPowers();
    }

    public AbstractCard makeCopy() {
        AbstractCard tmp = new ManaBurst();
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.setCostForTurn(this.cost - magickPlayedThisTurn());
        }
        return tmp;
    }

    @Override
    public String cardArtCopy() {
        return Tempest.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }
}