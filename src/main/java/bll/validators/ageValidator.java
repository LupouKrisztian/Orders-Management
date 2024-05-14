package bll.validators;

import model.Client;

public class ageValidator implements Validator<Client> {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;

    /**
     * Validates the age of a Client.
     * Throws an IllegalArgumentException if the age is not within the specified limits.
     * @param t The Client object to validate.
     * @throws IllegalArgumentException If the age of the Client is not within the specified limits.
     */
    public void validate(Client t) {

        if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The Client Age limit is not respected!");
        }

    }
}