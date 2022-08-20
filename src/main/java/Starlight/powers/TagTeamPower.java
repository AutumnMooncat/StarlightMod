package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

    public TextureAtlas.AtlasRegion P48, P128, L48, L128;
    Texture pTex = TexLoader.getTexture(TheStarlightMod.modID + "Resources/images/powers/TagTeamPower48.png");
    Texture pTexLarge = TexLoader.getTexture(TheStarlightMod.modID + "Resources/images/powers/TagTeamPower128.png");
    Texture lTex = TexLoader.getTexture(TheStarlightMod.modID + "Resources/images/powers/TagTeamPowerF48.png");
    Texture lTexLarge = TexLoader.getTexture(TheStarlightMod.modID + "Resources/images/powers/TagTeamPowerF128.png");

    boolean lastState;

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
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        updateDescription();
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && owner instanceof StarlightSisters) {
            if (((StarlightSisters) owner).attackerInFront) {
                return damage + 1;
            }
        }
        return damage;
    }

    public float modifyBlock(float blockAmount) {
        if (owner instanceof StarlightSisters && !((StarlightSisters) owner).attackerInFront) {
            return blockAmount + 1;
        }
        return blockAmount;
    }

    @Override
    public void updateDescription() {
        if (owner instanceof StarlightSisters) {
            this.region48 = ((StarlightSisters) owner).attackerInFront ? P48 : L48;
            this.region128 = ((StarlightSisters) owner).attackerInFront ? P128 : L128;
            if (lastState != ((StarlightSisters) owner).attackerInFront) {
                flashWithoutSound();
                lastState = ((StarlightSisters) owner).attackerInFront;
            }
            if (((StarlightSisters) owner).attackerInFront) {
                description = DESCRIPTIONS[0] + NAMES[2] + DESCRIPTIONS[1] + NAMES[3] + DESCRIPTIONS[2];
            } else {
                description = DESCRIPTIONS[3] + NAMES[3] + DESCRIPTIONS[4] + NAMES[2] + DESCRIPTIONS[5];
            }
        } else {
            description = "???";
        }
    }

}
