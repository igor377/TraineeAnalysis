package com.example.demo.RH.Controller;

import com.example.demo.RH.Domain.DTO.RHConfigDTO;
import com.example.demo.RH.Domain.DTO.RHConfigResponseDTO;
import com.example.demo.RH.Service.RHService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class RHController {

    @Autowired
    private RHService RHService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RHConfigResponseDTO> create (@RequestBody @Valid RHConfigDTO data) {
        RHConfigResponseDTO response = RHService.create(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{date}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<RHConfigResponseDTO> findByDate (@PathVariable("date") String date) {
        RHConfigResponseDTO response = RHService.getByDate(date);
        return ResponseEntity.ok(response);
    }
}
