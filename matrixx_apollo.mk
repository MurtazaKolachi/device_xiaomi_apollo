#
# Copyright (C) 2021 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit some common Lineage stuff.
$(call inherit-product, vendor/matrixx/config/common_full_phone.mk)

# Inherit from apollo device
$(call inherit-product, device/xiaomi/apollo/device.mk)

# Matrixx Goodies
$(call soong_config_set,surfaceflinger,frame_rate_category_high,144)
$(call soong_config_set,surfaceflinger,frame_rate_category_min,60)
TARGET_SUPPORTED_REFRESH_RATES := 60,90,120,144
TARGET_BOOT_ANIMATION_RES := 1080
WITH_GMS := false
TARGET_INCLUDE_PIXEL_LAUNCHER := false
TARGET_DEFAULT_PIXEL_LAUNCHER := false
WITH_GMS_COMMS_SUITE := false
WITH_GMS_AICORE := false
WITH_BCR := false
MATRIXX_MAINTAINER := Murtaza Kolachi

PRODUCT_NAME := matrixx_apollo
PRODUCT_DEVICE := apollo
PRODUCT_MANUFACTURER := Xiaomi
PRODUCT_BRAND := Xiaomi
PRODUCT_MODEL := Xiaomi Mi 10T

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi

PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="apollo_global-user 12 RKQ1.211001.001 V14.0.4.0.SJDMIXM release-keys" \
    BuildFingerprint=Xiaomi/apollo_global/apollo:12/RKQ1.211001.001/V14.0.4.0.SJDMIXM:user/release-keys

# Keys
-include vendor/lineage-priv/keys/keys.mk
