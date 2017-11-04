package util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FormValidateTest {
    @Test
    public void validateEmailShoulReturnTrue() throws Exception {
        Assert.assertEquals(true, FormValidate.validateEmail("mahendrathennakoon@gmail.com"));
    }

    @Test
    public void validateEmailShouldReturnFalse() throws Exception {
        Assert.assertEquals(false, FormValidate.validateEmail("mahendrathennakoon@gmail"));
    }

    @Test
    public void countCharacter() throws Exception {
        Assert.assertEquals(4, FormValidate.countCharacter("aaaab", 'a'));
    }

    @Test
    public void validateNICShouldReturnTrue() throws Exception {
        Assert.assertEquals(true, FormValidate.validateNIC("941567865v"));
    }

    @Test
    public void validatePhone() throws Exception {
        Assert.assertEquals(true, FormValidate.ValidatePhone("0988989786"));
    }

    @Test
    public void validateAge() throws Exception {
        Assert.assertEquals(true, FormValidate.validateAge("23"));
    }

    @Test
    public void isMinusValue() throws Exception {
        Assert.assertEquals(true, FormValidate.isMinusValue("-12"));
    }

}