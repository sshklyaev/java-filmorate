package Exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationException extends IllegalArgumentException {

    public ValidationException(String s) {
        log.error(s);
    }
}
