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
        "org.apache.velocity:velocity:jar:1.7",
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

# https://data.stadt-zuerich.ch/dataset?res_format=csv&tags=entsorgung&groups=umwelt
http_file(
    name = "entsorgungskalender_bioabfall",
    urls = [
        "https://data.stadt-zuerich.ch/dataset/erz_entsorgungskalender_bioabfall/download/entsorgungskalender_bioabfall_2021.csv",
    ],
)

http_file(
    name = "entsorgungskalender_karton",
    urls = [
        "https://data.stadt-zuerich.ch/dataset/erz_entsorgungskalender_karton/download/entsorgungskalender_karton_2021.csv",
    ],
)

http_file(
    name = "entsorgungskalender_papier",
    urls = [
        "https://data.stadt-zuerich.ch/dataset/erz_entsorgungskalender_papier/download/entsorgungskalender_papier_2021.csv",
    ],
)
