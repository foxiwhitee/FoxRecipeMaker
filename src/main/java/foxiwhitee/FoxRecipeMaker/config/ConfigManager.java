package foxiwhitee.FoxRecipeMaker.config;

import foxiwhitee.FoxRecipeMaker.config.parser.Lexer;
import foxiwhitee.FoxRecipeMaker.config.parser.Parser;
import foxiwhitee.FoxRecipeMaker.config.parser.Token;
import foxiwhitee.FoxRecipeMaker.config.parser.statements.Statement;
import foxiwhitee.FoxRecipeMaker.config.utils.BlockInformation;
import foxiwhitee.FoxRecipeMaker.tiles.TileCustomReciper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ConfigManager {
    private static final String configFile = "config/Fox-Mods/FoxRecipeMaker.frm";

    public static final Map<String, String> INTEGRATIONS = new HashMap<>();
    public static final Map<String, String> IMPORTS = new HashMap<>();

    private static final String defaultConfig = ".addIntegration(minecraft, CraftingTable)\n" +
            ".addIntegration(avaritia, Avaritia)\n" +
            ".addIntegration(botania, Botania)\n" +
            ".addIntegration(ExtraUtilities, ExtraUtilities)\n" +
            ".addIntegration(thaumcraft, Thaumcraft)\n" +
            ".addIntegration(ThermalExpansion, ThermalExpansion)\n" +
            ".addIntegration(AWWayofTime, BloodMagic)\n" +
            ".addIntegration(IC2, IC2)\n" +
            "\n" +
            "// Vanilla\n" +
            "// Furnace\n" +
            ".addBlock(minecraft, reciperFurnace, #404040, #404040)\n" +
            ".addGui(reciperFurnace, 216,120, 172,44, 92,52)\n" +
            ".addSlider(reciperFurnace, 0, 16, 85, 184, 20, \"XP \", \"\", 0, 100, 0)\n" +
            ".addContainer(reciperFurnace, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>))\n" +
            ".addOffset(reciperFurnace, 0, -48)\n" +
            ".addPattern(reciperFurnace, \"furnace.addRecipe(<out>, <slot0>, <slider0>);\")\n" +
            ".addLocalization(reciperFurnace, en_US, \"[Vanilla] Furnace Recipe Maker\")\n" +
            "\n" +
            "// Furnace Fuel\n" +
            ".addBlock(minecraft, reciperFurnaceFuel, #404040, #B0B0B0)\n" +
            ".addGui(reciperFurnaceFuel, 216,120, 136,44)\n" +
            ".addSlider(reciperFurnaceFuel, 0, 16, 85, 184, 20, \"Duration \", \"\", 1, 1000000, 1000)\n" +
            ".addContainer(reciperFurnaceFuel, [20, 133]:decorate, <100, 52>:big:decorate, (<0, -9999, -9999>))\n" +
            ".addOffset(reciperFurnaceFuel, 0, -48)\n" +
            ".addPattern(reciperFurnaceFuel, \"furnace.setFuel(<out>, <slider0>);\")\n" +
            ".addLocalization(reciperFurnaceFuel, en_US, \"[Vanilla] Furnace Fuel Recipe Maker\")\n" +
            "\n" +
            "\n" +
            "// Avaritia\n" +
            "// Compressor\n" +
            ".addBlock(avaritia, reciperAvaritiaCompressor, #3c77e3, #75ABFF)\n" +
            ".addGui(reciperAvaritiaCompressor, 216,120, 172,44, 92,52)\n" +
            ".addSlider(reciperAvaritiaCompressor, 0, 16, 85, 184, 20, \"Count \", \"\", 1, 100000, 1000)\n" +
            ".addContainer(reciperAvaritiaCompressor, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>))\n" +
            ".addOffset(reciperAvaritiaCompressor, 0, -48)\n" +
            ".addPattern(reciperAvaritiaCompressor, \"mods.avaritia.Compressor.add(<out>, <slider0>, <slot0>);\")\n" +
            ".addLocalization(reciperAvaritiaCompressor, en_US, \"[Avaritia] Compressor Recipe Maker\")\n" +
            "\n" +
            "// Extreme Crafting\n" +
            ".addBlock(avaritia, reciperAvaritiaExtremeCrafting, #3c77e3, #3c77e3)\n" +
            ".addGui(reciperAvaritiaExtremeCrafting, 256,256, 194,129, 180,87)\n" +
            ".addCheckBox(reciperAvaritiaExtremeCrafting, -2, 183, 38, true, \"Ore\")\n" +
            ".addCheckBox(reciperAvaritiaExtremeCrafting, 0, 183, 49, true, \"Uniform\")\n" +
            ".addContainer(reciperAvaritiaExtremeCrafting, [40, 190], <210, 86>:big,\n" +
            "(<0, 16, 16>, <1, 34, 16>, <2, 52, 16>, <3, 70, 16>, <4, 88, 16>, <5, 106, 16>, <6, 124, 16>, <7, 142, 16>, <8, 160, 16>,\n" +
            "<9, 16, 34>, <10, 34, 34>, <11, 52, 34>, <12, 70, 34>, <13, 88, 34>, <14, 106, 34>, <15, 124, 34>, <16, 142, 34>, <17, 160, 34>,\n" +
            "<18, 16, 52>, <19, 34, 52>, <20, 52, 52>, <21, 70, 52>, <22, 88, 52>, <23, 106, 52>, <24, 124, 52>, <25, 142, 52>, <26, 160, 52>,\n" +
            "<27, 16, 70>, <28, 34, 70>, <29, 52, 70>, <30, 70, 70>, <31, 88, 70>, <32, 106, 70>, <33, 124, 70>, <34, 142, 70>, <35, 160, 70>,\n" +
            "<36, 16, 88>, <37, 34, 88>, <38, 52, 88>, <39, 70, 88>, <40, 88, 88>, <41, 106, 88>, <42, 124, 88>, <43, 142, 88>, <44, 160, 88>,\n" +
            "<45, 16, 106>, <46, 34, 106>, <47, 52, 106>, <48, 70, 106>, <49, 88, 106>, <50, 106, 106>, <51, 124, 106>, <52, 142, 106>, <53, 160, 106>,\n" +
            "<54, 16, 124>, <55, 34, 124>, <56, 52, 124>, <57, 70, 124>, <58, 88, 124>, <59, 106, 124>, <60, 124, 124>, <61, 142, 124>, <62, 160, 124>,\n" +
            "<63, 16, 142>, <64, 34, 142>, <65, 52, 142>, <66, 70, 142>, <67, 88, 142>, <68, 106, 142>, <69, 124, 142>, <70, 142, 142>, <71, 160, 142>,\n" +
            "<72, 16, 160>, <73, 34, 160>, <74, 52, 160>, <75, 70, 160>, <76, 88, 160>, <77, 106, 160>, <78, 124, 160>, <79, 142, 160>, <80, 160, 160>))\n" +
            ".addPattern(reciperAvaritiaExtremeCrafting, \"mods.avaritia.ExtremeCrafting.~<checkbox0> ? \\\"addShaped(<out>, [**[0-9]*, **[9-18]*, **[18-27]*, **[27-36]*, **[36-45]*, **[45-54]*, **[54-72]*, **[72-81]*]);\\\" : \\\"addShapeless(<out>, *[0-81]*);\\\"~\")\n" +
            ".addLocalization(reciperAvaritiaExtremeCrafting, en_US, \"[Avaritia] Extreme Recipe Maker\")\n" +
            "\n" +
            "// ExtraUtilities\n" +
            "// QED\n" +
            ".addBlock(ExtraUtilities, reciperQED, #eed423, #eed423)\n" +
            ".addGui(reciperQED, 216,120, 172,44, 92,52)\n" +
            ".addCheckBox(reciperQED, -2, 16, 16, true, \"Ore\")\n" +
            ".addContainer(reciperQED, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 28, 34>, <1, 46, 34>, <2, 64, 34>, <3, 28, 52>, <4, 46, 52>, <5, 64, 52>, <6, 28, 70>, <7, 46, 70>, <8, 64, 70>))\n" +
            ".addOffset(reciperQED, 0, -48)\n" +
            ".addPattern(reciperQED, \"mods.extraUtils.QED.addShapedRecipe(<out>, [**[0-3]*, **[3-6]*, **[6-9]*]);\")\n" +
            ".addLocalization(reciperQED, en_US, \"[ExtraUtilities] QED Recipe Maker\")\n" +
            "\n" +
            "\n" +
            "// Botania\n" +
            "// Mana Pool\n" +
            ".addBlock(botania, reciperManaPool, #51aff1, #1c99f0)\n" +
            ".addGui(reciperManaPool, 216,120, 172,44, 92,52)\n" +
            ".addCheckBox(reciperManaPool, 0, 16, 16, true, \"Infusion\")\n" +
            ".addCheckBox(reciperManaPool, 1, 72, 16, false, \"Alchemy\")\n" +
            ".addCheckBox(reciperManaPool, 2, 128, 16, false, \"Conjuration\")\n" +
            ".addSlider(reciperManaPool, 3, 16, 85, 184, 20, \"Mana \", \"\", 0, 1000000, 1000)\n" +
            ".addContainer(reciperManaPool, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>))\n" +
            ".addOffset(reciperManaPool, 0, -48)\n" +
            ".addPattern(reciperManaPool, \"mods.botania.ManaInfusion.~<checkbox0> ? \\\"addInfusion\\\" : <checkbox1> ? \\\"addAlchemy\\\" : <checkbox2> ? \\\"addConjuration\\\" : \\\"\\\"~(<out>, <slot0>, <slider1>);\")\n" +
            ".addLocalization(reciperManaPool, en_US, \"[Botania] Mana Pool Recipe Maker\")\n" +
            "\n" +
            "// Petals\n" +
            ".addBlock(botania, reciperPetals, #51aff1, #F5FC2D)\n" +
            ".addGui(reciperPetals, 256,256, 71,71, 171,80)\n" +
            ".addContainer(reciperPetals, [40, 190], <205, 80>:big, (\n" +
            "<1, 79, 9>, <2, 104, 19>, <3, 129, 29>, <4, 139, 54>, <5, 149, 79>, <6, 139, 105>, <7, 129, 130>, <8, 104, 140>,\n" +
            "<9, 79, 150>, <10, 54, 140>, <11, 29, 130>, <12, 19, 105>, <13, 9, 79>, <14, 19, 54>, <15, 29, 29>, <16, 54, 19>,\n" +
            "))\n" +
            ".addPattern(reciperPetals, \"mods.botania.Apothecary.addRecipe(<out>, *[1-17]*);\")\n" +
            ".addLocalization(reciperPetals, en_US, \"[Botania] Petal Apothecary Recipe Maker\")\n" +
            "\n" +
            "// Rune Altar\n" +
            ".addBlock(botania, reciperRuneAltar, #51aff1, #debb96)\n" +
            ".addGui(reciperRuneAltar, 256,256, 71,71, 171,80)\n" +
            ".addSlider(reciperRuneAltar, 0, 16, 169, 224, 20, \"Mana \", \"\", 0, 1000000, 1000)\n" +
            ".addContainer(reciperRuneAltar, [40, 199], <205, 80>:big, (\n" +
            "<1, 79, 9>, <2, 104, 19>, <3, 129, 29>, <4, 139, 54>, <5, 149, 79>, <6, 139, 105>, <7, 129, 130>, <8, 104, 140>,\n" +
            "<9, 79, 150>, <10, 54, 140>, <11, 29, 130>, <12, 19, 105>, <13, 9, 79>, <14, 19, 54>, <15, 29, 29>, <16, 54, 19>,\n" +
            "))\n" +
            ".addPattern(reciperRuneAltar, \"mods.botania.RuneAltar.addRecipe(<out>, *[1-17]*, <slider0>);\")\n" +
            ".addLocalization(reciperRuneAltar, en_US, \"[Botania] Rune Altar Recipe Maker\")\n" +
            "\n" +
            "// Pure Daisy\n" +
            ".addBlock(botania, reciperPureDaisy, #51aff1, #ffffff)\n" +
            ".addGui(reciperPureDaisy, 216,120, 172,44, 92,52)\n" +
            ".addContainer(reciperPureDaisy, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>))\n" +
            ".addOffset(reciperPureDaisy, 0, -48)\n" +
            ".addPattern(reciperPureDaisy, \"mods.botania.PureDaisy.(<slot0>, <out>);\")\n" +
            ".addLocalization(reciperPureDaisy, en_US, \"[Botania] Pure Daisy Recipe Maker\")\n" +
            "\n" +
            "// Elven Trade\n" +
            ".addBlock(botania, reciperElvenTrade, #51aff1, #2DFC41)\n" +
            ".addGui(reciperElvenTrade, 216,120, 172,44, 92,52)\n" +
            ".addContainer(reciperElvenTrade, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 28, 52>, <1, 46, 52>, <2, 64, 52>))\n" +
            ".addOffset(reciperElvenTrade, 0, -48)\n" +
            ".addPattern(reciperElvenTrade, \"mods.botania.PureDaisy.(<out>, *[0-3]*);\")\n" +
            ".addLocalization(reciperElvenTrade, en_US, \"[Botania] Elven Trade Recipe Maker\")\n" +
            "\n" +
            "\n" +
            "// Thermal Expansion\n" +
            "// Redstone Furnace\n" +
            ".addBlock(ThermalExpansion, reciperRedstoneFurnace, #70db70, #70db70)\n" +
            ".addGui(reciperRedstoneFurnace, 216,120, 172,44, 92,52)\n" +
            ".addSlider(reciperRedstoneFurnace, 0, 16, 85, 184, 20, \"Energy \", \" RF\", 0, 1000000, 1000)\n" +
            ".addContainer(reciperRedstoneFurnace, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>))\n" +
            ".addOffset(reciperRedstoneFurnace, 0, -50)\n" +
            ".addPattern(reciperRedstoneFurnace, \"mods.thermalexpansion.Furnace.addRecipe(<slider0>, <slot0>, <out>);\")\n" +
            ".addLocalization(reciperRedstoneFurnace, en_US, \"[ThermalExpansion] Redstone Furnace Recipe Maker\")\n" +
            "\n" +
            "// Insolator\n" +
            ".addBlock(ThermalExpansion, reciperInsolator, #70db70, #eed423)\n" +
            ".addGui(reciperInsolator, 216,140, 172,44, 92,52)\n" +
            ".addSlider(reciperInsolator, 0, 16, 85, 184, 20, \"Energy \", \" RF\", 0, 1000000, 1000)\n" +
            ".addSlider(reciperInsolator, 1, 16, 105, 184, 20, \"Chance \", \"\", 0, 100, 100)\n" +
            ".addContainer(reciperInsolator, [20, 153]:decorate, <136, 52>:big, (<0, 46, 52>, <1, 64, 52>, <2, 136, 24>))\n" +
            ".addOffset(reciperInsolator, 0, -50)\n" +
            ".addPattern(reciperInsolator, \"mods.thermalexpansion.Insolator.addRecipe(<slider0>, <slot0>, <slot1>, <out>, <slot2>, <slider1>);\")\n" +
            ".addLocalization(reciperInsolator, en_US, \"[ThermalExpansion] Phytogenic Insolator Recipe Maker\")\n" +
            "\n" +
            "// Pulverizer\n" +
            ".addBlock(ThermalExpansion, reciperPulverizer, #70db70, #B0B0B0)\n" +
            ".addGui(reciperPulverizer, 216,140, 172,44, 92,52)\n" +
            ".addSlider(reciperPulverizer, 0, 16, 85, 184, 20, \"Energy \", \" RF\", 0, 1000000, 1000)\n" +
            ".addSlider(reciperPulverizer, 1, 16, 105, 184, 20, \"Chance \", \"\", 0, 100, 100)\n" +
            ".addContainer(reciperPulverizer, [20, 153]:decorate, <136, 52>:big, (<1, 64, 52>, <2, 136, 24>))\n" +
            ".addOffset(reciperPulverizer, 0, -50)\n" +
            ".addPattern(reciperPulverizer, \"mods.thermalexpansion.Pulverizer.addRecipe(<slider0>, <slot1>, <out>, <slot2>, <slider1>);\")\n" +
            ".addLocalization(reciperPulverizer, en_US, \"[ThermalExpansion] Pulverizer Recipe Maker\")\n" +
            "\n" +
            "// Sawmill\n" +
            ".addBlock(ThermalExpansion, reciperSawmill, #70db70, #75ABFF)\n" +
            ".addGui(reciperSawmill, 216,140, 172,44, 92,52)\n" +
            ".addSlider(reciperSawmill, 0, 16, 85, 184, 20, \"Energy \", \" RF\", 0, 1000000, 1000)\n" +
            ".addSlider(reciperSawmill, 1, 16, 105, 184, 20, \"Chance \", \"\", 0, 100, 100)\n" +
            ".addContainer(reciperSawmill, [20, 153]:decorate, <136, 52>:big, (<1, 64, 52>, <2, 136, 24>))\n" +
            ".addOffset(reciperSawmill, 0, -50)\n" +
            ".addPattern(reciperSawmill, \"mods.thermalexpansion.Sawmill.addRecipe(<slider0>, <slot1>, <out>, <slot2>, <slider1>);\")\n" +
            ".addLocalization(reciperSawmill, en_US, \"[ThermalExpansion] Sawmill Recipe Maker\")\n" +
            "\n" +
            "// Induction Smelter\n" +
            ".addBlock(ThermalExpansion, reciperSmelter, #70db70, #ff5656)\n" +
            ".addGui(reciperSmelter, 216,140, 172,44, 92,52)\n" +
            ".addSlider(reciperSmelter, 0, 16, 85, 184, 20, \"Energy \", \" RF\", 0, 1000000, 1000)\n" +
            ".addSlider(reciperSmelter, 1, 16, 105, 184, 20, \"Chance \", \"\", 0, 100, 100)\n" +
            ".addContainer(reciperSmelter, [20, 153]:decorate, <136, 52>:big, (<0, 46, 52>, <1, 64, 52>, <2, 136, 24>))\n" +
            ".addOffset(reciperSmelter, 0, -50)\n" +
            ".addPattern(reciperSmelter, \"mods.thermalexpansion.Smelter.addRecipe(<slider0>, <slot0>, <slot1>, <out>, <slot2>, <slider1>);\")\n" +
            ".addLocalization(reciperSmelter, en_US, \"[ThermalExpansion] Induction Smelter Recipe Maker\")\n" +
            "\n" +
            "// IC2\n" +
            "// Compressor\n" +
            ".addBlock(IC2, reciperCompressor, #47f4fa, #70db70)\n" +
            ".addGui(reciperCompressor, 216,120, 172,44, 92,52)\n" +
            ".addContainer(reciperCompressor, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>))\n" +
            ".addOffset(reciperCompressor, 0, -50)\n" +
            ".addPattern(reciperCompressor, \"mods.ic2.Compressor.addRecipe(<out>, <slot0>);\")\n" +
            ".addLocalization(reciperCompressor, en_US, \"[IC2] Compressor Recipe Maker\")\n" +
            "\n" +
            "// Extractor\n" +
            ".addBlock(IC2, reciperExtractor, #47f4fa, #e460fc)\n" +
            ".addGui(reciperExtractor, 216,120, 172,44, 92,52)\n" +
            ".addContainer(reciperExtractor, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>))\n" +
            ".addOffset(reciperExtractor, 0, -50)\n" +
            ".addPattern(reciperExtractor, \"mods.ic2.Extractor.addRecipe(<out>, <slot0>);\")\n" +
            ".addLocalization(reciperExtractor, en_US, \"[IC2] Extractor Recipe Maker\")\n" +
            "\n" +
            "// Macerator\n" +
            ".addBlock(IC2, reciperMacerator, #47f4fa, #89cefe)\n" +
            ".addGui(reciperMacerator, 216,120, 172,44, 92,52)\n" +
            ".addContainer(reciperMacerator, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>))\n" +
            ".addOffset(reciperMacerator, 0, -50)\n" +
            ".addPattern(reciperMacerator, \"mods.ic2.Macerator.addRecipe(<out>, <slot0>);\")\n" +
            ".addLocalization(reciperMacerator, en_US, \"[IC2] Macerator Recipe Maker\")\n" +
            "\n" +
            "// MetalFormer\n" +
            ".addBlock(IC2, reciperMetalFormer, #47f4fa, #F54927)\n" +
            ".addGui(reciperMetalFormer, 216,120, 172,44, 92,52)\n" +
            ".addCheckBox(reciperMetalFormer, 0, 16, 16, true, \"Cutting\")\n" +
            ".addCheckBox(reciperMetalFormer, 1, 128, 16, false, \"Extruding\")\n" +
            ".addCheckBox(reciperMetalFormer, 2, 72, 16, false, \"Rolling\")\n" +
            ".addContainer(reciperMetalFormer, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>))\n" +
            ".addOffset(reciperMetalFormer, 0, -50)\n" +
            ".addPattern(reciperMetalFormer, \"mods.ic2.MetalFormer.add~<checkbox0> ? \\\"Cutting\\\" : <checkbox1> ? \\\"Extruding\\\" : <checkbox2> ? \\\"Rolling\\\" : \\\"\\\"~Recipe(<out>, <slot0>);\")\n" +
            ".addLocalization(reciperMetalFormer, en_US, \"[IC2] MetalFormer Recipe Maker\")\n" +
            "\n" +
            "// Blood Magic\n" +
            "// Blood Altar\n" +
            ".addBlock(AWWayofTime, reciperAltar, #d53b3b, #d53b3b)\n" +
            ".addGui(reciperAltar, 216,120, 172,44, 92,52)\n" +
            ".addSlider(reciperAltar, 0, 16, 85, 184, 20, \"\", \" LP\", 0, 1000000, 1000)\n" +
            ".addContainer(reciperAltar, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 64, 52>, <1, 40, 52>))\n" +
            ".addOffset(reciperAltar, 0, -50)\n" +
            ".addPattern(reciperAltar, \"mods.bloodmagic.Altar.addRecipe(<out>, <slot0>, <slot1>.stack, <slider0>);\")\n" +
            ".addLocalization(reciperAltar, en_US, \"[BloodMagic] Blood Altar Recipe Maker\")\n" +
            "\n" +
            "// Alchemical Chemistry Set\n" +
            ".addBlock(AWWayofTime, reciperAlchemy, #d53b3b, #ffffff)\n" +
            ".addGui(reciperAlchemy, 216,120, 172,44)\n" +
            ".addSlider(reciperAlchemy, 0, 16, 85, 184, 20, \"\", \" LP\", 0, 1000000, 1000)\n" +
            ".addContainer(reciperAlchemy, [20, 133]:decorate, <80, 35>, (<0, 140, 57>, <1, 80, 13>, <2, 105, 29>, <3, 92, 57>, <4, 68, 57>, <5, 55, 29>))\n" +
            ".addOffset(reciperAlchemy, 0, -50)\n" +
            ".addPattern(reciperAlchemy, \"mods.bloodmagic.Alchemy.addRecipe(<out>, *[1-6]*, <slot0>.stack, <slider0>);\")\n" +
            ".addLocalization(reciperAlchemy, en_US, \"[BloodMagic] Alchemical Chemistry Set Recipe Maker\")\n" +
            "\n" +
            "// Blood Orb\n" +
            ".addBlock(AWWayofTime, reciperOrb, #d53b3b, #51aff1)\n" +
            ".addGui(reciperOrb, 216,120, 172,44, 92,52)\n" +
            ".addCheckBox(reciperOrb, 0, 16, 16, true, \"Uniform\")\n" +
            ".addContainer(reciperOrb, [20, 133]:decorate, <136, 52>:big:decorate, (<0, 28, 34>, <1, 46, 34>, <2, 64, 34>, <3, 28, 52>, <4, 46, 52>, <5, 64, 52>, <6, 28, 70>, <7, 46, 70>, <8, 64, 70>))\n" +
            ".addOffset(reciperOrb, 0, -48)\n" +
            ".addPattern(reciperOrb, \"mods.bloodmagic.BloodOrb.~<checkbox0> ? \\\"addShaped(<out>, [**[0-3]*, **[3-6]*, **[6-9]*]);\\\" : \\\"addShapeless(<out>, *[0-9]*);\\\"\")\n" +
            ".addLocalization(reciperOrb, en_US, \"[ExtraUtilities] Blood Orb Recipe Maker\")\n" +
            "\n";

    public static void run() throws IOException {
        Path path = Paths.get(configFile);
        Files.createDirectories(path.getParent());

        if (!Files.exists(path)) {
            Files.createFile(path);

            Files.write(path, defaultConfig.getBytes(StandardCharsets.UTF_8));
        }
        String code = Files.lines(path)
                .collect(Collectors.joining("\n"));

        List<Token> tokens = new Lexer(code).tokenize();
        Statement config = new Parser(tokens).parse();
        config.execute();
    }
}
