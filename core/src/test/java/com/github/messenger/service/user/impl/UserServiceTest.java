package com.github.messenger.service.user.impl;

import com.github.messenger.entity.User;
import com.github.messenger.exceptions.Conflict;
import com.github.messenger.exceptions.UpdateError;
import com.github.messenger.payload.Role;
import com.github.messenger.payload.Status;
import com.github.messenger.repository.IRepository;
import com.github.messenger.repository.impl.HibernateRepository;
import com.github.messenger.service.user.IUserService;
import com.github.messenger.utils.HibernateSessionManager;
import org.hibernate.TransientObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static final IRepository<User> repository = new HibernateRepository<>(User.class, new HibernateSessionManager());

    private static final IUserService service = new UserService(repository);

    private static final User mockUser = new User(
            null,
            "firstname",
            "lastname",
            "login",
            "password",
            "email",
            "nickname",
            "phone",
            Role.USER,
            Status.OFFLINE,
            0L
    );

    private static final User[] mockUserArray = {
            new User(null, "firstname_User1", "lastname_User1", "login_User1", "password_User1", "email_User1", "nickname_User1", "phone_User1", Role.USER, Status.OFFLINE, 0L),
            new User(null, "firstname_User2", "lastname_User2", "login_User2", "password_User2", "email_User2", "nickname_User2", "phone_User2", Role.USER, Status.OFFLINE, 0L),
            new User(null, "firstname_User3", "lastname_User3", "login_User3", "password_User3", "email_User3", "nickname_User3", "phone_User3", Role.USER, Status.OFFLINE, 0L),
            new User(null, "firstname_Admin1", "lastname_Admin1", "login_Admin1", "password_Admin1", "email_Admin1", "nickname_Admin1", "phone_Admin1", Role.ADMIN, Status.OFFLINE, 0L),
            new User(null, "firstname_Admin2", "lastname_Admin2", "login_Admin2", "password_Admin2", "email_Admin2", "nickname_Admin2", "phone_Admin2", Role.ADMIN, Status.OFFLINE, 0L),
            new User(null, "firstname_Admin3", "lastname_Admin3", "login_Admin3", "password_Admin3", "email_Admin3", "nickname_Admin3", "phone_Admin3", Role.ADMIN, Status.OFFLINE, 0L),
    };

    @Before
    public void setUp() {
        for (User user : mockUserArray) {
            repository.save(user);
        }
    }

    @After
    public void tearDown() {
        Collection<User> users = repository.findAll();
        for (User user : users) {
            repository.delete(user);
        }
    }


    @Test
    public void findAll() {
        Collection<User> exp = Arrays.asList(mockUserArray);
        Collection<User> act = service.findAll();
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllEmpty() {
        for (User user : mockUserArray) {
            repository.delete(user);
        }
        Collection<User> exp = new ArrayList<>();
        Collection<User> act = service.findAll();
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findById() {
        User exp = mockUserArray[0];
        User act = service.findById(mockUserArray[0].getId());
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findByIdNotPresented() {
        Assert.assertNull(service.findById(1L));
    }

    @Test
    public void findByIdNegative() {
        Assert.assertNull(service.findById(-1L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByIdNull() {
        Assert.assertNull(service.findById(null));
    }

    @Test
    public void findByLogin() {
        User exp = mockUserArray[2];
        User act = service.findByLogin("login_User3");
        Assert.assertEquals(exp, act);
    }

    @Test (expected = NoResultException.class)
    public void findByLoginNotPresented() {
        Assert.assertNull(service.findByLogin("not_presented"));
    }

    @Test
    public void findByNickname() {
        User exp = mockUserArray[3];
        User act = service.findByNickname("nickname_Admin1");
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findByEmail() {
        User exp = mockUserArray[4];
        User act = service.findByEmail("email_Admin2");
        Assert.assertEquals(exp, act);
    }

    @Test
    public void insert() {
        service.insert(mockUser);
        User act = service.findById(mockUser.getId());
        Assert.assertEquals(mockUser, act);
    }

    @Test(expected = Conflict.class)
    public void insertDuplicate() {
        service.insert(mockUserArray[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() {
        service.insert(null);
    }

    @Test
    public void update() {
        service.insert(mockUser);
        mockUser.setRole(Role.ADMIN);
        service.update(mockUser);
        User act = service.findById(mockUser.getId());
        Assert.assertEquals(mockUser, act);
        mockUser.setRole(Role.USER);
    }

    @Test (expected = UpdateError.class)
    public void updateNullId() {
        service.update(new User(
                null,
                "firstname_User1",
                "lastname_User1",
                "login_User1",
                "password_User1",
                "email_User1",
                "nickname_User1",
                "phone_User1",
                Role.USER,
                Status.OFFLINE,
                0L
        ));
    }

    @Test (expected = UpdateError.class)
    public void updateNotPresentedId() {
        service.update(new User(
                1L,
                "firstname_User1",
                "lastname_User1",
                "login_User1",
                "password_User1",
                "email_User1",
                "nickname_User1",
                "phone_User1",
                Role.USER,
                Status.OFFLINE,
                0L
        ));
    }

    @Test (expected = UpdateError.class)
    public void updateSameLogin() {
        User user = service.findByLogin("login_User1");
        user.setLogin("login_User2");
        service.update(user);
    }

    private boolean collectionsEqualsInAnyOrder(Collection<User> first, Collection<User> second) {
        return first.size() == second.size() && first.containsAll(second) && second.containsAll(first);
    }

}