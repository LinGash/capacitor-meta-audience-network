import Foundation

@objc public class MetaAudienceNetwork: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
