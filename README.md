# EmployeeDirection

1. Build & Tools:
  - Android Studio: 2021.2.1 Canary 3
  - kotlin-gradle-plugin:1.5.31
  - gradle:7.0.3

2. Focus area:
  - Clean Architecure: data + domain + presentation.
      - Data layer - it has remote repository, and handle the error scenarios caused by Exceptionss or bad data (such as missing mandantory field)
      - Domain layer - defined use cases, return Flow type to viewModel in presentation
      - Presentation - handle observers
  - Software design patters:
      - Creational: Independency inject, Singleton, 
      - Structual:  Facad in repository API, Adapter of recyclerView
      - Behavioral: Observer in viewModel
  - UI Implementaion: 
      - Activity as container
      - Activity owned toolBar with menu to load 3 different types of data (employees.json, employees_malformed.json,employees_empty.json) 
      - Fragment as the view holding the viewModel
  - Recyclerview efficiency
      - Adapter: Use DiffUtil to manage page render choice
      - overide equal function of data class Employee to be reference by DiffUtil
  - Memory Managemant
      - Overide size of drawable loaded by Glide ( here I didn't use Picasso because the image size is unknown, and we don't won't cached the full-size images)
      - Use Glide Automatic cache strategy
  - UX 
      - Add loading spinnier
      - Display different message hint for mal-data & empty data
      - Material design elements like CardView, SnackBar
  - Test cases
      - To increase testablility: define EmployeeRepository interface, will can be easily implemented by test class to mock response
      - Main coverage: 
        - Repository test covers most of reponse types (code of 200, 400, etc)    
        - Usecase test cover all 3 parameter types of its method
        - ViewModel test case cover 3 types of Usecases.
3. Copied-in code or copied-in dependencies: only 3 helper classes
   - package com.example.employeedirection.common.model.Event
   - package com.example.employeedirection.feature.employee_list.common.util.getOrAwaitValueTest for testing
   - package com.example.employeedirection.common.MainCoroutineRule for testing

4. Time of duration: 5-7 hours
