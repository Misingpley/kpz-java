package ua.duikt.holoshchuk.lr8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.vendor.trial.TrialPolicy;
import ua.vendor.trial.Vendor;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class TrialPolicyAcceptanceTest {

    private static final ZoneId KYIV_ZONE = ZoneId.of("Europe/Kyiv");
    private static final Clock FIXED_CLOCK_MAY_10 = Clock.fixed(Instant.parse("2026-05-10T09:00:00Z"), KYIV_ZONE);

    private int buildNumber;
    private TrialPolicy policy;

    @BeforeEach
    void setUp() {
        buildNumber = Integer.getInteger("build", 4);
        policy = Vendor.build(buildNumber, FIXED_CLOCK_MAY_10);
    }

    @Test
    void freeOnFirstDayOfTrial() {
        LocalDate registered = LocalDate.of(2026, 5, 10);
        assertEquals(0, policy.chargeAmount(registered, null));
    }

    @Test
    void freeOnSeventhCalendarDayWhileTrialIsActive() {
        LocalDate registered = LocalDate.of(2026, 5, 4);
        assertEquals(0, policy.chargeAmount(registered, null));
    }

    @Test
    void chargeOnEighthCalendarDayWhenNotCancelled() {
        LocalDate registered = LocalDate.of(2026, 5, 3);
        assertEquals(199, policy.chargeAmount(registered, null));
    }

    @Test
    void usesKyivTimezoneForTodayBoundaryAfterMidnight() {
        Clock kyivAfterMidnightClock = Clock.fixed(Instant.parse("2026-05-07T22:30:00Z"), ZoneId.of("UTC"));
        TrialPolicy kyivPolicy = Vendor.build(buildNumber, kyivAfterMidnightClock);
        LocalDate registered = LocalDate.of(2026, 5, 1);
        assertEquals(199, kyivPolicy.chargeAmount(registered, null));
    }

    @Test
    void freeWhenCancelledOnLastDayOfTrial() {
        LocalDate registered = LocalDate.of(2026, 5, 1);
        LocalDate cancelledOnSeventhDay = LocalDate.of(2026, 5, 7);
        assertEquals(0, policy.chargeAmount(registered, cancelledOnSeventhDay));
    }

    @Test
    void freeWhenCancelledDuringTrialCheckedAfterTrialEnded() {
        LocalDate registered = LocalDate.of(2026, 5, 1);
        LocalDate cancelledOnThirdDay = LocalDate.of(2026, 5, 3);
        assertEquals(0, policy.chargeAmount(registered, cancelledOnThirdDay));
    }

    @Test
    void chargeWhenCancelledOnEighthDayAfterTrialEnded() {
        LocalDate registered = LocalDate.of(2026, 5, 1);
        LocalDate cancelledTooLate = LocalDate.of(2026, 5, 8);
        assertEquals(199, policy.chargeAmount(registered, cancelledTooLate));
    }

    @Test
    void rejectWhenCancelledBeforeRegistered() {
        LocalDate registered = LocalDate.of(2026, 5, 5);
        LocalDate cancelledBefore = LocalDate.of(2026, 5, 4);
        assertThrows(IllegalArgumentException.class, () -> policy.chargeAmount(registered, cancelledBefore));
    }

    @Test
    void rejectWhenRegisteredDateIsNull() {
        assertThrows(IllegalArgumentException.class, () -> policy.chargeAmount(null, null));
    }

    @Test
    void rejectWhenRegisteredDateInFuture() {
        LocalDate tomorrow = LocalDate.of(2026, 5, 11);
        assertThrows(IllegalArgumentException.class, () -> policy.chargeAmount(tomorrow, null));
    }
}
