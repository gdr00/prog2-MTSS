////////////////////////////////////////////////////////////////////
// Gabriele Da Re 2008071
// Claudio Giaretta 1225419
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertFalse; 
import static org.junit.Assert.assertTrue;
 
import java.time.LocalTime; 
import java.util.ArrayList; 
import java.util.List;

import org.junit.Before;
import org.junit.Test;
 
import it.unipd.mtss.business.exception.BillException; 
import it.unipd.mtss.model.EItem; 
import it.unipd.mtss.model.User; 
import it.unipd.mtss.model.EItem.ItemType;

public class BillCalculatorTest {

    List<EItem> listItems;
    User user;
    BillCalculator bill;

    @Before
    public void setup() {
        user = new User("pippo", 32);

        listItems = new ArrayList<EItem>();
        listItems.add(new EItem(ItemType.PROCESSOR, "Ryzen 9 5950x", 545.99));
        listItems.add(new EItem(ItemType.MOTHERBOARD, "asus rog z470", 200.12));
        listItems.add(new EItem(ItemType.MOUSE, "Glorius", 60.99));
        listItems.add(new EItem(ItemType.KEYBOARD, "Logitech", 150.99));

        bill = new BillCalculator();
    }

    @Test
    public void testGetOrderPrice() throws BillException {
        assertEquals(958.09, bill.getOrderPrice(listItems, user), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListNull() throws BillException {
        assertEquals(958.09, bill.getOrderPrice(null, user), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListEmpty() throws BillException {
        assertEquals(958.09, bill.getOrderPrice(new ArrayList<EItem>(), user), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNull() throws BillException {
        assertEquals(958.09, bill.getOrderPrice(listItems, null), 0.0001);
    }

    @Test
    public void testDiscountProcessori() throws BillException {
        listItems.add(new EItem(ItemType.PROCESSOR, "Intel i3 3580u", 100.10));
        listItems.add(new EItem(ItemType.PROCESSOR, "intel i7 7700k", 308.12));
        listItems.add(new EItem(ItemType.PROCESSOR, "ryzen 7 6600x", 430.23));
        listItems.add(new EItem(ItemType.PROCESSOR, "ryzen 7 5800x", 370.99));
        listItems.add(new EItem(ItemType.PROCESSOR, "intel i9 9900", 699.99));

        assertEquals(2817.469, bill.getOrderPrice(listItems, user), 0.01);
    }

    @Test
    public void testMouseOffer() throws BillException {
        listItems.add(new EItem(ItemType.MOUSE, "glorius", 10.10));
        listItems.add(new EItem(ItemType.MOUSE, "logitech", 38.12));
        listItems.add(new EItem(ItemType.MOUSE, "sbruders", 43.23));
        listItems.add(new EItem(ItemType.MOUSE, "razer", 37.99));
        listItems.add(new EItem(ItemType.MOUSE, "mignolino", 69.99));
        listItems.add(new EItem(ItemType.MOUSE, "matteo", 10.09));
        listItems.add(new EItem(ItemType.MOUSE, "asdpoisd", 38.12));
        listItems.add(new EItem(ItemType.MOUSE, "spdoadsj", 43.23));
        listItems.add(new EItem(ItemType.MOUSE, "dsaofdfo", 37.99));
        listItems.add(new EItem(ItemType.MOUSE, "asdcvi", 99.99));

        assertEquals(1386.93, bill.getOrderPrice(listItems, user), 0.01);
    }
}