import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.soen387asg2pollsystem.model.User;

public class testUserManage {

    private User usr1 = new User();
    private User usr2 = new User(1, "Jason", "Zhang", "jasonzhang@gmail.com", "1234");

    @Test
    public void testGetUserId() {
        assertEquals(0, usr1.getUserId());
        assertEquals(1, usr2.getUserId());
    }

    @Test
    public void testSetUserId() {
        usr2.setUserId(10);
        assertEquals(10, usr2.getUserId());
        assertNotEquals(1, usr2.getUserId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals(null, usr1.getFirstName());
        assertEquals("Jason", usr2.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        usr2.setFirstName("Stanley");
        assertEquals("Stanley", usr2.getFirstName());
        assertNotEquals("Jason", usr2.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals(null, usr1.getLastName());
        assertEquals("Zhang", usr2.getLastName());
    }

    @Test
    public void testSetLastName() {
        usr2.setLastName("Biden");
        assertEquals("Biden", usr2.getLastName());
        assertNotEquals("Zhang", usr2.getFirstName());
    }

    @Test
    public void testGetEmail() {
        assertEquals(null, usr1.getEmail());
        assertEquals("jasonzhang@gmail.com", usr2.getEmail());
    }

    @Test
    public void testSetEmail() {
        usr2.setEmail("jasonzhang@hotmail.com");
        assertEquals("jasonzhang@hotmail.com", usr2.getEmail());
        assertNotEquals("jasonzhang@gmail.com", usr2.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals(null, usr1.getPassword());
        assertEquals("1234", usr2.getPassword());
    }

    @Test
    public void testSetPassword() {
        usr2.setPassword("4321");
        assertEquals("4321", usr2.getPassword());
        assertNotEquals("1234", usr2.getPassword());
    }
}
