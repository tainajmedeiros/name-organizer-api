package com.NameOrganizerAPI.service;

import com.NameOrganizerAPI.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userServiceTest;

    private User createUser(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

    @Test
    public void saveUser() {
        Boolean userSave = userServiceTest.add(createUser("Taina"));
        assertThat(userSave).isTrue();
    }

    @Test
    public void dontSaveUserWhenUserExistInDatabase() {
        Boolean userSave = userServiceTest.add(createUser("Taina"));
        Boolean userSaveAgain = userServiceTest.add(createUser("Taina"));

        List<User> findAll = userServiceTest.list();

        assertThat(userSave).isTrue();
        assertThat(userSaveAgain).isFalse();
    }

    @Test
    public void listUsers(){
        userServiceTest.add(createUser("Taina"));
        userServiceTest.add(createUser("Brenda"));
        userServiceTest.add(createUser("Kae"));

        List<User> findAll = userServiceTest.list();

        assertThat(findAll.get(0).getName()).isEqualTo("Taina");
        assertThat(findAll.get(1).getName()).isEqualTo("Brenda");
        assertThat(findAll.get(2).getName()).isEqualTo("Kae");
    }

    @Test
    public void listUsersWhenListIsEmpty(){
        List<User> findAll = userServiceTest.list();
        assertThat(findAll).isEmpty();
    }

    @Test
    public void findUserWhenUserExist(){
        userServiceTest.add(createUser("Taina"));
        Optional<User> findUser = userServiceTest.findByName("Taina");

        assertThat(findUser.get().getName()).isEqualTo("Taina");
    }

    @Test
    public void findUserWhenUserDontExist(){
        Optional<User> findUser = userServiceTest.findByName("Taina");

        assertThat(findUser).isEmpty();
    }
}
