package pl.adamczerwinski097.example.i18n.utils.common;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Locale;

public class BeanValidationUtils {
    public static Validator getValidator(Locale locale) {
        final HibernateValidatorFactory hibernateValidatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .buildValidatorFactory()
                .unwrap(HibernateValidatorFactory.class);

        return hibernateValidatorFactory.usingContext().constraintValidatorPayload(locale.getLanguage()).getValidator();
    }
}
