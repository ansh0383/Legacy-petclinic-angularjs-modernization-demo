package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 *
 * @author Michael Isvy
 * Simple test to make sure that Bean Validation is working
 * (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 *
 */
public class ValidatorTests {

	private Validator createValidator() {
	      LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
	      localValidatorFactoryBean.afterPropertiesSet();
	      return localValidatorFactoryBean;
	  }

	@Test
    public void shouldNotValidateWhenFirstNameEmpty() {

	LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("");
        person.setLastName("smith");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Person> violation =  constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
        assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }

    @Test
    public void shouldNotValidateWhenLastNameEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("lastName");
    }

    @Test
    public void shouldNotValidateOwnerWhenAddressEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Owner owner = createValidOwner();
        owner.setAddress("");

        Validator validator = createValidator();
        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("address");
    }

    @Test
    public void shouldNotValidateOwnerWhenCityEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Owner owner = createValidOwner();
        owner.setCity("");

        Validator validator = createValidator();
        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("city");
    }

    @Test
    public void shouldNotValidateOwnerWhenTelephoneEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Owner owner = createValidOwner();
        owner.setTelephone("");

        Validator validator = createValidator();
        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertThat(constraintViolations).isNotEmpty();
        assertThat(constraintViolations).anyMatch(v -> v.getPropertyPath().toString().equals("telephone"));
    }

    @Test
    public void shouldNotValidateOwnerWhenTelephoneHasNonDigits() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Owner owner = createValidOwner();
        owner.setTelephone("abc");

        Validator validator = createValidator();
        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertThat(constraintViolations).isNotEmpty();
        assertThat(constraintViolations).anyMatch(v -> v.getPropertyPath().toString().equals("telephone"));
    }

    @Test
    public void shouldValidateOwnerWhenAllFieldsCorrect() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Owner owner = createValidOwner();

        Validator validator = createValidator();
        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(owner);

        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    private Owner createValidOwner() {
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Springfield");
        owner.setTelephone("1234567890");
        return owner;
    }

}
