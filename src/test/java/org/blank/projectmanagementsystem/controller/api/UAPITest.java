package org.blank.projectmanagementsystem.controller.api;

import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.formInput.UpdateUserFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserAPI.class})
@ExtendWith(SpringExtension.class)
class UAPITest {
  @MockBean
  private DepartmentService departmentService;

  @MockBean
  private SessionRegistry sessionRegistry;

  @Autowired
  private UserAPI userAPI;

  @MockBean
  private UserService userService;
  /**
  * Method under test: {@link UserAPI#addUser(AddUserFormInput)}
  */
  @Test
  void testAddUser() throws Exception {
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
    when(userService.createMember(Mockito.<AddUserFormInput>any(), Mockito.<String>any())).thenReturn(user);

    AddUserFormInput addUserFormInput = new AddUserFormInput();
    addUserFormInput.setDepartment(1L);
    addUserFormInput.setEmail("jane.doe@example.org");
    addUserFormInput.setName("Name");
    addUserFormInput.setRole("Role");
    String content = (new ObjectMapper()).writeValueAsString(addUserFormInput);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/member-create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string("<UserViewObject><id>1</id><name>Name</name><email>jane.doe@example.org</email><role>PMO</role>"
                + "<departmentId>1</departmentId><department>Name</department><active>true</active></UserViewObject>"));
  }

  /**
   * Method under test: {@link UserAPI#changePassword(ChangePasswordFormInput)}
   */
  @Test
  void testChangePassword() throws Exception {
    ChangePasswordFormInput buildResult = ChangePasswordFormInput.builder()
        .confirmPassword("iloveyou")
        .currentPassword("iloveyou")
        .newPassword("iloveyou")
        .build();
    when(userService.changePassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(buildResult);

    ChangePasswordFormInput changePasswordFormInput = new ChangePasswordFormInput();
    changePasswordFormInput.setConfirmPassword("iloveyou");
    changePasswordFormInput.setCurrentPassword("iloveyou");
    changePasswordFormInput.setNewPassword("iloveyou");
    String content = (new ObjectMapper()).writeValueAsString(changePasswordFormInput);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/change-password")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "<ChangePasswordFormInput><currentPassword>iloveyou</currentPassword><newPassword>iloveyou</newPassword"
                    + "><confirmPassword>iloveyou</confirmPassword></ChangePasswordFormInput>"));
  }

  /**
   * Method under test:  {@link UserAPI#checkCurrentPassword(ChangePasswordFormInput)}
   */
  @Test
  void testCheckCurrentPassword() throws Exception {
    when(userService.checkCurrentPassword(Mockito.<String>any())).thenReturn(true);

    ChangePasswordFormInput changePasswordFormInput = new ChangePasswordFormInput();
    changePasswordFormInput.setConfirmPassword("iloveyou");
    changePasswordFormInput.setCurrentPassword("iloveyou");
    changePasswordFormInput.setNewPassword("iloveyou");
    String content = (new ObjectMapper()).writeValueAsString(changePasswordFormInput);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/check-current-password")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content().string("<Boolean>true</Boolean>"));
  }

  /**
   * Method under test: {@link UserAPI#addUser(AddUserFormInput)}
   */
  @Test
  void testAddUser2() throws Exception {
    Department department = new Department();
    department.setActive(true);
    department.setId(1);
    department.setName("Name");

    User user = new User();
    user.setActive(false);
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
    when(userService.createMember(Mockito.<AddUserFormInput>any(), Mockito.<String>any())).thenReturn(user);

    AddUserFormInput addUserFormInput = new AddUserFormInput();
    addUserFormInput.setDepartment(1L);
    addUserFormInput.setEmail("jane.doe@example.org");
    addUserFormInput.setName("Name");
    addUserFormInput.setRole("Role");
    String content = (new ObjectMapper()).writeValueAsString(addUserFormInput);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/member-create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string("<UserViewObject><id>1</id><name>Name</name><email>jane.doe@example.org</email><role>PMO</role>"
                + "<departmentId>1</departmentId><department>Name</department><active>false</active></UserViewObject>"));
  }

  /**
   * Method under test:  {@link UserAPI#checkUserByUsernameOrEmail(String)}
   */
  @Test
  void testCheckUserByUsernameOrEmail() throws Exception {
    when(userService.checkUserExistOrNotWithUsername(Mockito.<String>any())).thenReturn(true);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/check-user-by-username")
        .param("data", "foo");
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content().string("<Boolean>true</Boolean>"));
  }

  /**
   * Method under test:  {@link UserAPI#getActiveUsers()}
   */
  @Test
  void testGetActiveUsers() throws Exception {
    when(userService.getAllUsers()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/get-active-user");
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content().string("<List/>"));
  }

  /**
   * Method under test: {@link UserAPI#getActiveUsers()}
   */
  @Test
  void testGetActiveUsers2() throws Exception {
    ArrayList<UserViewObject> userViewObjectList = new ArrayList<>();
    UserViewObject buildResult = UserViewObject.builder()
        .active(true)
        .department("Department")
        .departmentId(1)
        .email("jane.doe@example.org")
        .id(1L)
        .name("Name")
        .role("Role")
        .build();
    userViewObjectList.add(buildResult);
    when(userService.getAllUsers()).thenReturn(userViewObjectList);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/get-active-user");
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "<List><item><id>1</id><name>Name</name><email>jane.doe@example.org</email><role>Role</role><departmentId"
                    + ">1</departmentId><department>Department</department><active>true</active></item></List>"));
  }

  /**
   * Method under test:  {@link UserAPI#getActiveUsers()}
   */
  @Test
  void testGetActiveUsers3() throws Exception {
    when(userService.getAllUsers()).thenReturn(new ArrayList<>());
    SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userAPI).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /**
   * Method under test: {@link UserAPI#getActiveUsers()}
   */
  @Test
  void testGetActiveUsers4() throws Exception {
    ArrayList<UserViewObject> userViewObjectList = new ArrayList<>();
    UserViewObject buildResult = UserViewObject.builder()
        .active(true)
        .department("Department")
        .departmentId(1)
        .email("jane.doe@example.org")
        .id(1L)
        .name("Name")
        .role("Role")
        .build();
    userViewObjectList.add(buildResult);
    UserViewObject buildResult2 = UserViewObject.builder()
        .active(false)
        .department("Department")
        .departmentId(1)
        .email("jane.doe@example.org")
        .id(1L)
        .name("Name")
        .role("Role")
        .build();
    userViewObjectList.add(buildResult2);
    when(userService.getAllUsers()).thenReturn(userViewObjectList);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/get-active-user");
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "<List><item><id>1</id><name>Name</name><email>jane.doe@example.org</email><role>Role</role><departmentId"
                    + ">1</departmentId><department>Department</department><active>true</active></item></List>"));
  }

  /**
   * Method under test:  {@link UserAPI#getDepartment()}
   */
  @Test
  void testGetDepartment() throws Exception {
    when(departmentService.getAllDepartments()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/get-department");
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content().string("<List/>"));
  }

  /**
   * Method under test:  {@link UserAPI#getUserList()}
   */
  @Test
  void testGetUserList() throws Exception {
    when(userService.getAllUsers()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/get-users");
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content().string("<List/>"));
  }

  /**
   * Method under test: {@link UserAPI#getUserList()}
   */
  @Test
  void testGetUserList2() throws Exception {
    ArrayList<UserViewObject> userViewObjectList = new ArrayList<>();
    UserViewObject buildResult = UserViewObject.builder()
        .active(true)
        .department("Department")
        .departmentId(1)
        .email("jane.doe@example.org")
        .id(1L)
        .name("Name")
        .role("Role")
        .build();
    userViewObjectList.add(buildResult);
    when(userService.getAllUsers()).thenReturn(userViewObjectList);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/get-users");
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "<List><item><id>1</id><name>Name</name><email>jane.doe@example.org</email><role>Role</role><departmentId"
                    + ">1</departmentId><department>Department</department><active>true</active></item></List>"));
  }

  /**
   * Method under test: {@link UserAPI#getUserList()}
   */
  @Test
  void testGetUserList3() throws Exception {
    ArrayList<UserViewObject> userViewObjectList = new ArrayList<>();
    UserViewObject buildResult = UserViewObject.builder()
        .active(true)
        .department("Department")
        .departmentId(1)
        .email("jane.doe@example.org")
        .id(1L)
        .name("Name")
        .role("Role")
        .build();
    userViewObjectList.add(buildResult);
    UserViewObject buildResult2 = UserViewObject.builder()
        .active(false)
        .department("Department")
        .departmentId(1)
        .email("jane.doe@example.org")
        .id(1L)
        .name("Name")
        .role("Role")
        .build();
    userViewObjectList.add(buildResult2);
    when(userService.getAllUsers()).thenReturn(userViewObjectList);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/get-users");
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "<List><item><id>1</id><name>Name</name><email>jane.doe@example.org</email><role>Role</role><departmentId"
                    + ">1</departmentId><department>Department</department><active>true</active></item><item><id>1</id><name"
                    + ">Name</name><email>jane.doe@example.org</email><role>Role</role><departmentId>1</departmentId><department"
                    + ">Department</department><active>false</active></item></List>"));
  }

  /**
   * Method under test: {@link UserAPI#updateMemberStatus(Long, boolean)}
   */
  @Test
  void testUpdateMemberStatus() throws Exception {
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
    when(userService.save(Mockito.<User>any())).thenReturn(user2);
    when(userService.getUserById(Mockito.<Long>any())).thenReturn(user);
    MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/member/status/{id}", 1L);
    MockHttpServletRequestBuilder requestBuilder = putResult.param("newStatus", String.valueOf(true));
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "<User><id>1</id><name>Name</name><username>janedoe</username><email>jane.doe@example.org</email><role"
                    + ">PMO</role><department><id>1</id><name>Name</name><active>true</active></department><photoData/>"
                    + "<userRole>User Role</userRole><phone>6625550144</phone><active>true</active><defaultPassword>true<"
                    + "/defaultPassword><enabled>true</enabled><authorities><authorities><authority>PMO</authority></authorities"
                    + "></authorities><credentialsNonExpired>true</credentialsNonExpired><photoDataAsString/><accountNonExpired"
                    + ">true</accountNonExpired><accountNonLocked>true</accountNonLocked></User>"));
  }

  /**
   * Method under test: {@link UserAPI#updateMemberStatus(Long, boolean)}
   */
  @Test
  void testUpdateMemberStatus2() throws Exception {
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

    Department department2 = new Department();
    department2.setActive(true);
    department2.setId(1);
    department2.setName("Name");

    User user2 = new User();
    user2.setActive(false);
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
    when(userService.save(Mockito.<User>any())).thenReturn(user2);
    when(userService.getUserById(Mockito.<Long>any())).thenReturn(user);
    when(sessionRegistry.getAllPrincipals()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/member/status/{id}", 1L);
    MockHttpServletRequestBuilder requestBuilder = putResult.param("newStatus", String.valueOf(true));
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "<User><id>1</id><name>Name</name><username>janedoe</username><email>jane.doe@example.org</email><role"
                    + ">PMO</role><department><id>1</id><name>Name</name><active>true</active></department><photoData/>"
                    + "<userRole>User Role</userRole><phone>6625550144</phone><active>false</active><defaultPassword>true<"
                    + "/defaultPassword><enabled>false</enabled><authorities><authorities><authority>PMO</authority></authorities"
                    + "></authorities><credentialsNonExpired>true</credentialsNonExpired><photoDataAsString/><accountNonExpired"
                    + ">true</accountNonExpired><accountNonLocked>true</accountNonLocked></User>"));
  }

  /**
   * Method under test: {@link UserAPI#updateMemberStatus(Long, boolean)}
   */
  @Test
  void testUpdateMemberStatus3() throws Exception {
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

    Department department2 = new Department();
    department2.setActive(true);
    department2.setId(1);
    department2.setName("Name");

    User user2 = new User();
    user2.setActive(false);
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
    when(userService.save(Mockito.<User>any())).thenReturn(user2);
    when(userService.getUserById(Mockito.<Long>any())).thenReturn(user);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("42");
    when(sessionRegistry.getAllPrincipals()).thenReturn(objectList);
    MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/member/status/{id}", 1L);
    MockHttpServletRequestBuilder requestBuilder = putResult.param("newStatus", String.valueOf(true));
    MockMvcBuilders.standaloneSetup(userAPI)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "<User><id>1</id><name>Name</name><username>janedoe</username><email>jane.doe@example.org</email><role"
                    + ">PMO</role><department><id>1</id><name>Name</name><active>true</active></department><photoData/>"
                    + "<userRole>User Role</userRole><phone>6625550144</phone><active>false</active><defaultPassword>true<"
                    + "/defaultPassword><enabled>false</enabled><authorities><authorities><authority>PMO</authority></authorities"
                    + "></authorities><credentialsNonExpired>true</credentialsNonExpired><photoDataAsString/><accountNonExpired"
                    + ">true</accountNonExpired><accountNonLocked>true</accountNonLocked></User>"));
  }
  @Test
  void testUpdateUser() throws Exception {
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
    when(userService.getUserById(Mockito.<Long>any())).thenReturn(user);

    Department department2 = new Department();
    department2.setActive(true);
    department2.setId(1);
    department2.setName("Name");
    when(departmentService.getDepartmentById(Mockito.<Integer>any())).thenReturn(department2);
    MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/user-edit/{id}", 1L);
    putResult.characterEncoding("https://example.org/example");

    UpdateUserFormInput updateUserFormInput = new UpdateUserFormInput();
    updateUserFormInput.setDepartment(1);
    updateUserFormInput.setEmail("jane.doe@example.org");
    updateUserFormInput.setId(1L);
    updateUserFormInput.setName("Name");
    updateUserFormInput.setRole("Role");
    String content = (new ObjectMapper()).writeValueAsString(updateUserFormInput);
    MockHttpServletRequestBuilder requestBuilder = putResult.contentType(MediaType.APPLICATION_JSON).content(content);
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userAPI).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
  }
}
