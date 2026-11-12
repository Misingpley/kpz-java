package com.example.demo;

import com.example.demo.client.CameraClient;
import com.example.demo.dto.CameraInfoDto;
import com.example.demo.dto.CameraItemDto;
import com.example.demo.dto.CameraListDto;
import com.example.demo.entity.CameraEntity;
import com.example.demo.repository.CameraRepository;
import com.example.demo.service.CameraService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CameraJpaServiceTest {

    @Test
    void shouldImportFindAndDeleteCameras() {
        CameraClient client = Mockito.mock(CameraClient.class);
        CameraRepository repo = Mockito.mock(CameraRepository.class);
        CameraService service = new CameraService(client, repo);

        CameraItemDto item = new CameraItemDto(
                1L, "Main Entrance", "DOME", "ONLINE",
                new CameraInfoDto.Location("Lobby", 1)
        );
        when(client.getCameras()).thenReturn(new CameraListDto(List.of(item)));
        when(repo.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        List<CameraEntity> imported = service.importCameras();
        assertEquals(1, imported.size());
        assertEquals("Main Entrance", imported.get(0).getName());
        assertEquals("Lobby", imported.get(0).getRoom());

        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.getSavedCameraById(1L));

        when(repo.existsById(1L)).thenReturn(true);
        service.deleteSavedCamera(1L);
        verify(repo).deleteById(1L);
    }
}
