import SwiftUI
import shared

@main
struct iOSApp: App {
//    let sdk = SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory())
    let spacexViewModel = SpaceXViewModel(spacexRepository: SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory()))
    var body: some Scene {
        WindowGroup {
            ContentView(mainActorObserver: .init(spaceXViewModel: spacexViewModel))
        }
    }
}
