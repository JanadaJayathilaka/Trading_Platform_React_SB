package com.example.trading.model;

import com.example.trading.domain.VerificationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class TwoFactorAuth {
    @JsonProperty("isEnabled")
    private boolean isEnabled =false;
    @JsonProperty("sendTo")
    private VerificationType sendTo;
}
