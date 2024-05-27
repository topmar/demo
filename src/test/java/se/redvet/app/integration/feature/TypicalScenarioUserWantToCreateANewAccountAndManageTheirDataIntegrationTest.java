package se.redvet.app.integration.feature;

import com.auth0.jwt.JWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import se.redvet.app.domain.user.UserFacade;
import se.redvet.app.domain.user.dto.UserDto;
import se.redvet.app.infrastructure.security.dto.ResponseSignIn;
import se.redvet.app.integration.BaseIntegrationTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioUserWantToCreateANewAccountAndManageTheirDataIntegrationTest extends BaseIntegrationTest {
    @Autowired
    UserFacade userFacade;

    @Test
    public void user_want_to_sign_up_verify_email_sign_in_get_data_update_data_remove_account() throws Exception {
        //step 1: User wants to create a new account by executing a POST request to the /api/auth/signup endpoint
        //given
        //when
        ResultActions signUp = mockMvc.perform(post("/api/auth/signup")
                .content("""
                        {
                        "username": "username",
                        "password": "password",
                        "firstName": "firstName",
                        "lastName": "lastName",
                        "email": "info@redvet.se"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult signUpResult = signUp.andExpect(status().isCreated()).andReturn();
        String userDtoJson = signUpResult.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(userDtoJson, UserDto.class);
        assertAll("UserDto mismatch",
                () -> assertEquals(1, userDto.id(), "User ID should be 1"),
                () -> Assertions.assertEquals("username", userDto.username()),
                () -> Assertions.assertEquals("info@redvet.se", userDto.email()),
                () -> Assertions.assertEquals("firstName", userDto.firstName())
        );

        //step 2: User wants to verify email by executing a POST request with token to the /api/valid endpoint (greenmail not working now :( )
        userFacade.enableUser("username");
        //step 3: User wants to retrieve the JWT token by executing a query on the /api/auth/signin endpoint
        ResultActions signIn = mockMvc.perform(post("/api/auth/signin")
                .content("""
                        {
                        "username": "username",
                        "password": "password"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
        );
        MvcResult signInResult = signIn.andExpect(status().isOk()).andReturn();
        String jwtToken = signInResult.getResponse().getContentAsString();
        ResponseSignIn jwtResponse = objectMapper.readValue(jwtToken, ResponseSignIn.class);
        Pattern pattern = Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$");
        Matcher matcher = pattern.matcher(jwtResponse.token());
        assertAll(
                () -> assertTrue(matcher.matches(), "token format is valid"),
                () -> assertEquals("\"username\"", JWT.decode(jwtResponse.token()).getClaim("sub").toString())
        );

        //step 4: User wants to get user data by executing a POST request with token to the /api/users/user endpoint
        ResultActions getUserData = mockMvc.perform(get("/api/users/user")
                .header("Authorization", "Bearer "+ jwtResponse.token())
                .contentType(MediaType.APPLICATION_JSON)
        );
        MvcResult mvcResult2 = getUserData.andExpect(status().isOk()).andReturn();
        String jsonWithUserData = mvcResult2.getResponse().getContentAsString();
        assertAll("UserDto mismatch",
                () -> assertEquals(1, userDto.id(), "User ID should be 1"),
                () -> Assertions.assertEquals("username", userDto.username()),
                () -> Assertions.assertEquals("info@redvet.se", userDto.email()),
                () -> Assertions.assertEquals("firstName", userDto.firstName())
        );

        //step 5: User wants to update user data by executing a PUT request with token to the /api/users/user endpoint
        ResultActions putUserData = mockMvc.perform(put("/api/users/user")
                .header("Authorization", "Bearer "+ jwtResponse.token())
                        .content("""
                        {
                        "username": "username2",
                        "password": "password",
                        "firstName": "firstName2",
                        "lastName": "lastName",
                        "email": "info@redvet.se"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
        );
        MvcResult mvcResult3 = putUserData.andExpect(status().isOk()).andReturn();
        String jsonWithUserUpdatedData = mvcResult3.getResponse().getContentAsString();
        UserDto userUpdatedDto = objectMapper.readValue(jsonWithUserUpdatedData, UserDto.class);
        assertAll("UserDto mismatch",
                () -> assertEquals(1, userUpdatedDto.id(), "User ID should be 1"),
                () -> Assertions.assertEquals("username2", userUpdatedDto.username()),
                () -> Assertions.assertEquals("info@redvet.se", userUpdatedDto.email()),
                () -> Assertions.assertEquals("firstName2", userUpdatedDto.firstName())
        );

        //step 6: User wants to remove account by executing a DELETE request to the /api/users/user endpoint
        ResultActions deleteUser = mockMvc.perform(delete("/api/users/user")
                .header("Authorization", "Bearer "+ jwtResponse.token())
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcResult4 = deleteUser.andExpect(status().isNoContent()).andReturn();
        //step 7: another attempt to sign in ends in failure and the status of forbidden is returned
        ResultActions signIn2 = mockMvc.perform(post("/api/auth/signin")
                .content("""
                        {
                        "username": "username",
                        "password": "password"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
        );
        MvcResult signInResult2 = signIn2.andExpect(status().isForbidden()).andReturn();
    }
}
