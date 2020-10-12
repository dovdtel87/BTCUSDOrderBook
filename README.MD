# General information of the project:  
  
- **Language**: Kotlin  
- **IDE**: Android Studio  
- **Design Pattern**: MVVM (Model - View - ViewModel) with LiveData  
- **DI**: Dagger Hilt  
- **Concurrency**:  RxJava  
- **WebSockets**:  Scarlet  
- **Unit Test Framework**: Spec  
- **UI Test Framework**: Espresso  
  
The project follows a feature structure, there is a feature folder where all additional features would be placed, in this example, the feature provided is `orderbook`  
  
Each feature manages the three layers of clean architecture (Data - Domain - UI).  
  
As DI framework I used Dagger Hilt, its configuration is done in the subfolder DI  
  
The management of the response is done with `RxJava`, using `Scarlet` library from tinder to handle the communication with the websockets
  
I decided to to transform the data provided to the sockets to the domain model creating the modelts `FormattedOrderBook`and `Ticker` and doing the transformation with two  `Mapper`  
  
The project use the `UseCases` pattern in order to help identify the different use cases the app could have. In this example there are five use cases
  
In order to display the fragment and support navigation I decided to use the Android Navigation Component so adding a second fragment and handling the navigation to it could be easily setup in the `nav_graph`
  
For `UnitTest` I use the spec framework, as I think it improves the readability of the different use cases and as well the scalability for more complex input data structures.  
The  `UnitTest` test the 3 different layers of the feature  
  
I provide a `UITest` using espresso to test the displayed of the list in the fragment. This is done mocking the response of the view states. Please use an emulator with API 28 to run it.  

Please note, I didn't add end to end test, that should be added to test the integration of all the layers of the proyect.
  
The gradle file has 2 build types, one for debug and other for production, both of them refer to the same API URL.
  
# Scrum:  
In order to manage this project I followed the principles of _Scrum Methodology_ identifying the following tasks and estimating them as follows:  
  
- [x] **TaskA**: Setup the project -> Story Points: **2**  
- [x] **TaskB**: Establish the MVVM architecture with Data-Domain-UI -> Story Points: **5**  
- [x]  **TaskC**: Implement Dagger-> Story Points: **5**  
- [x] **TaskD**: Implement retrieving the data from WebSockets-> Story Points: **3**  
- [x] **TaskE**: Handle the messages received-> Story Points: **3**
- [x] **TaskF**: UI implementation of the order book -> Story Points: **3**  
- [x] **TaskG**: UITest of the view -> Story Points: **3**  
  
Other tasks identified not implemented:  
  
- [ ] **TaskH**: Improve designs  
- [ ] **TaskI**: Better handle of errors 
- [ ] **TaskJ**: Define more custom styles for the app  
- [ ] **TaskK**: Implement end to end tests
- [ ] **TaskL**: Implement UI test for details
- [ ] **TaskM**: Implement persistence for not network 
- [ ] **TaskN**: Implement pull to refresh  on the list
- [ ] **TaskO**: Migrate gradle to KTS  
