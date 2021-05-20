package com.github.messenger.repository;

import com.github.messenger.payload.Role;
import com.github.messenger.entity.User;
import com.github.messenger.payload.Status;
import com.github.messenger.repository.impl.HibernateRepository;
import com.github.messenger.utils.HibernateSessionManager;
import org.hibernate.TransientObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.*;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.util.*;

public class HibernateRepositoryTest {

    private static final IRepository<User> repository = new HibernateRepository<>(User.class, new HibernateSessionManager());

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
        Collection<User> act = repository.findAll();
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllEmpty() {
        for (User user : mockUserArray) {
            repository.delete(user);
        }
        Collection<User> exp = new ArrayList<>();
        Collection<User> act = repository.findAll();
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findById() {
        User exp = mockUserArray[0];
        User act = repository.findById(mockUserArray[0].getId());
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findByIdNotPresented() {
        Assert.assertNull(repository.findById(1L));
    }

    @Test
    public void findByIdNegative() {
        Assert.assertNull(repository.findById(-1L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByIdNull() {
        Assert.assertNull(repository.findById(null));
    }

    @Test
    public void findAllByFirstname() {
        Collection<User> exp = new ArrayList<>();
        exp.add(mockUserArray[0]);
        Collection<User> act = repository.findAllBy("firstname", "firstname_User1");
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllByLastname() {
        Collection<User> exp = new ArrayList<>();
        exp.add(mockUserArray[1]);
        Collection<User> act = repository.findAllBy("lastname", "lastname_User2");
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllByFirstnameNotPresented() {
        Collection<User> exp = new ArrayList<>();
        Collection<User> act = repository.findAllBy("firstname", "not_presented");
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findByLogin() {
        User exp = mockUserArray[2];
        User act = repository.findBy("login", "login_User3");
        Assert.assertEquals(exp, act);
    }

    @Test (expected = NoResultException.class)
    public void findByLoginNotPresented() {
        Assert.assertNull(repository.findBy("login", "not_presented"));
    }

    @Test
    public void findByNickname() {
        User exp = mockUserArray[3];
        User act = repository.findBy("nickname", "nickname_Admin1");
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findByEmail() {
        User exp = mockUserArray[4];
        User act = repository.findBy("email", "email_Admin2");
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findByPhone() {
        User exp = mockUserArray[5];
        User act = repository.findBy("phone", "phone_Admin3");
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findAllByRoleUser() {
        Collection<User> exp = new ArrayList<>();
        exp.add(mockUserArray[0]);
        exp.add(mockUserArray[1]);
        exp.add(mockUserArray[2]);
        Collection<User> act = repository.findAllBy("role", Role.USER);
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllByRoleAdmin() {
        Collection<User> exp = new ArrayList<>();
        exp.add(mockUserArray[3]);
        exp.add(mockUserArray[4]);
        exp.add(mockUserArray[5]);
        Collection<User> act = repository.findAllBy("role", Role.ADMIN);
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllByWrongField() {
        Collection<User> act = repository.findAllBy("wrong", "wrong");
    }

    @Test
    public void save() {
        repository.save(mockUser);
        User exp = mockUser;
        User act = repository.findById(mockUser.getId());
        Assert.assertEquals(exp, act);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveDuplicate() {
        repository.save(mockUserArray[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() {
        repository.save(null);
    }

    @Test
    public void update() {
        repository.save(mockUser);
        mockUser.setRole(Role.ADMIN);
        User exp = mockUser;
        repository.update(mockUser);
        User act = repository.findById(mockUser.getId());
        Assert.assertEquals(exp, act);
    }

    @Test (expected = TransientObjectException.class)
    public void updateNullId() {
        repository.update(new User(
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

    @Test (expected = OptimisticLockException.class)
    public void updateNotPresentedId() {
        repository.update(new User(
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

    @Test (expected = PersistenceException.class)
    public void updateSameLogin() {
        User user = repository.findBy("login", "login_User1");
        user.setLogin("login_User2");
        repository.update(user);
    }

    @Test
    public void delete() {
        Collection<User> exp = new ArrayList<>(Arrays.asList(mockUserArray));
        repository.save(mockUser);
        repository.delete(mockUser);
        Collection<User> act = repository.findAll();
        Assert.assertEquals(exp, act);
    }

    @Test
    public void deleteNullId(){
        repository.delete(new User(
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
        Collection<User> exp = new ArrayList<>(Arrays.asList(mockUserArray));
        Collection<User> act = repository.findAll();
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test (expected = OptimisticLockException.class)
    public void deleteNotPresented(){
        repository.delete(new User(
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
        Collection<User> exp = new ArrayList<>(Arrays.asList(mockUserArray));
        Collection<User> act = repository.findAll();
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }


    private boolean collectionsEqualsInAnyOrder(Collection<User> first, Collection<User> second) {
        return first.size() == second.size() && first.containsAll(second) && second.containsAll(first);
    }

}