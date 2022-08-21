
package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.OnForetoldCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.purple.Ragnarok;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;

import static Starlight.TheStarlightMod.makeID;

public class Doomsday extends AbstractEasyCard implements OnForetoldCard {
    public final static String ID = makeID(Doomsday.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 3;
    private static final int DMG = 16;
    private static final int UP_DMG = 4;
    private static final int SCALE = 8;
    private static final int UP_SCALE = 2;

    public Doomsday() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = SCALE;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.MED, false);
                this.isDone = true;
            }
        });
        Wiz.atb(new VFXAction(new ScreenOnFireEffect(), 0.5f));
        Wiz.atb(new SFXAction("THUNDERCLAP", 0.05F));
        Wiz.forAllMonstersLiving(mon -> Wiz.atb(new VFXAction(new LightningEffect(mon.drawX, mon.drawY), 0.05F)));
        allDmg(AbstractGameAction.AttackEffect.NONE);
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_SCALE);
    }

    @Override
    public String cardArtCopy() {
        return Ragnarok.ID;
    }

    @Override
    public void onForetold() {
        this.addToBot(new ModifyDamageAction(this.uuid, this.magicNumber));
    }
}