package uno.dos.tres.service.validator;

import uno.dos.tres.bean.RegInfo;
import uno.dos.tres.service.ServiceException;


public interface Validator {

    void checkRegInfo(RegInfo regInfo) throws ValidatorException;

}
