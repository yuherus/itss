import controller.UserController;
import model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserControllerBlackBoxTest {

    UserController userController = new UserController();

    @Test
    public void testGetAllUsers() throws SQLException {
        List<User> users = userController.getAll();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    public void testGetUserById() throws SQLException {
        User user = userController.getById(1);
        assertNotNull(user);
        assertEquals(1, user.getUserId());
    }

    @Test
    public void testAddUser() throws SQLException {
        User user = new User("new_user", "password", "New User", "new_user@example.com", User.UserType.TOURIST);
        userController.add(user);
        user.setUserId(userController.getAll().size());
        User retrievedUser = userController.getById(user.getUserId());
        assertNotNull(retrievedUser);
        assertEquals("new_user", retrievedUser.getUsername());
    }

    @Test
    public void testUpdateUser() throws SQLException {
        User user = userController.getById(1);
        user.setName("Updated Name");
        userController.update(user);
        User updatedUser = userController.getById(1);
        assertEquals("Updated Name", updatedUser.getName());
    }

    @Test
    public void testDeleteUser() throws SQLException {
        userController.delete(2);
        User user = userController.getById(2);
        assertNull(user.getName());
    }

}

