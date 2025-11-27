package ru.iizqm.web.validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("floatConverter")
public class FloatConverter implements Converter<Float> {
    @Override
    public Float getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value == null || value.trim().isEmpty()) return null;
            return Float.valueOf(value.replace(',', '.')); // поддержка ввода с запятой
        } catch (NumberFormatException e) {
            FacesMessage msg = new FacesMessage("Ошибка!", "Введите корректное вещественное число");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Float value) {
        return value != null ? value.toString() : "";
    }
}
