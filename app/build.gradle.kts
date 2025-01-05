import com.android.build.api.variant.ComponentIdentity
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import sp.gx.core.asFile
import sp.gx.core.buildDir
import sp.gx.core.camelCase
import sp.gx.core.create
import sp.gx.core.getByName
import sp.gx.core.kebabCase

repositories {
    google()
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.compose") version Version.compose
}

fun ComponentIdentity.getVersion(): String {
    val versionName = android.defaultConfig.versionName ?: error("No version name!")
    check(versionName.isNotBlank())
    val versionCode = android.defaultConfig.versionCode ?: error("No version code!")
    check(versionCode > 0)
    check(name.isNotBlank())
    return when (name) {
        "realRelease" -> kebabCase(
            versionName,
            versionCode.toString(),
        )
        else -> kebabCase(
            versionName,
            name,
            versionCode.toString(),
        )
    }
}

android {
    namespace = "test.android.cns2"
    compileSdk = Version.Android.compileSdk

    defaultConfig {
        applicationId = namespace
        minSdk = Version.Android.minSdk
        targetSdk = Version.Android.targetSdk
        versionCode = 1
        versionName = "0.0.$versionCode"
        manifestPlaceholders["appName"] = "@string/app_name"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".$name"
            versionNameSuffix = "-$name"
            isMinifyEnabled = false
            isShrinkResources = false
            manifestPlaceholders["buildType"] = name
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions.kotlinCompilerExtensionVersion = "1.5.15"

    productFlavors {
        "version".also { dimension ->
            flavorDimensions += dimension
            create("real") {
                this.dimension = dimension
                manifestPlaceholders["f$dimension"] = name
            }
            create("mock") {
                this.dimension = dimension
                manifestPlaceholders["f$dimension"] = name
                applicationIdSuffix = ".$name"
                versionNameSuffix = "-$name"
            }
        }
    }
}

androidComponents.onVariants { variant ->
    val output = variant.outputs.single()
    check(output is com.android.build.api.variant.impl.VariantOutputImpl)
    output.outputFileName = "${kebabCase(rootProject.name, variant.getVersion())}.apk"
    afterEvaluate {
        tasks.getByName<JavaCompile>("compile", variant.name, "JavaWithJavac") {
            targetCompatibility = Version.jvmTarget
        }
        tasks.getByName<KotlinCompile>("compile", variant.name, "Kotlin") {
            kotlinOptions.jvmTarget = Version.jvmTarget
        }
        val checkManifestTask = tasks.create("checkManifest", variant.name) {
            dependsOn(camelCase("compile", variant.name, "Sources"))
            doLast {
                val file = layout.buildDir()
                    .dir("intermediates/merged_manifests/${variant.name}")
                    .dir(camelCase("process", variant.name, "Manifest"))
                    .asFile("AndroidManifest.xml")
                val manifest = groovy.xml.XmlParser().parse(file)
                val actual = manifest.getAt(groovy.namespace.QName.valueOf("uses-permission")).map {
                    check(it is groovy.util.Node)
                    val key = groovy.namespace.QName.valueOf("{http://schemas.android.com/apk/res/android}name")
                    it.attributes()[key] as String
                }
                val applicationId by variant.applicationId
                val expected = setOf(
                    "$applicationId.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION",
                )
                check(actual.sorted() == expected.sorted()) {
                    "Actual is:\n$actual\nbut expected is:\n$expected"
                }
            }
        }
        tasks.getByName(camelCase("assemble", variant.name)) {
            dependsOn(checkManifestTask)
        }
    }
}

dependencies {
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation(compose.foundation)
    implementation("com.github.kepocnhh:Logics:0.1.3-SNAPSHOT")
}
