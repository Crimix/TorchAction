package com.black_dog20.torchaction.common.utils;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.torchaction.TorchAction;

public enum Translations implements ITranslation {
    CATEGORY("keys.category"),
    ITEM_CATEGORY("itemGroup.torchaction"),
    KEY_PLACE_TORCH("keys.place_torch"),

    AMOUNT_TORCHES("tooltip.amount_torches"),
    PLACE_TORCH_KEY("tooltip.place_torch_key"),
    AUTO_PICKUP_TOGGLE("tooltip.auto_pickup_toggle"),
    AUTO_PICKUP("tooltip.auto_pickup"),
    ON("tooltip.on"),
    OFF("tooltip.off"),
    ;

    private final String modId;
    private final String key;

    Translations(String key) {
        this.modId = TorchAction.MOD_ID;
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getModId() {
        return modId;
    }
}
