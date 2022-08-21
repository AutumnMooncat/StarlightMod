package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.OnTagTeamTriggeredCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.FollowUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static Starlight.TheStarlightMod.makeID;

public class Thrash extends AbstractEasyCard implements OnTagTeamTriggeredCard {
    public final static String ID = makeID(Thrash.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 12;
    private static final int UP_DMG = 2;
    private static final int UP_UP_DMG = 1;

    public Thrash() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = UP_DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);
    }

    public void upp() {
        //upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_UP_DMG);
    }

    @Override
    public void onTagTriggered(AbstractCard card, AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ModifyDamageAction(this.uuid, this.magicNumber));
    }

    @Override
    public String cardArtCopy() {
        return FollowUp.ID;
    }
}