KeyCode {
  classvar keys;

  *initClass {
    // X11 keycodes for linux. Found here: https://gist.github.com/rickyzhang82/8581a762c9f9fc6ddb8390872552c250
    keys = (
      \A: 38,
      \B: 56,
      \C: 54,
      \D: 40,
      \E: 26,
      \F: 41,
      \G: 42,
      \H: 43,
      \I: 31,
      \J: 44,
      \K: 45,
      \L: 46,
      \M: 58,
      \N: 57,
      \O: 32,
      \P: 33,
      \Q: 24,
      \R: 27,
      \S: 39,
      \T: 28,
      \U: 30,
      \V: 55,
      \W: 25,
      \X: 53,
      \Y: 29,
      \Z: 52,
      \AltLeft: 64,
      \AltRight: 113,
      \Backquote: 49,
      \Backslash: 51,
      \Backspace: 22,
      \BracketLeft: 34,
      \BracketRight: 35,
      \CapsLock: 66,
      \Comma: 59,
      \CtrlLeft: 37,
      \CtrlRight: 109,
      \CursorDown: 104,
      \CursorLeft: 100,
      \CursorRight: 102,
      \CursorUp: 98,
      \Dash: 20,
      \Delete: 107,
      \End: 103,
      \Equal: 21,
      \Esc: 9,
      \F10: 76,
      \F11: 95,
      \F12: 96,
      \F1: 67,
      \F2: 68,
      \F3: 69,
      \F4: 70,
      \F5: 71,
      \F6: 72,
      \F7: 73,
      \F8: 74,
      \F9: 75,
      \Home: 97,
      \Insert: 106,
      \International: 94,
      \LogoLeft: 115,
      \LogoRight: 116,
      \Menu: 117,
      \NUM0: 19,
      \NUM1: 10,
      \NUM2: 11,
      \NUM3: 12,
      \NUM4: 13,
      \NUM5: 14,
      \NUM6: 15,
      \NUM7: 16,
      \NUM8: 17,
      \NUM9: 18,
      \NumLock: 77,
      \Numpad0: 90,
      \Numpad1: 87,
      \Numpad2: 88,
      \Numpad3: 89,
      \Numpad4: 83,
      \Numpad5: 84,
      \Numpad6: 85,
      \Numpad7: 79,
      \Numpad8: 80,
      \Numpad9: 81,
      \NumpadAdd: 86,
      \NumpadDecimal: 91,
      \NumpadDivide: 112,
      \NumpadEnter: 108,
      \NumpadMultiply: 63,
      \NumpadSubtract: 82,
      \PageDown: 105,
      \PageUp: 99,
      \Pause: 110,
      \Period: 60,
      \PrintScreen: 111,
      \QuoteSingle: 48,
      \Return: 36,
      \ScrollLock: 78,
      \Semicolon: 47,
      \ShiftLeft: 50,
      \ShiftRight: 62,
      \Slash: 61,
      \Space: 65,
      \Tab: 23,
    );
  }

  add {|key|
    ^keys[key];
  }
}
