package Starlight;

import Starlight.cards.cardvars.Info;
import Starlight.cards.cardvars.SecondDamage;
import Starlight.cards.cardvars.SecondMagicNumber;
import Starlight.cards.interfaces.LunaCard;
import Starlight.cards.interfaces.PrimroseCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.characters.StarlightSisters;
import Starlight.patches.CardCounterPatches;
import Starlight.potions.*;
import Starlight.powers.*;
import Starlight.relics.AbstractEasyRelic;
import Starlight.ui.*;
import Starlight.util.*;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class TheStarlightMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber,
        PostDungeonInitializeSubscriber,
        StartActSubscriber,
        PostDeathSubscriber,
        PostBattleSubscriber {

    public static final String modID = "Starlight";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static final Color METEORITE_PURPLE = CardHelper.getColor(60, 31, 118);

    public static final String SHOULDER1 = modID + "Resources/images/char/mainChar/shoulderHappy.png";
    public static final String SHOULDER2 = modID + "Resources/images/char/mainChar/shoulderLook.png";
    public static final String CORPSE = modID + "Resources/images/char/mainChar/corpse.png";
    private static final String ATTACK_S_ART = modID + "Resources/images/512/bg_attack_space4.png";
    private static final String SKILL_S_ART = modID + "Resources/images/512/bg_skill_space4.png";
    private static final String POWER_S_ART = modID + "Resources/images/512/bg_power_space4.png";
    private static final String ATTACK_L_ART = modID + "Resources/images/1024/bg_attack_space4.png";
    private static final String SKILL_L_ART = modID + "Resources/images/1024/bg_skill_space4.png";
    private static final String POWER_L_ART = modID + "Resources/images/1024/bg_power_space4.png";
    private static final String CARD_ENERGY_S = modID + "Resources/images/512/energy.png";
    private static final String TEXT_ENERGY = modID + "Resources/images/512/text_energy.png";
    private static final String CARD_ENERGY_L = modID + "Resources/images/1024/energy.png";
    private static final String CHARSELECT_BUTTON = modID + "Resources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = modID + "Resources/images/charSelect/charBG3.png";

    public static final String BADGE_IMAGE = modID + "Resources/images/Badge.png";

    public static UIStrings uiStrings;
    public static String[] TEXT;
    public static String[] EXTRA_TEXT;
    private static final String AUTHOR = "Mistress Alison";

    // Mod-settings settings. This is if you want an on/off savable button
    public static SpireConfig starlightConfig;

    public static final String ENABLE_CHIMERA_CROSSOVER = "enableChimeraCrossover";
    public static boolean enableChimeraCrossover = true; // The boolean we'll be setting on/off (true/false)

    public static final String ENABLE_CARD_BATTLE_TALK_SETTING = "enableCardBattleTalk";
    public static boolean enableCardBattleTalkEffect = false;

    public static final String CARD_BATTLE_TALK_PROBABILITY_SETTING = "cardTalkProbability";
    public static int cardTalkProbability = 10; //Out of 100

    public static final String ENABLE_DAMAGED_BATTLE_TALK_SETTING = "enableDamagedBattleTalk";
    public static boolean enableDamagedBattleTalkEffect = false;

    public static final String DAMAGED_BATTLE_TALK_PROBABILITY_SETTING = "damagedTalkProbability";
    public static int damagedTalkProbability = 20; //Out of 100

    public static final String ENABLE_PRE_BATTLE_TALK_SETTING = "enablePreBattleTalk";
    public static boolean enablePreBattleTalkEffect = false;

    public static final String PRE_BATTLE_TALK_PROBABILITY_SETTING = "preTalkProbability";
    public static int preTalkProbability = 50; //Out of 100

    public static final String PRIMROSE_BOOK_INDEX = "PRIM_BOOK";
    public static final String LUNA_BOOK_INDEX = "LUNA_BOOK";
    public static int primroseBookIndex = 1;
    public static int lunaBookIndex = 0;

    public static final int MAX_INDEX = 3;

    //public static AbilityButton abilityButton;
    public static SpellbookUI spellUI;
    public static FilterUI filterUI;

    public TheStarlightMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(StarlightSisters.Enums.METEORITE_PURPLE_COLOR, METEORITE_PURPLE, METEORITE_PURPLE, METEORITE_PURPLE,
                METEORITE_PURPLE, METEORITE_PURPLE, METEORITE_PURPLE, METEORITE_PURPLE,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        Properties starlightDefaultSettings = new Properties();
        starlightDefaultSettings.setProperty(ENABLE_CHIMERA_CROSSOVER, Boolean.toString(enableChimeraCrossover));

        starlightDefaultSettings.setProperty(ENABLE_CARD_BATTLE_TALK_SETTING, Boolean.toString(enableCardBattleTalkEffect));
        starlightDefaultSettings.setProperty(CARD_BATTLE_TALK_PROBABILITY_SETTING, String.valueOf(cardTalkProbability));
        starlightDefaultSettings.setProperty(ENABLE_DAMAGED_BATTLE_TALK_SETTING, Boolean.toString(enableDamagedBattleTalkEffect));
        starlightDefaultSettings.setProperty(DAMAGED_BATTLE_TALK_PROBABILITY_SETTING, String.valueOf(damagedTalkProbability));
        starlightDefaultSettings.setProperty(ENABLE_PRE_BATTLE_TALK_SETTING, Boolean.toString(enablePreBattleTalkEffect));
        starlightDefaultSettings.setProperty(PRE_BATTLE_TALK_PROBABILITY_SETTING, String.valueOf(preTalkProbability));
        starlightDefaultSettings.setProperty(PRIMROSE_BOOK_INDEX, Integer.toString(primroseBookIndex));
        starlightDefaultSettings.setProperty(LUNA_BOOK_INDEX, Integer.toString(lunaBookIndex));
        try {
            starlightConfig = new SpireConfig("StarlightMod", "StarlightModConfig", starlightDefaultSettings);
            enableChimeraCrossover = starlightConfig.getBool(ENABLE_CHIMERA_CROSSOVER);
            enableCardBattleTalkEffect = starlightConfig.getBool(ENABLE_CARD_BATTLE_TALK_SETTING);
            cardTalkProbability = starlightConfig.getInt(CARD_BATTLE_TALK_PROBABILITY_SETTING);
            enableDamagedBattleTalkEffect = starlightConfig.getBool(ENABLE_DAMAGED_BATTLE_TALK_SETTING);
            damagedTalkProbability = starlightConfig.getInt(DAMAGED_BATTLE_TALK_PROBABILITY_SETTING);
            enablePreBattleTalkEffect = starlightConfig.getBool(ENABLE_PRE_BATTLE_TALK_SETTING);
            preTalkProbability = starlightConfig.getInt(PRE_BATTLE_TALK_PROBABILITY_SETTING);
            primroseBookIndex = starlightConfig.getInt(PRIMROSE_BOOK_INDEX);
            lunaBookIndex = starlightConfig.getInt(LUNA_BOOK_INDEX);
            if (primroseBookIndex > MAX_INDEX || primroseBookIndex == lunaBookIndex) {
                primroseBookIndex = lunaBookIndex == 1 ? 0 : 1;
            }
            if (lunaBookIndex > MAX_INDEX) {
                lunaBookIndex = primroseBookIndex == 0 ? 1 : 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        TheStarlightMod thismod = new TheStarlightMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new StarlightSisters(StarlightSisters.characterStrings.NAMES[1], StarlightSisters.Enums.THE_SISTERS),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, StarlightSisters.Enums.THE_SISTERS);

        BaseMod.addPotion(SPPotion.class, new Color(65/255f,138/255f,217/255f,1), new Color(87/255f,66/255f,245/255f,1), null, SPPotion.POTION_ID, StarlightSisters.Enums.THE_SISTERS);
        BaseMod.addPotion(JinxPotion.class, new Color(95/255f,46/255f,209/255f,1), new Color(40/255f,20/255f,107/255f,1), null, JinxPotion.POTION_ID, StarlightSisters.Enums.THE_SISTERS);
        BaseMod.addPotion(TimeSplitter.class, new Color(255/255f,255/255f,255/255f,1), new Color(0/255f,0/255f,0/255f,1), null, TimeSplitter.POTION_ID, StarlightSisters.Enums.THE_SISTERS);
        BaseMod.addPotion(EfficiencyPotion.class, new Color(65/255f,217/255f,90/255f,1), new Color(5/255f,66/255f,15/255f,1), null, EfficiencyPotion.POTION_ID, StarlightSisters.Enums.THE_SISTERS);
        BaseMod.addPotion(BottledFists.class, new Color(217/255f,215/255f,65/255f,1), new Color(105/255f,101/255f,12/255f,1), null, BottledFists.POTION_ID, StarlightSisters.Enums.THE_SISTERS);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        BaseMod.addDynamicVariable(new Info());
        new AutoAdd(modID)
                .packageFilter("Starlight.cards")
                .setDefaultSeen(true)
                .cards();
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/Cardstrings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/eng/Augmentstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/eng/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/eng/Charstrings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/eng/Powerstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/CardModstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/Chatterstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/DamageModstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/Abilitystrings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/eng/UIstrings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/eng/Potionstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/BookOfWaterstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/BookOfIcestrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/BookOfFirestrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/BookOfNaturestrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receivePostInitialize() {

        if (Loader.isModLoaded("widepotions")) {


            //Simple Potions

            WidePotionsMod.whitelistSimplePotion(SPPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(JinxPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(TimeSplitter.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(EfficiencyPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(BottledFists.POTION_ID);

            //Complex Potions

            //WidePotionsMod.whitelistComplexPotion(MyOtherPotion.POTION_ID, new WideMyOtherPotion());
        }

        if (Loader.isModLoaded("CardAugments")) {
            AugmentHelper.register();
        }

        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ModConfigs"));
        EXTRA_TEXT = uiStrings.EXTRA_TEXT;
        TEXT = uiStrings.TEXT;
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Load the Mod Badge
        Texture badgeTexture = TexLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, EXTRA_TEXT[0], AUTHOR, EXTRA_TEXT[1], settingsPanel);

        //Get the longest slider text for positioning
        ArrayList<String> labelStrings = new ArrayList<>(Arrays.asList(TEXT));
        float sliderOffset = getSliderPosition(labelStrings);
        labelStrings.clear();
        float currentYposition = 740f;
        float spacingY = 55f;

        //Used to set the tutorial setting
        /*showBoosterTutorialButton = new ModLabeledToggleButton(TEXT[0],350.0f, currentYposition, Settings.CREAM_COLOR, FontHelper.charDescFont,
                !m10RobotConfig.getBool(BOOSTER_TUTORIAL_SEEN), settingsPanel, (label) -> {}, (button) -> {
            m10RobotConfig.setBool(BOOSTER_TUTORIAL_SEEN, !button.enabled);
            boosterTutorialSeen = !button.enabled;
            try {m10RobotConfig.save();} catch (IOException e) {e.printStackTrace();}
        });
        currentYposition -= spacingY;*/

        //Used to set the unused self damage setting.
        ModLabeledToggleButton enableChimeraCrossoverButton = new ModLabeledToggleButton(TEXT[0],350.0f, currentYposition, Settings.CREAM_COLOR, FontHelper.charDescFont,
                starlightConfig.getBool(ENABLE_CHIMERA_CROSSOVER), settingsPanel, (label) -> {}, (button) -> {
            starlightConfig.setBool(ENABLE_CHIMERA_CROSSOVER, button.enabled);
            enableChimeraCrossover = button.enabled;
            try {
                starlightConfig.save();} catch (IOException e) {e.printStackTrace();}
        });
        currentYposition -= spacingY;

        settingsPanel.addUIElement(enableChimeraCrossoverButton);


        DynamicTextBlocks.registerCustomCheck(makeID("IsSisters"), card -> {
            if (!CardCrawlGame.isInARun() || Wiz.adp() instanceof StarlightSisters) {
                return 1;
            }
            return 0;
        });
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            private final Color c = Settings.GOLD_COLOR.cpy();
            private final Color prim = new Color(0xdf7e86ff);
            private final Color luna = new Color(0x996ab0ff);

            @Override
            public boolean test(AbstractCard abstractCard) {
                return tagTest(abstractCard);
            }

            @Override
            public Color getColor(AbstractCard abstractCard) {
                /*if (Wiz.adp() instanceof StarlightSisters) {
                    if (((StarlightSisters) Wiz.adp()).attackerInFront) {
                        return luna;
                    } else {
                        return prim;
                    }
                }*/
                return c;
            }

            @Override
            public String glowID() {
                return makeID("TagTeamGlow");
            }
        });
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            private final Color c = Settings.GOLD_COLOR.cpy();

            @Override
            public boolean test(AbstractCard abstractCard) {
                return primroseTest(abstractCard);
            }

            @Override
            public Color getColor(AbstractCard abstractCard) {
                return c;
            }

            @Override
            public String glowID() {
                return makeID("PrimroseGlow");
            }
        });
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            private final Color c = Settings.GOLD_COLOR.cpy();

            @Override
            public boolean test(AbstractCard abstractCard) {
                return lunaTest(abstractCard);
            }

            @Override
            public Color getColor(AbstractCard abstractCard) {
                return c;
            }

            @Override
            public String glowID() {
                return makeID("LunaGlow");
            }
        });
        /*BaseMod.addSaveField(makeID("Abilities"), new CustomSavable<AbilityManager.AbilitySaveData>() {

            @Override
            public AbilityManager.AbilitySaveData onSave() {
                return new AbilityManager.AbilitySaveData(AbilityManager.investedAbilityLevels, AbilityManager.abilityPoints, AbilityManager.timesProvided);
            }

            @Override
            public void onLoad(AbilityManager.AbilitySaveData abilitySaveData) {
                AbilityManager.load(abilitySaveData);
            }
        });*/

        BaseMod.addPower(JinxPower.class, JinxPower.POWER_ID);
        BaseMod.addPower(SpellPower.class, SpellPower.POWER_ID);
        BaseMod.addPower(ReversePower.class, ReversePower.POWER_ID);
        BaseMod.addPower(AscensionPower.class, AscensionPower.POWER_ID);

        //abilityButton = new AbilityButton();
        spellUI = new SpellbookUI();
        //filterUI = new FilterUI();
    }

    //Get the longest text so all sliders are centered
    private float getSliderPosition (ArrayList<String> stringsToCompare) {
        float longest = 0;
        for (String s : stringsToCompare) {
            longest = Math.max(longest, FontHelper.getWidth(FontHelper.charDescFont, s, 1 / Settings.scale));
        }
        return longest + 60f;
    }

    public static boolean tagTest(AbstractCard c) {
        if (c instanceof TagTeamCard) {
            if (AbstractDungeon.player instanceof StarlightSisters) {
                return CardCounterPatches.swapsThisTurn > 0;
            } else {
                AbstractCard last = Wiz.lastCardPlayed();
                if (last != null) {
                    switch (last.type) {
                        case ATTACK:
                            return c.type == AbstractCard.CardType.SKILL;
                        case SKILL:
                            return c.type == AbstractCard.CardType.ATTACK;
                    }
                }
            }
        }
        return false;
    }

    public static boolean primroseTest(AbstractCard c) {
        if (c instanceof PrimroseCard) {
            if (Wiz.adp() instanceof StarlightSisters) {
                return ((StarlightSisters) Wiz.adp()).attackerInFront;
            } else {
                AbstractCard last = Wiz.lastCardPlayed();
                return last != null && last.type == AbstractCard.CardType.ATTACK;
            }
        }
        return false;
    }

    public static boolean lunaTest(AbstractCard c) {
        if (c instanceof LunaCard) {
            if (Wiz.adp() instanceof StarlightSisters) {
                return !((StarlightSisters) Wiz.adp()).attackerInFront;
            } else {
                AbstractCard last = Wiz.lastCardPlayed();
                return last != null && last.type == AbstractCard.CardType.SKILL;
            }
        }
        return false;
    }

    @Override
    public void receiveStartGame() {
        LoopingSoundManager.stopAllLoopedSounds();
        ProjectedCardManager.EmptyCards.yeet();
        EnvisionedCardManager.EmptyCards.yeet();
        /*BaseMod.removeTopPanelItem(abilityButton);
        if (Wiz.adp() instanceof StarlightSisters) {
            BaseMod.addTopPanelItem(abilityButton);
        }*/
    }

    @Override
    public void receiveStartAct() {
        /*if (AbstractDungeon.actNum > AbilityManager.timesProvided) {
            AbilityManager.actStartGainPoint();
        }*/
    }

    @Override
    public void receivePostDungeonInitialize() {
        //AbilityManager.reset();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        LoopingSoundManager.stopAllLoopedSounds();
    }

    @Override
    public void receivePostDeath() {
        LoopingSoundManager.stopAllLoopedSounds();
    }
}
