package uno.dos.tres.service.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;
import uno.dos.tres.bean.RegInfo;
import uno.dos.tres.service.validator.ValidatorException;

public class ValidatorImpl implements Validator {
    private final EmailValidator emailValidator = EmailValidator.getInstance();
    // Регулярное выражение для логина: буквы и цифры, длина от 2 до 15 символов
    private final RegexValidator usernameValidator = new RegexValidator("^[a-zA-Z0-9]{2,15}$");
    // Регулярное выражение для пароля: минимум 8 символов, должна содержать цифру, заглавную и строчную букву, спецсимвол
    private final RegexValidator passwordValidator = new RegexValidator("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");

    @Override
    public void checkRegInfo(RegInfo regInfo) throws ValidatorException {
        String userName = regInfo.getUserName();
        String password = regInfo.getPassword();
        String email = regInfo.getEmail();
            if (!usernameValidator.isValid(userName)){
                throw new ValidatorException("Invalid username. Come up with a username from 2 to 18 characters");
            } else if (!emailValidator.isValid(email)){
                throw new ValidatorException("Invalid email");
            } else if (!passwordValidator.isValid(password)){
                throw new ValidatorException("Invalid password. Please use a password of at least 8 characters, as well as numbers, uppercase and lowercase letters");
            }
    }
}
