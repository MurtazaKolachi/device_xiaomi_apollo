/*
 * Copyright (C) 2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 *
 * ABI compatibility stubs for libcameraimpl.so
 *
 * libcameraimpl was built against MIUI's libcameraservice which exported two
 * symbols that do not exist in LineageOS's libcameraservice:
 *
 * 1. android::camera3::removeInFlightRequestIfReadyLocked(CaptureOutputStates&, int)
 *    MIUI had a 2-arg version; LineageOS only has a 3-arg version (internal, not exported).
 *
 * 2. android::camera3::returnAndRemovePendingOutputBuffers(...)
 *    MIUI-only. IPrivacyCamera class does not exist in LineageOS.
 *
 * Both are no-op stubs using raw mangled names via extern "C".
 * Zero camera headers included — no transitive dependency issues.
 */

extern "C" {

/*
 * android::camera3::removeInFlightRequestIfReadyLocked(
 *     android::camera3::CaptureOutputStates&, int)
 * Mangled: _ZN7android7camera334removeInFlightRequestIfReadyLockedERNS0_19CaptureOutputStatesEi
 */
__attribute__((visibility("default")))
void _ZN7android7camera334removeInFlightRequestIfReadyLockedERNS0_19CaptureOutputStatesEi(
        void* /*states*/, int /*idx*/) {}

/*
 * android::camera3::returnAndRemovePendingOutputBuffers(
 *     bool, sp<NotificationListener>, InFlightRequest&,
 *     SessionStatsBuilder&, IPrivacyCamera*)
 * Mangled: _ZN7android7camera335returnAndRemovePendingOutputBuffersEbNS_2spINS_20NotificationListenerEEERNS0_15InFlightRequestERNS_19SessionStatsBuilderEPNS_14IPrivacyCameraE
 */
__attribute__((visibility("default")))
void _ZN7android7camera335returnAndRemovePendingOutputBuffersEbNS_2spINS_20NotificationListenerEEERNS0_15InFlightRequestERNS_19SessionStatsBuilderEPNS_14IPrivacyCameraE(
        bool, void*, void*, void*, void*) {}

} /* extern "C" */
