
package com.example.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "room_devices")
public class Room_Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long deviceId;

    // Foreign key to Room by room_name
    @ManyToOne
    @JoinColumn(name = "room_name", referencedColumnName = "room_name", nullable = false)
    private Room room;
    //room name used for better readability

    @Column(name = "device_name", nullable = false, unique = true)
    private String deviceName;
    //example: "Living Room Light"

    @Column(name = "watts", precision = 10, scale = 2)
    private BigDecimal watts;

    @Column(name = "hours_per_day", precision = 5, scale = 2)
    private BigDecimal hoursPerDay;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Getters and Setters
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public BigDecimal getWatts() { return watts; }
    public void setWatts(BigDecimal watts) { this.watts = watts; }

    public BigDecimal getHoursPerDay() { return hoursPerDay; }
    public void setHoursPerDay(BigDecimal hoursPerDay) { this.hoursPerDay = hoursPerDay; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
