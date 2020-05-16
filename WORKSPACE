# This project is build with https://bazel.build/

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive", "http_file")

RULES_JVM_EXTERNAL_TAG = "2.10"

RULES_JVM_EXTERNAL_SHA = "1bbf2e48d07686707dd85357e9a94da775e1dbd7c464272b3664283c9c716d26"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

# Run bazel run @unpinned_maven//:pin
# when deps are changed
maven_install(
    name = "maven",
    artifacts = [
        "com.beust:jcommander:1.78",
        "com.google.auto.value:auto-value:jar:1.7",
        "com.google.auto.value:auto-value-annotations:jar:1.7",
        "com.google.truth:truth:0.36",
    ],
    maven_install_json = "//third_party:maven_install.json",
    repositories = [
        "https://jcenter.bintray.com/",
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()

http_file(
    name = "entsorgungskalender_bioabfall_2020",
    urls = [
        "https://data.stadt-zuerich.ch/dataset/entsorgungskalender_gartenabfall/resource/a0953059-f4e6-4fe5-8db3-a2ccbda884a6/download/entsorgungskalender_bioabfall_2020.csv",
    ],
)

http_file(
    name = "entsorgungskalender_karton_2020",
    urls = [
        "https://data.stadt-zuerich.ch/dataset/entsorgungskalender_karton/resource/6d28096a-1e04-43ef-8d18-0ce9464a7329/download/entsorgungskalender_karton_2020.csv",
    ],
)

http_file(
    name = "entsorgungskalender_papier_2020",
    urls = [
        "https://data.stadt-zuerich.ch/dataset/entsorgungskalender_papier/resource/eeca6200-7cc1-4f05-af13-fc262b830149/download/entsorgungskalender_papier_2020.csv",
    ],
)
