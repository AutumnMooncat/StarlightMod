
package Starlight.cards;

import Starlight.actions.EasyXCostAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.OnForetoldCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.purple.Ragnarok;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;

import static Starlight.TheStarlightMod.makeID;
import static Starlight.util.Wiz.applyToSelfTop;
import static Starlight.util.Wiz.atb;

public class Doomsday extends AbstractEasyCard implements OnForetoldCard {
    public final static String ID = makeID(Doomsday.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = -1;
    private static final int DMG = 8;
    private static final int UP_DMG = 3;

    public Doomsday() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = 0;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect + params[0]; i++) {
                Wiz.att(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                Wiz.forAllMonstersLiving(mon -> Wiz.att(new VFXAction(new LightningEffect(mon.drawX, mon.drawY), 0.05F)));
                Wiz.att(new SFXAction("THUNDERCLAP", 0.05F));
            }
            if (effect + params[0] > 0) {
                Wiz.att(new VFXAction(new ScreenOnFireEffect(), 0.5f));
                Wiz.att(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.MED, false);
                        this.isDone = true;
                    }
                });
            }
            return true;
        }, magicNumber));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return Ragnarok.ID;
    }

    @Override
    public void onForetold() {
        upgradeMagicNumber(1);
    }
}