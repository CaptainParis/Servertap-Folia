package io.servertap.api.platform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the PlatformFactory class
 */
public class PlatformFactoryTest {

    @Test
    @DisplayName("detectPlatformType should return UNKNOWN when no platform classes are available")
    void detectPlatformTypeReturnsUnknownWhenNoPlatformClassesAreAvailable() throws Exception {
        // Use reflection to access the private method
        java.lang.reflect.Method method = PlatformFactory.class.getDeclaredMethod("detectPlatformType");
        method.setAccessible(true);
        
        // Since the test environment doesn't have any platform classes, it should return UNKNOWN
        PlatformType result = (PlatformType) method.invoke(null);
        
        assertEquals(PlatformType.UNKNOWN, result);
    }
    
    @Test
    @DisplayName("createPlatform should throw UnsupportedOperationException when platform type is UNKNOWN")
    void createPlatformThrowsUnsupportedOperationExceptionWhenPlatformTypeIsUnknown() {
        Logger mockLogger = Mockito.mock(Logger.class);
        Object mockPlugin = new Object();
        
        try {
            PlatformFactory.createPlatform(mockPlugin, mockLogger);
        } catch (UnsupportedOperationException e) {
            assertEquals("Unsupported platform type: UNKNOWN", e.getMessage());
        }
    }
}
