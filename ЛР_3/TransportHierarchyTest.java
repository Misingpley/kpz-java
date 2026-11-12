package ua.duikt.holoshchuk.lr3;

import org.junit.jupiter.api.Test;
import ua.duikt.holoshchuk.lr3.app.Main;
import ua.duikt.holoshchuk.lr3.domain.*;

import static org.junit.jupiter.api.Assertions.*;

class TransportHierarchyTest {

    @Test
    void allSubtypesShouldRespectBaseContractOnValidAndInvalidDistances() {
        Transport[] fleet = {
                new Car("Toyota", 7.0, 50.0, 1.0),
                new Truck("MAN", 25.0, 50.0, 10.0, 2.0),
                new Motorcycle("Yamaha", 4.0, 50.0, 0.5),
                new ElectricVan("Nissan", 20.0, 7.0, 30.0)
        };

        for (Transport t : fleet) {
            assertTrue(t.tripCost(100.0) > 0.0);
            assertThrows(IllegalArgumentException.class, () -> t.tripCost(-10.0));
        }
        assertTrue(Main.printFleetTripReport(fleet, 100.0) > 0.0);
    }
}
