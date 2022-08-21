package Starlight.cards.abstracts;

import Starlight.TheStarlightMod;
import Starlight.util.ImageHelper;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractPowerCard extends AbstractEasyCard {
    public AbstractPowerCard(AbstractPower power) {
        super(TheStarlightMod.makeID(power.ID+"Card"), -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        this.name = power.name;
        this.rawDescription = processDescription(power);
        this.portrait = getPortrait(power);
        initializeTitle();
        initializeDescription();
    }

    private String processDescription(AbstractPower power) {
        //String prefixID = power.ID.split(":")[0];
        StringBuilder sb = new StringBuilder();
        for (String s : power.description.split(" ")) {
            if (s.startsWith("#") && s.length() > 2) {
                s = s.substring(2);
            }
            /*if (GameDictionary.keywords.containsKey(prefixID+":"+s)) {
                s = prefixID+":"+s;
            }*/
            sb.append(s).append(" ");
        }
        return sb.toString().trim();
    }

    private TextureAtlas.AtlasRegion getPortrait(AbstractPower p) {
        TextureAtlas.AtlasRegion cardBack = p.type == AbstractPower.PowerType.DEBUFF ? CardLibrary.getCard(VoidCard.ID).portrait : CardLibrary.getCard(Miracle.ID).portrait;
        TextureAtlas.AtlasRegion powerIcon = p.region128;
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
    public void upp() {}

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}
}
