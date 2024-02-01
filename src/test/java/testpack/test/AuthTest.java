package testpack.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static testpack.data.DataGenerator.Registration.getRegisteredUser;
import static testpack.data.DataGenerator.Registration.getUser;
import static testpack.data.DataGenerator.getRandomLogin;
import static testpack.data.DataGenerator.getRandomPassword;

class AuthTest {
    @BeforeEach

    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfullyLoginWithActiveRegisteredUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        // $("h2").shouldHave(Condition.text("Личный кабинет")).shouldBe(Condition.visible);
        $(byText("Личный кабинет"));
    }


    @Test
    @DisplayName("Message")
    void shouldUserNotRegistered() {
        var notRegisterUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisterUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisterUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(byText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Message")
    void invalidLogin() {
        var registeredUser = getRegisteredUser("active");
        var invloginUser = getRandomLogin();
        $("[data-test-id='login'] input").setValue(invloginUser);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(byText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Message")
    void invalidPassword() {
        var registeredUser = getRegisteredUser("active");
        var invPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(invPassword);
        $("[data-test-id='action-login']").click();
        $(byText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Message")
    void statusBlock() {
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(byText("Ошибка! Пользователь заблокирован"));
    }
}
