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

    public VerificationType getSendTo() {
        return sendTo;
    }

    public void setSendTo(VerificationType sendTo) {
        this.sendTo = sendTo;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
