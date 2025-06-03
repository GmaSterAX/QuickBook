package com.softwarearchitecture.QuickBook.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_service")
public class RoomService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String service_name;
    private String roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public RoomService(long id, String serviceName, String roomType) {
        this.id = id;
        this.service_name = serviceName;
        this.roomType = roomType;
    }
}
