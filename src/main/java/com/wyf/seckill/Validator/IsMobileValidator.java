package com.wyf.seckill.Validator;

import  javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wyf.seckill.util.ValidateUtil;
import org.apache.commons.lang3.StringUtils;


public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required) {
            return ValidateUtil.isMobile(value);
        }else {
            if(StringUtils.isEmpty(value)) {
                return true;
            }else {
                return ValidateUtil.isMobile(value);
            }
        }
    }

}
