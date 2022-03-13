package com.yuvaraj.securityservice.models.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yuvaraj.securityservice.helpers.TokenType;
import com.yuvaraj.securityservice.models.DefaultToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SessionToken extends DefaultToken {

    private String customerId;
    private String type;

    public SessionToken(String customerId) {
        this.customerId = customerId;
        this.type = TokenType.SESSION.getType();
    }
}
