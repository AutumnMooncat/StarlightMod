package Starlight.util;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class CustomTags {
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_CAST_ANIM;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_APPLIES_BURN;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_APPLIES_CHILL;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_ARROW;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_ASCENDED;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_FIRE;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_WATER;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_ICE;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_NATURE;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_DARK;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_LIGHT;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_STORM;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_VOID;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_SAND;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_SPACE;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_TIME;
    @SpireEnum
    public static AbstractCard.CardTags STARLIGHT_EARTH;

    public static ArrayList<AbstractCard.CardTags> getMagicTags() {
        ArrayList<AbstractCard.CardTags> l = new ArrayList<>();
        l.add(STARLIGHT_FIRE);
        l.add(STARLIGHT_WATER);
        l.add(STARLIGHT_ICE);
        l.add(STARLIGHT_NATURE);
        l.add(STARLIGHT_DARK);
        l.add(STARLIGHT_LIGHT);
        l.add(STARLIGHT_STORM);
        l.add(STARLIGHT_VOID);
        l.add(STARLIGHT_SAND);
        l.add(STARLIGHT_SPACE);
        l.add(STARLIGHT_TIME);
        l.add(STARLIGHT_EARTH);
        return l;
    }
}
