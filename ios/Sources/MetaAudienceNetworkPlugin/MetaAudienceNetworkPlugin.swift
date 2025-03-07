import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(MetaAudienceNetworkPlugin)
public class MetaAudienceNetworkPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "MetaAudienceNetworkPlugin"
    public let jsName = "MetaAudienceNetwork"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = MetaAudienceNetwork()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
