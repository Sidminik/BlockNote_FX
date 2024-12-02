package javafx.scene.input;

import java.util.HashMap;
import java.util.Map;

public enum KeyCode {
    ESCAPE(0x1B, "Esc"),
    F(0x46, "F"),
    J(	0x4A, "J"),
    N(0x4E, "N"),
    O(0x4F, "O"),
    S(0x53, "S"),
    W(0x57, "W"),
    F1(0x70, "F1");

    private final int code;
    private final String name;
    private final Map nameMap = new HashMap(KeyCode.values().length);

    KeyCode(int code, String name) {
        this.code = code;
        this.name = name;
        for (KeyCode c : KeyCode.values()) {
            nameMap.put(c.name, c);
        }
    }
}
