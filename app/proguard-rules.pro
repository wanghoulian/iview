# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class !com.bumptech.glide.repackaged.**,com.bumptech.glide.**
# Keep the entry point to this library, see META-INF\services\javax.annotation.processing.Processor
-keep class com.bumptech.glide.annotation.compiler.GlideAnnotationProcessor
# Reflective accesses in com.google.common.util.concurrent.* and some others
-dontnote com.bumptech.glide.repackaged.com.google.common.**
