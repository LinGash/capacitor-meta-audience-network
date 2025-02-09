// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "LingashCapacitorMetaAudienceNetwork",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "LingashCapacitorMetaAudienceNetwork",
            targets: ["MetaAudienceNetworkPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "MetaAudienceNetworkPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/MetaAudienceNetworkPlugin"),
        .testTarget(
            name: "MetaAudienceNetworkPluginTests",
            dependencies: ["MetaAudienceNetworkPlugin"],
            path: "ios/Tests/MetaAudienceNetworkPluginTests")
    ]
)