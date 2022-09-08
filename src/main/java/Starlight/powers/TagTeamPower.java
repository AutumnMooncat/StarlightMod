package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.util.AbilityManager;
import Starlight.util.FormatHelper;
import Starlight.util.TexLoader;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;

public class TagTeamPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(TagTeamPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String[] NAMES = CardCrawlGame.languagePack.getCharacterString(TheStarlightMod.makeID("StarlightSisters")).NAMES;
    public static final String PRIM = NAMES[2];
    public static final String LUNA = NAMES[3];

    public TextureAtlas.AtlasRegion P48, P128, L48, L128;
    Texture pTex = TexLoader.getTexture(TheStarlightMod.modID + "Resources/images/powers/TagTeamPower48.png");
    Texture pTexLarge = TexLoader.getTexture(TheStarlightMod.modID + "Resources/images/powers/TagTeamPower128.png");
    Texture lTex = TexLoader.getTexture(TheStarlightMod.modID + "Resources/images/powers/TagTeamPowerF48.png");
    Texture lTexLarge = TexLoader.getTexture(TheStarlightMod.modID + "Resources/images/powers/TagTeamPowerF128.png");

    boolean lastState;

    HashMap<AbstractPower, Integer> addedPowers = new HashMap<>();

    public TagTeamPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = -1;
        this.priority = Short.MIN_VALUE;
        this.type = NeutralPowertypePatch.NEUTRAL;
        P48 = new TextureAtlas.AtlasRegion(pTex, 0, 0, pTex.getWidth(), pTex.getHeight());
        P128 = new TextureAtlas.AtlasRegion(pTexLarge, 0, 0, pTexLarge.getWidth(), pTexLarge.getHeight());
        L48 = new TextureAtlas.AtlasRegion(lTex, 0, 0, lTex.getWidth(), lTex.getHeight());
        L128 = new TextureAtlas.AtlasRegion(lTexLarge, 0, 0, lTexLarge.getWidth(), lTexLarge.getHeight());
        this.region48 = P48;
        this.region128 = P128;
        this.lastState = owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront;
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        for (AbilityManager.AbilityType t : AbilityManager.AbilityType.values()) {
            if (AbilityManager.getAbilityLevel(t) > 0) {
                AbstractPower ability = AbilityManager.getAbilityPower(t);
                Wiz.applyToSelf(ability);
                addedPowers.put(ability, t.isPolarity());
            }
        }
        updateDescription();
    }

    private boolean isPrimrose() {
        return owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront;
    }

    private boolean isLuna() {
        return owner instanceof StarlightSisters && !((StarlightSisters) owner).attackerInFront;
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        updateDescription();
    }

    /*@Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (isPrimrose()) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                Wiz.atb(new GainBlockAction(owner, 2));
            }
        } else if (isLuna()) {
            if (card.type == AbstractCard.CardType.SKILL) {
                Wiz.atb(new ApplyPowerAction(owner, owner, new SpellPower(owner, 1)));
            }
        }
    }*/

    /*@Override
    public void onExhaust(AbstractCard card) {
        flashWithoutSound();
        if (((StarlightSisters) owner).attackerInFront) {
            Wiz.atb(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1)));
        }
    }*/

    /*@Override
    public void onCardDraw(AbstractCard card) {
        if (isLuna()) {
            if (card.type == AbstractCard.CardType.STATUS) {
                flashWithoutSound();
                Wiz.atb(new ImmediateExhaustCardAction(card));
                //Wiz.atb(new ApplyPowerAction(owner, owner, new SpellPower(owner, 3)));
            }
        }
    }*/

    /*@Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (isPrimrose() && info.type != DamageInfo.DamageType.NORMAL) {
            return (int) (damageAmount/2f);
        }
        return damageAmount;
    }*/

    @Override
    public void updateDescription() {
        if (owner instanceof StarlightSisters) {
            this.region48 = ((StarlightSisters) owner).attackerInFront ? P48 : L48;
            this.region128 = ((StarlightSisters) owner).attackerInFront ? P128 : L128;
            if (lastState != ((StarlightSisters) owner).attackerInFront) {
                flashWithoutSound();
                lastState = ((StarlightSisters) owner).attackerInFront;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(DESCRIPTIONS[0]);
            if (((StarlightSisters) owner).attackerInFront) {
                sb.append(PRIM);
            } else {
                sb.append(LUNA);
            }
            sb.append(DESCRIPTIONS[1]);
            int pol = ((StarlightSisters) owner).attackerInFront ? 1 : -1;
            for (AbstractPower ability : addedPowers.keySet()) {
                ability.updateDescription();
                if (addedPowers.get(ability) == 0 || addedPowers.get(ability) == pol) {
                    sb.append(" NL - ").append(FormatHelper.prefixWords(ability.name, "#y"));
                }
            }
            sb.append(DESCRIPTIONS[2]);
            if (((StarlightSisters) owner).attackerInFront) {
                sb.append(LUNA).append(DESCRIPTIONS[3]);
            } else {
                sb.append(PRIM).append(DESCRIPTIONS[4]);
            }
            description = sb.toString();
        } else {
            description = "???";
        }
    }

    /*@Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return o instanceof AbstractCard && damageInfo!= null && damageInfo.type == DamageInfo.DamageType.NORMAL && isPrimrose();
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(new FiredUpDamage());
    }*/
}
