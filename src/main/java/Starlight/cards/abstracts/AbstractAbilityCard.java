package Starlight.cards.abstracts;

import Starlight.TheStarlightMod;
import Starlight.patches.TypeOverridePatch;
import Starlight.util.AbilityManager;
import Starlight.util.ImageHelper;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAbilityCard extends AbstractEasyCard {
    private static final String ID = TheStarlightMod.makeID("Abilities");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] ABILITY_TEXT = uiStrings.TEXT;
    public AbstractPower storedPower;
    private static List<TooltipInfo> tips;

    public AbstractAbilityCard(String ID, AbilityManager.AbilityType type) {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        TypeOverridePatch.TypeOverrideField.typeOverride.set(this, ABILITY_TEXT[0]);
        this.storedPower = AbilityManager.getAbilityPower(type);
        appendLevel();
        for (int i = 0 ; i < AbilityManager.getAbilityLevel(type) ; i++) {
            upgrade();
        }
        this.portrait = getPortrait();
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (tips == null) {
            tips = new ArrayList<>();
            tips.add(new TooltipInfo(ABILITY_TEXT[0], ABILITY_TEXT[1]));
        }
        return tips;
    }

    private TextureAtlas.AtlasRegion getPortrait() {
        TextureAtlas.AtlasRegion cardBack = storedPower.type == AbstractPower.PowerType.DEBUFF ? CardLibrary.getCard(VoidCard.ID).portrait : CardLibrary.getCard(Miracle.ID).portrait;
        TextureAtlas.AtlasRegion powerIcon = storedPower.region128;
        powerIcon.flip(false, true);
        cardBack.flip(false, true);
        FrameBuffer fb = ImageHelper.createBuffer(250, 190);
        OrthographicCamera og = new OrthographicCamera(250, 190);
        SpriteBatch sb = new SpriteBatch();
        sb.setProjectionMatrix(og.combined);
        ImageHelper.beginBuffer(fb);
        sb.setColor(Color.WHITE);
        sb.begin();
        sb.draw(cardBack, -125, -95);
        sb.draw(powerIcon, -powerIcon.packedWidth/2F, -powerIcon.packedHeight/2F);
        sb.end();
        fb.end();
        cardBack.flip(false, true);
        powerIcon.flip(false, true);
        TextureRegion a = ImageHelper.getBufferTexture(fb);
        return new TextureAtlas.AtlasRegion(a.getTexture(), 0, 0, 250, 190);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    protected void appendLevel() {
        this.name = this.originalName + ABILITY_TEXT[2] + timesUpgraded;
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) {
            upgradeName();
            upp();
        }
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        appendLevel();
        this.initializeTitle();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        return super.makeCopy();
    }

    public abstract void upgradeAbility();
}
