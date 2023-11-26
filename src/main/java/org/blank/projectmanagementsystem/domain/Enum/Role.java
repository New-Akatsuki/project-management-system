package org.blank.projectmanagementsystem.domain.Enum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

public enum Role {
    PMO,SDQC,DH,PM, ADMIN, MEMBER
}
