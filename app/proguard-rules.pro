-keepattributes *Annotation*,EnclosingMethod,Signature

# Crashlytics
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

# Firebase database objects
-keepclassmembers public class manwithandroid.learnit.models.** { *; }
-keepclassmembers public class manwithandroid.learnit.models.adapters.** { *; }

-dontwarn okio.**
-dontwarn retrofit2.**

-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**
-keep class org.codehaus.** { *; }
-keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
    public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *; }
