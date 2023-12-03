package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.blank.projectmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#save(User)}
     */
    @Test
    void testSave() {
        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        User user = new User();
        user.setActive(true);
        user.setDefaultPassword(true);
        user.setDepartment(department);
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRole(Role.PMO);
        user.setUserRole("User Role");
        user.setUsername("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        Department department2 = new Department();
        department2.setActive(true);
        department2.setId(1);
        department2.setName("Name");

        User user2 = new User();
        user2.setActive(true);
        user2.setDefaultPassword(true);
        user2.setDepartment(department2);
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setRole(Role.PMO);
        user2.setUserRole("User Role");
        user2.setUsername("janedoe");
        User actualSaveResult = userServiceImpl.save(user2);
        verify(userRepository).save(Mockito.<User>any());
        assertSame(user, actualSaveResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#save(User)}
     */
    @Test
    void testSave2() {
        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        User user = new User();
        user.setActive(true);
        user.setDefaultPassword(true);
        user.setDepartment(department);
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRole(Role.PMO);
        user.setUserRole("User Role");
        user.setUsername("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        Department department2 = new Department();
        department2.setActive(true);
        department2.setId(1);
        department2.setName("Name");
        User user2 = mock(User.class);
        when(user2.getPassword()).thenReturn("iloveyou");
        doNothing().when(user2).setActive(anyBoolean());
        doNothing().when(user2).setDefaultPassword(anyBoolean());
        doNothing().when(user2).setDepartment(Mockito.<Department>any());
        doNothing().when(user2).setEmail(Mockito.<String>any());
        doNothing().when(user2).setId(Mockito.<Long>any());
        doNothing().when(user2).setName(Mockito.<String>any());
        doNothing().when(user2).setPassword(Mockito.<String>any());
        doNothing().when(user2).setPhone(Mockito.<String>any());
        doNothing().when(user2).setRole(Mockito.<Role>any());
        doNothing().when(user2).setUserRole(Mockito.<String>any());
        doNothing().when(user2).setUsername(Mockito.<String>any());
        user2.setActive(true);
        user2.setDefaultPassword(true);
        user2.setDepartment(department2);
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setRole(Role.PMO);
        user2.setUserRole("User Role");
        user2.setUsername("janedoe");
        User actualSaveResult = userServiceImpl.save(user2);
        verify(user2).getPassword();
        verify(user2).setActive(anyBoolean());
        verify(user2).setDefaultPassword(anyBoolean());
        verify(user2).setDepartment(Mockito.<Department>any());
        verify(user2).setEmail(Mockito.<String>any());
        verify(user2).setId(Mockito.<Long>any());
        verify(user2).setName(Mockito.<String>any());
        verify(user2).setPassword(Mockito.<String>any());
        verify(user2).setPhone(Mockito.<String>any());
        verify(user2).setRole(Mockito.<Role>any());
        verify(user2).setUserRole(Mockito.<String>any());
        verify(user2).setUsername(Mockito.<String>any());
        verify(userRepository).save(Mockito.<User>any());
        assertSame(user, actualSaveResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#save(User)}
     */
    @Test
    void testSave3() {
        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        User user = new User();
        user.setActive(true);
        user.setDefaultPassword(true);
        user.setDepartment(department);
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRole(Role.PMO);
        user.setUserRole("User Role");
        user.setUsername("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        Department department2 = new Department();
        department2.setActive(true);
        department2.setId(1);
        department2.setName("Name");
        User user2 = mock(User.class);
        when(user2.getPassword()).thenReturn(null);
        doNothing().when(user2).setActive(anyBoolean());
        doNothing().when(user2).setDefaultPassword(anyBoolean());
        doNothing().when(user2).setDepartment(Mockito.<Department>any());
        doNothing().when(user2).setEmail(Mockito.<String>any());
        doNothing().when(user2).setId(Mockito.<Long>any());
        doNothing().when(user2).setName(Mockito.<String>any());
        doNothing().when(user2).setPassword(Mockito.<String>any());
        doNothing().when(user2).setPhone(Mockito.<String>any());
        doNothing().when(user2).setRole(Mockito.<Role>any());
        doNothing().when(user2).setUserRole(Mockito.<String>any());
        doNothing().when(user2).setUsername(Mockito.<String>any());
        user2.setActive(true);
        user2.setDefaultPassword(true);
        user2.setDepartment(department2);
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setRole(Role.PMO);
        user2.setUserRole("User Role");
        user2.setUsername("janedoe");
        User actualSaveResult = userServiceImpl.save(user2);
        verify(user2).getPassword();
        verify(user2).setActive(anyBoolean());
        verify(user2).setDefaultPassword(anyBoolean());
        verify(user2).setDepartment(Mockito.<Department>any());
        verify(user2).setEmail(Mockito.<String>any());
        verify(user2).setId(Mockito.<Long>any());
        verify(user2).setName(Mockito.<String>any());
        verify(user2, atLeast(1)).setPassword(Mockito.<String>any());
        verify(user2).setPhone(Mockito.<String>any());
        verify(user2).setRole(Mockito.<Role>any());
        verify(user2).setUserRole(Mockito.<String>any());
        verify(user2).setUsername(Mockito.<String>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertSame(user, actualSaveResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#saveDepartment(Department)}
     */
    @Test
    void testSaveDepartment() {
        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");
        when(departmentRepository.save(Mockito.<Department>any())).thenReturn(department);

        Department department2 = new Department();
        department2.setActive(true);
        department2.setId(1);
        department2.setName("Name");
        userServiceImpl.saveDepartment(department2);
        verify(departmentRepository).save(Mockito.<Department>any());
        assertEquals("Name", department2.getName());
        assertEquals(1, department2.getId().intValue());
        assertTrue(department2.isActive());
    }

    /**
     * Method under test:  {@link UserServiceImpl#saveDepartment(Department)}
     */
    @Test
    void testSaveDepartment2() {
        when(departmentRepository.save(Mockito.<Department>any())).thenThrow(new RuntimeException("foo"));

        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");
        assertThrows(RuntimeException.class, () -> userServiceImpl.saveDepartment(department));
        verify(departmentRepository).save(Mockito.<Department>any());
    }



    /**
     * Method under test: {@link UserServiceImpl#getUserById(Long)}
     */
    @Test
    void testGetUserById() {
        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        User user = new User();
        user.setActive(true);
        user.setDefaultPassword(true);
        user.setDepartment(department);
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRole(Role.PMO);
        user.setUserRole("User Role");
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        User actualUserById = userServiceImpl.getUserById(1L);
        verify(userRepository).findById(Mockito.<Long>any());
        assertSame(user, actualUserById);
    }

    /**
     * Method under test:  {@link UserServiceImpl#getUserById(Long)}
     */
    @Test
    void testGetUserById2() {
        when(userRepository.findById(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));
        assertThrows(RuntimeException.class, () -> userServiceImpl.getUserById(1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }


    /**
     * Method under test: {@link UserServiceImpl#getEmail(String)}
     */
    @Test
    void testGetEmail() {
        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        User user = new User();
        user.setActive(true);
        user.setDefaultPassword(true);
        user.setDepartment(department);
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRole(Role.PMO);
        user.setUserRole("User Role");
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        Optional<User> actualEmail = userServiceImpl.getEmail("jane.doe@example.org");
        verify(userRepository).findByEmail(Mockito.<String>any());
        assertTrue(actualEmail.isPresent());
        assertSame(ofResult, actualEmail);
    }

    /**
     * Method under test:  {@link UserServiceImpl#getEmail(String)}
     */
    @Test
    void testGetEmail2() {
        when(userRepository.findByEmail(Mockito.<String>any())).thenThrow(new RuntimeException("foo"));
        assertThrows(RuntimeException.class, () -> userServiceImpl.getEmail("jane.doe@example.org"));
        verify(userRepository).findByEmail(Mockito.<String>any());
    }
}
