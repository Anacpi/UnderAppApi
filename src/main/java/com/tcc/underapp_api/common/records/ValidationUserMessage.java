package com.tcc.underapp_api.common.records;

import java.util.Arrays;

/**
 * Maps technical validation messages to frontend-facing messages in Portuguese.
 */
public enum ValidationUserMessage {
    EMAIL_REQUIRED(ValidationMessages.EMAIL_REQUIRED, "O e-mail é obrigatório."),
    INVALID_EMAIL(ValidationMessages.INVALID_EMAIL, "Informe um e-mail válido."),
    EMAIL_MAX_LENGTH(ValidationMessages.EMAIL_MAX_LENGTH, "O e-mail deve ter no máximo 254 caracteres."),
    PASSWORD_REQUIRED(ValidationMessages.PASSWORD_REQUIRED, "A senha é obrigatória."),
    PASSWORD_MAX_LENGTH(ValidationMessages.PASSWORD_MAX_LENGTH, "A senha deve ter no máximo 72 caracteres."),
    PASSWORD_RANGE(ValidationMessages.PASSWORD_RANGE, "A senha deve ter entre 8 e 72 caracteres."),
    PASSWORD_PATTERN(
            ValidationMessages.PASSWORD_PATTERN,
            "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula e um número."),
    FIRST_NAME_REQUIRED(ValidationMessages.FIRST_NAME_REQUIRED, "O primeiro nome é obrigatório."),
    FIRST_NAME_RANGE(ValidationMessages.FIRST_NAME_RANGE, "O primeiro nome deve ter entre 2 e 100 caracteres."),
    FIRST_NAME_PATTERN(ValidationMessages.FIRST_NAME_PATTERN, "O primeiro nome deve conter apenas letras."),
    LAST_NAME_REQUIRED(ValidationMessages.LAST_NAME_REQUIRED, "O sobrenome é obrigatório."),
    LAST_NAME_RANGE(ValidationMessages.LAST_NAME_RANGE, "O sobrenome deve ter entre 2 e 100 caracteres."),
    LAST_NAME_PATTERN(ValidationMessages.LAST_NAME_PATTERN, "O sobrenome deve conter apenas letras."),
    CEP_REQUIRED(ValidationMessages.CEP_REQUIRED, "O CEP é obrigatório."),
    CEP_PATTERN(ValidationMessages.CEP_PATTERN, "O CEP deve conter exatamente 8 dígitos."),
    INVALID_BODY(ValidationMessages.INVALID_BODY, "Corpo da requisição inválido ou mal formatado."),
    INVALID_TYPE("", "");

    private final String technicalMessage;
    private final String userMessage;

    ValidationUserMessage(String technicalMessage, String userMessage) {
        this.technicalMessage = technicalMessage;
        this.userMessage = userMessage;
    }

    public String technicalMessage() {
        return technicalMessage;
    }

    public String userMessage() {
        return userMessage;
    }

    public static String fromTechnicalMessage(String technicalMessage) {
        return Arrays.stream(values())
                .filter(value -> !value.technicalMessage.isBlank())
                .filter(value -> value.technicalMessage.equals(technicalMessage))
                .findFirst()
                .map(ValidationUserMessage::userMessage)
                .orElse("O campo informado é inválido.");
    }

    public static String invalidTypeMessage(String fieldName, String expectedType) {
        return "O campo '" + fieldName + "' deve ser do tipo " + expectedType + ".";
    }
}
