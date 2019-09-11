# 创建项目

（一）创建项目

在android studio中创建新项目，步骤不累述

（二）添加依赖

在项目的**build.gradle**中添加依赖库

```
buildscript {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
        ...
    }
    ...
}

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
        ...
    }
}
```



在模块的**build.gradle**中添加依赖

```groovy
android {
    ...
    compileOptions {
        sourceCompatibility 1.8
        targetCompatibility 1.8
    }
    ...
}
dependencies {
    ...
    implementation 'com.github.yuanye1818.afuncs:core:1.0.9'
    implementation 'com.github.yuanye1818.afuncs:func4j:1.0.9'
    implementation 'com.github.yuanye1818.afuncs:core_compiler_annotation:1.0.9'
    annotationProcessor 'com.github.yuanye1818.afuncs:core_compiler:1.0.9'
    //Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
    //RxJava
    implementation 'io.reactivex.rxjava2:rxjava:2.2.2'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
    ...
}

```

（三）创建配置类

创建**Configs.java**，继承自**cn.yuanye1818.func4a.global.CoreConfigs**

```java
public class Configs extends CoreConfigs {
   ...
}
```

（四）创建Application类

创建**APP.java**，继承自**cn.yuanye1818.func4a.global.App**，在*createConfigs*方法中返回*Configs*对象

```java
public class APP extends App {
    @Override
    public CoreConfigs createConfigs() {
        return new Configs();
    }
}
```

（五）配置AndroidManifest

在**AndroidManifest.xml**中添加配置

```xml
<application
        ...
        android:name="global.APP"
        ...
        android:theme="@style/AppTheme.Main">

</application>
```

