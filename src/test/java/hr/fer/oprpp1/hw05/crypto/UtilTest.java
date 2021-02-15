package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    public void byteToHexTest() {

        assertEquals("01ae22603ce08075a100", Util.byteToHex(new byte[] {1, -82, 34, 96, 60, -32, -128, 117, -95, 0}));
        
    }

    @Test
    public void hexToByteTest() {
        assertArrayEquals(new byte[] {1, -82, 34, 96, 60, -32, -128, 117, -95, 0}, Util.hexToByte("01aE22603ce08075a100"));
    }
}