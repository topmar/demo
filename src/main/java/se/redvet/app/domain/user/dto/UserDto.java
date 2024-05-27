package se.redvet.app.domain.user.dto;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record UserDto(
        int id,
        String username,
        String firstName,
        String lastName,
        String email,
        Timestamp createdAt,
        Timestamp updatedAt
) {
}
