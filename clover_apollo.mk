#
# Copyright (C) 2021 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit some common Clover stuff.
$(call inherit-product, vendor/clover/config/common_full_phone.mk)

# Inherit from apollo device
$(call inherit-product, device/xiaomi/apollo/device.mk)

# Boot animation
TARGET_BOOT_ANIMATION_RES := 1080

# Clover assortment
TARGET_INCLUDE_PIXEL_LAUNCHER := true
TARGET_FACE_UNLOCK_SUPPORTED := true
TARGET_SUPPORTS_GOOGLE_RECORDER := true
TARGET_SUPPORTS_QUICK_TAP := true

PRODUCT_NAME := clover_apollo
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
