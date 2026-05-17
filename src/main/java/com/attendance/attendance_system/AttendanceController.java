package com.attendance.attendance_system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @GetMapping("/status")
    public Map<String, String> status() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Attendance system");
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