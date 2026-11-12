package com.example.demo;

import com.example.demo.controller.CameraController;
import com.example.demo.dto.CameraInfoDto;
import com.example.demo.dto.SnapshotDto;
import com.example.demo.service.CameraService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CameraControllerTest {

    @Test
    void shouldReturnCameraInfoAndDecodedJpegSnapshot() {
        CameraService service = Mockito.mock(CameraService.class);
        CameraController controller = new CameraController(service);

        CameraInfoDto expectedInfo = new CameraInfoDto(
                101, "IP_CAMERA", "ONLINE",
                List.of("night_vision", "motion_detection"),
                new CameraInfoDto.Location("Server Room", 2)
        );
        when(service.getCameraInfo()).thenReturn(expectedInfo);
        assertEquals(expectedInfo, controller.getInfo());

        byte[] fakeJpeg = new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0};
        String base64 = Base64.getEncoder().encodeToString(fakeJpeg);
        when(service.captureSnapshot()).thenReturn(new SnapshotDto(base64));

        ResponseEntity<byte[]> response = controller.captureSnapshot();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
        assertArrayEquals(fakeJpeg, response.getBody());
    }
}
