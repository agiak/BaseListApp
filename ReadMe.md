# Base list application

This application shows a list of products. On tap, the user is navigated to a new screen to see product details.

The application retrieves the data from the server and stores them in a local database The next time the application is opened content is retrieved from the database. In addition, the application provides a refresh functionality. On refresh, new data is retrieved from the server and stored in the database, replacing the old ones.

# Architecture
For the development of the application is used the following technologies:
1. App follows MVVM architecture.
2. Hilt is used for dependency injection.
3. Room is used for the local database.
4. Retrofit is used for networking communication.
5. Coroutines and Flows are used for the communication between application layers.
6. New Android splash screen API is integrated.

# Install and Run the Project

Just fill in your server base URL at the BASE_API_URL attribute in the build.gradle (app module) file. Afterward, replace the endpoint at ProductService with your endpoint.
For debugging reasons there is a functionality to run the application with mock data and fake the refresh functionality. To enable mock data just set IS_DEMO_APP attribute to true.
