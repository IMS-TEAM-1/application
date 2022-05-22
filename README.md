# IMS 3000 Application

<h2>Software Design Description</h2>

<h3>MVVM</h3>

The application is based off MVVM software design pattern. It stands for Model-View-ViewModel and it enforces separation of concerns. 

* Model - Contains repositories that collects all data in one central place. This data can be fetched from for example databases or APIs.
* View - Contains the visual elements, that the end user can interact with. For example clicking buttons, reading text. The buttons are for example in layout files.
* ViewModel - Contains the controls for interacting with the view from a programmer perspective. It is used to request data from the model, apply logic to it like filtering a list of validating input. On events the view can fetch new data from the viewmodel. 

<h3>Dependency Injection</h3>

Classes in applications depend on other applications. "Dependency Injection (DI) allows classes to define their dependencies without constructing them." - https://developer.android.com/topic/architecture

This means that instead of the class creating the object itself, but is passed as parameter to the class. The package Dagger-Hilt solves this for us. This improves flexibility of the components.

<h3>View Binding</h3>

Allows for easy access to the views components outside of the XML file. Typically in Android, one has to use the function "findViewById()", however as the View Binding generates a class for each XML layout of the app, it simply needs a call to a "binding" in order to find the desired view-component.

<h3>Retrofit, GSON, OkHttp</h3>

The chosen method for sending HTTP requests is Retrofit combined with GSON and OkHttp. Retrofit is a package which allows us to send HTTP requests in Kotlin. We use it together with GSON which helps in converting JSON strings into objects which we can use and insert into our repositories. OkHttp is logging package which is used to help debug when things go wrong. It does for example tell us all information regarding a HTTP request, such as the status code, the header, the body or whatever else one may want to see when debugging requests.

<h3>EasyPermissions</h3>

Package for handling permissions.

<h3>Filestructure</h3>

<h4>api</h4>

The api folder contains files necessary for sending HTTP requests. 

**ApiInterface** which contains the different addresses the app sends requests to.

**ApiRepository** which sends the actual requests to the API.

**Util** folder which contains the resource file which handles abstraction of the response from the API.

<h4>data</h4>

The data folder contains the dataclasses throughtout the app, it has 2 sub folders:

**entities** and **remote**

entities are dataclasses which is only used locally

remote are dataclasses which mimics json objects from the API.

<h4>di</h4>

**NetworkModule** is used to setup all the packages for handling http requests. We setup Retrofit and OkHttp here.

**RepositoryModule** is used to provide our ApiRepository.

**AppModule** is not currently used as we are not actually handling any images in the application.

<h4>ui</h4>

The ui folder contains files and subfolders relevant to the UI.

**fragments** folder contains all layouts that used on top of an activity.

**viewmodels** folder contains all viewmodels that are used to act as a mediator between api and views.

**MapDrawer** file which is a helper class for visualizing the path of the mower in the MapFragment.



<h2>Process Requirements </h2>

Minimum
- The app shall take user input and translate this to drive commands passed to the robot.
- The app shall visualize the path travelled by the Mower including collision avoidance events.
- The code shall be under version control. (Github)
- There shall be a Software Design Description document where each source code component is described (Work in progress)
- A basic Work Breakdown Strcture (WBS) shall be created for the intended scope of the project.
- An analysis of the project shall be done (including process, technical aspects, collaboration and results) and documented in a Lessons Learned Document.

Medium
- The Software Design Description shall contain low level requirements linked to the high level requirements in as needed in order to implement the functionality.
    LLNR = Low Level Number Requirement
    - LLNR1: ApiStructure 1p
    - LLNR2: Setup Bluetooth connection between application and Mower. 13p
    - LLNR3: Fetch and set status to manual driving/standby/autonomous 3p
    - LLNR4: Send commands to mower from the app through bluetooth 3p
    - LLNR5: Create utility draw class  13p
    - LLNR6: Fetch location api requests 3p

    - Application structure 3p
    - Application mock up 3p
    - Command the mower via bluetooth 13p
    - Create all views to mimick the UX design 8p
    - Visualize path from mower 13p
- Each Low level requirement or related software work package shall contain an estimate of how much work that is required for completion of the task. (We did this in Miro.)
- The sum of all tasks and estimated efforts shall be compared with the time frame and resource availability of the project and planned accordingly. (Miro again)
- Completion of tasks and used effort shall be compared with the plan and the estimates. (Lesson Learned Document)

Maximum
- The software design document shall contain and architectural overview of the system and its sub components. (Software Architecture Design image)
- The source code shall be traceable to respective low level requirement. (In Software Design Description(LLNR))
- Test cases shall be defined for all requirements and a test report produced. (Not implemented in the app)

