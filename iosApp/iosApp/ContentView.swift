import SwiftUI
import shared

struct ContentView: View {
  @ObservedObject private(set) var mainActorObserver: MainActorObservableObject

    var body: some View {
        NavigationView {
            listView()
            .navigationBarTitle("SpaceX Launches")
            .navigationBarItems(trailing:
                Button("Reload") {
                self.mainActorObserver.spaceXViewModel.asyncGetSpacexRocketLaunches(forceReload: true)
            })
        }
    }

    private func listView() -> AnyView {

        switch mainActorObserver.launches {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let launches):
            return AnyView(List(launches) { launch in
                RocketLaunchRow(rocketLaunch: launch)
            })
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
}


extension ContentView {

    enum LoadableLaunches {
        case loading
        case result([RocketLaunch])
        case error(String)
    }

    @MainActor
    class MainActorObservableObject: ObservableObject {
        let spaceXViewModel: SpaceXViewModel
        @Published var launches = LoadableLaunches.loading

        init(spaceXViewModel: SpaceXViewModel) {
            self.spaceXViewModel = spaceXViewModel
            self.myObserve()
            spaceXViewModel.asyncGetSpacexRocketLaunches(forceReload: true)
        }

        func myObserve() {

            spaceXViewModel.flowOfRocketLaunches.subscribe { (nsArray: NSArray) in
                let someRocketLaunches: [RocketLaunch]? = nsArray as? [RocketLaunch]
                if(someRocketLaunches != nil) {
                    self.launches = .result(someRocketLaunches!)
                }
            } onComplete: {
                
            } onThrow: { (mThrowable: KotlinThrowable) in
                let mError: String? = mThrowable.message
                if(mError != nil) {
                    self.launches = .error(mError!)
                }
            }
        }
    }
}


extension RocketLaunch: Identifiable { }
