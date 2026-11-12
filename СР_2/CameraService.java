package com.example.demo.service;

import com.example.demo.client.CameraClient;
import com.example.demo.dto.CameraInfoDto;
import com.example.demo.dto.CameraItemDto;
import com.example.demo.dto.CameraListDto;
import com.example.demo.dto.SnapshotDto;
import com.example.demo.entity.CameraEntity;
import com.example.demo.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CameraService {

    private final CameraClient cameraClient;
    private final CameraRepository cameraRepository;

    public CameraInfoDto getCameraInfo() {
        log.info("Getting camera info");
        CameraInfoDto response = cameraClient.getCameraInfo();
        log.info("Camera info received: {}", response);
        return response;
    }

    public SnapshotDto captureSnapshot() {
        log.info("Capturing snapshot");
        SnapshotDto response = cameraClient.captureSnapshot();
        log.info("Snapshot received");
        return response;
    }

    @Transactional
    public List<CameraEntity> importCameras() {
        log.info("Importing cameras from external service");
        CameraListDto response = cameraClient.getCameras();
        if (response == null || response.cameras() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Зовнішній сервіс повернув порожній список камер");
        }
        List<CameraEntity> entities = response.cameras().stream()
                .map(this::toEntity)
                .toList();
        List<CameraEntity> saved = cameraRepository.saveAll(entities);
        log.info("Imported and saved {} cameras to H2", saved.size());
        return saved;
    }

    @Transactional(readOnly = true)
    public List<CameraEntity> getSavedCameras() {
        return cameraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CameraEntity getSavedCameraById(Long id) {
        return cameraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Камеру з ID " + id + " не знайдено в базі H2"
                ));
    }

    @Transactional
    public void deleteSavedCamera(Long id) {
        if (!cameraRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Камеру з ID " + id + " не знайдено");
        }
        cameraRepository.deleteById(id);
        log.info("Deleted camera with ID {}", id);
    }

    private CameraEntity toEntity(CameraItemDto dto) {
        String room = dto.location() != null ? dto.location().room() : null;
        Integer floor = dto.location() != null ? dto.location().floor() : null;
        return CameraEntity.builder()
                .id(dto.id())
                .name(dto.name())
                .deviceType(dto.deviceType())
                .status(dto.status())
                .room(room)
                .floor(floor)
                .build();
    }
}
