package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.data.annotation.CreatedDate;
import ru.practicum.shareit.booking.BookingStatus;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {
    Long id;
    @NonNull
    Long itemId;
    @FutureOrPresent
    LocalDateTime start;
    @Future
    LocalDateTime end;
    BookingStatus status;
    Long bookerId;
}
