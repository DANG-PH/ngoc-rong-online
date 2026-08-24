-keep class com.badlogic.gdx.** { *; }
-keep interface com.badlogic.gdx.** { *; }
-dontwarn com.badlogic.gdx.**

-keep class com.dang.dragonboy.** { *; }
-dontwarn com.dang.dragonboy.**

-keepattributes Signature
-keepattributes *Annotation*

-keep class com.google.gson.** { *; }
-keep class com.google.protobuf.** { *; }
-dontwarn com.google.protobuf.**

-keep class org.java_websocket.** { *; }
-dontwarn org.java_websocket.**

-keep class io.socket.** { *; }
-dontwarn io.socket.**

-dontwarn org.slf4j.**
