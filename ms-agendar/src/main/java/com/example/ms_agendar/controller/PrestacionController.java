package com.example.ms_agendar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_agendar.dto.PrestacionResponse;
import com.example.ms_agendar.service.PrestacionService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/prestaciones")
@RequiredArgsConstructor 
public class PrestacionController {
 
    private final PrestacionService service;
 
    @GetMapping 
    public ResponseEntity<List<PrestacionResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}
