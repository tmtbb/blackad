# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/SamWong/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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



-keepattributes SourceFile,LineNumberTable
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepattributes Signature
-keepattributes *Annotation*


-keepattributes SourceFile,LineNumberTable,*Annotation*
#-keep class com.crashlytics.android.**

-keep class  com.lidroid.xutils.**{*;}
-keep class  com.loopj.**{*;}
-keep class  android.support.**{*;}

-keep class  com.kenai.**{*;}
-keep class  com.novell.**{*;}
-keep class  de.measite.**{*;}
-keep class  org.**{*;}

-keep class  com.yishi.**{*;}

-keep class  com.tencent.**{*;}
-dontwarn com.tencent.connect.avatar.c.**

-keep class  com.sina.**{*;}


-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }

-keep public class com.yundian.comm.annotation.**{*;}
-keep public class com.yundian.comm.networkapi.**{*;}
-keep public class com.yundian.blackcard.android.activity.**{*;}
-keep public class com.yundian.blackcard.android.fragment.**{*;}
-keep public class com.yundian.blackcard.android.controller.**{*;}
-keep public class com.yundian.blackcard.android.view.**{*;}

-keep public class net.**{*;}

-keep public class com.yundian.blackcard.android.R$*{
    public static final int *;
}

-keepattributes Signature,*Annotation*

-dontwarn com.ut.mini.**
-dontwarn okio.**
-dontwarn com.xiaomi.**
-dontwarn com.squareup.wire.**

-keepattributes *Annotation*

-keep class okio.** {*;}
-keep class com.squareup.wire.** {*;}

-keep class com.umeng.message.* {
	 public <fields>;
         public <methods>;
}

-keep class org.android.agoo.impl.* {
	 public <fields>;
         public <methods>;
}

-keep class org.android.agoo.service.* {*;}

-keep class org.android.spdy.**{*;}

-keep public class **.R$*{
    public static final int *;
}

-keep class org.apache.*{}
-ignorewarnings

-keep class widget.*{*;}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#-------------环信-START---------------
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
#-------------环信-END---------------

#-------------网络-START---------------
#okhttp
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.{*;}
#retrofit
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keep class io.reactivex.** { *; }

-keep class com.yundian.blackcard.android.networkapi.**{*;}
-keep class com.yundian.blackcard.android.model.**{*;}

#-------------网络-END---------------

#fastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

#umeng
-keep class com.umeng.**{*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.yundian.blackcard.android.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
