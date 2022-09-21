package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

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

    boolean primrose;

    //HashMap<AbstractPower, Integer> addedPowers = new HashMap<>();

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
        this.primrose = owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront;
        this.region48 = primrose ? P48 : L48;
        this.region128 = primrose ? P128 : L128;
        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (primrose && type == DamageInfo.DamageType.NORMAL) {
            return damage + 1;
        }
        return damage;
    }

    public float modifyBlock(float blockAmount) {
        if (!primrose) {
            return blockAmount + 1;
        }
        return blockAmount;
    }

    public void onSwap() {
        if (owner instanceof StarlightSisters) {
            this.region48 = ((StarlightSisters) owner).attackerInFront ? P48 : L48;
            this.region128 = ((StarlightSisters) owner).attackerInFront ? P128 : L128;
            if (primrose != ((StarlightSisters) owner).attackerInFront) {
                flashWithoutSound();
                primrose = ((StarlightSisters) owner).attackerInFront;
            }
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (primrose) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1];
        }
    }

    /*@Override
    public void onInitialApplication() {
        for (AbilityManager.AbilityType t : AbilityManager.AbilityType.values()) {
            if (AbilityManager.getAbilityLevel(t) > 0) {
                AbstractPower ability = AbilityManager.getAbilityPower(t);
                Wiz.applyToSelf(ability);
                addedPowers.put(ability, t.isPolarity());
            }
        }
        updateDescription();
    }*/

    /*@Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        updateDescription();
    }*/

    /*@Override
    public void updateDescription() {
        if (owner instanceof StarlightSisters) {
            this.region48 = ((StarlightSisters) owner).attackerInFront ? P48 : L48;
            this.region128 = ((StarlightSisters) owner).attackerInFront ? P128 : L128;
            if (primrose != ((StarlightSisters) owner).attackerInFront) {
                flashWithoutSound();
                primrose = ((StarlightSisters) owner).attackerInFront;
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
    }*/
}
