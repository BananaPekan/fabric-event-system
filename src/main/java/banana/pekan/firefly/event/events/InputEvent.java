package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;

public class InputEvent extends Event {

    public InputEvent() {

    }

    public static class KeyEvent extends InputEvent {

        int keyCode;
        int scanCode;

        int action;

        public KeyEvent(int keyCode, int scanCode, int action) {
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.action = action;
        }

        public int getKeyCode() {
            return keyCode;
        }

        public int getScanCode() {
            return scanCode;
        }

        public KeyAction getAction() {
            return KeyAction.getAction(action);
        }

        enum KeyAction {
            Pressed(1), Released(0), Held(2);

            final int action;

            KeyAction(int action) {
                this.action = action;
            }

            public int getAction() {
                return action;
            }

            public static KeyAction getAction(int action) {
                return switch (action) {
                    case 0 -> Released;
                    case 1 -> Pressed;
                    case 2 -> Held;
                    default -> null;
                };
            }

        }


    }

    public static class MouseEvent extends Event {

        int action;
        int button;

        public MouseEvent(int button, int action) {
            this.button = button;
            this.action = action;
        }

        public void setButton(int button) {
            this.button = button;
        }

        public void setAction(MouseAction action) {
            this.action = action.action;
        }

        public MouseAction getAction() {
            return MouseAction.getAction(action);
        }

        public int getButton() {
            return button;
        }

        public enum MouseAction {

            Pressed(1), Released(0);

            public final int action;

            MouseAction(int action) {
                this.action = action;
            }

            public static MouseAction getAction(int action) {
                return switch (action) {
                    case 1 -> Pressed;
                    case 0 -> Released;
                    default -> null;
                };
            }

        }

    }

}
