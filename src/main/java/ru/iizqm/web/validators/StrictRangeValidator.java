package ru.iizqm.web.validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("strictRangeValidator")
public class StrictRangeValidator implements Validator<Float> {
    @Override
    public void validate(FacesContext context, UIComponent component, Float value) throws ValidatorException {
        if (value != null && (value <= -5.0f || value >= 5.0f)) {
            throw new ValidatorException(new FacesMessage("Введите число строго от -5 до 5, не включая границы"));
        }
    }
}