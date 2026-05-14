package com.attendance.attendance_system;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @GetMapping("/status")
    public Map<String, String> status() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Attendance System");
        response.put("status", "UP");
        return response;
    }

    @PostMapping("/checkin")
    public Map<String, String> checkin(@RequestBody Map<String, String> request) {
        String user = request.getOrDefault("user", "unknown");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Check-in successful");
        response.put("user", user);

        return response;
    }
}