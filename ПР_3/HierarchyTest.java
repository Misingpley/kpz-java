package ua.duikt.holoshchuk.pr3;

import org.junit.jupiter.api.Test;
import ua.duikt.holoshchuk.pr3.model.Developer;
import ua.duikt.holoshchuk.pr3.model.Employee;
import ua.duikt.holoshchuk.pr3.model.Manager;

import static org.junit.jupiter.api.Assertions.*;

class HierarchyTest {

    @Test
    void shouldDispatchMethodDynamicallyAndBindFieldStatically() {
        Developer dev = new Developer("Тест", 40000.0, "Java");
        Employee baseRef = dev;

        assertEquals("Працівник (базове поле Employee)", baseRef.label);
        assertEquals("Розробник (похідне поле Developer)", dev.label);
        assertTrue(baseRef.describe().contains("Посада: Розробник (стек: Java)"));
    }

    @Test
    void shouldIncludeSuperDescriptionInManager() {
        Employee mgr = new Manager("Олена", 50000.0, 5);
        assertTrue(mgr.describe().contains("Працівник: Олена"));
        assertTrue(mgr.describe().contains("Посада: Менеджер (команда: 5 осіб)"));
    }
}
