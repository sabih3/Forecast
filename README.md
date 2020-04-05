# Forecast
  An Android weather information application powered by openweathermap.org  
  
# About Project
  This project is built on MVVM architecture & repository pattern along with Unit Tests coverage.
  The unit tests have been written for repoistory,VM,Screens & some utilities which have been used in the code.
  
# Build and Execution

# Step 1- Obtaining the project

The project can be obtained in 2 ways:
1) Cloning directly into Android Studio, by opening :
File -> New -> Project from Version Control -> Git and then inserting this repository http address. Can be found by clicking on 'Clone or download' button on this page.

2) Downloading project as zip and then extracting the files on any location of choice. Then opening the project in Android Studio. For that, Open Android Studio, then choose File -> Open-> Navigate to extracted project folder, then click Open.

# Step 2- Building the project
Once the project is opened in Android Studio, the project will automatically start to build itself.After successful build, run the project on any Virtual Device, or attach any physical device with USB Debugging Enabled to run it on physical device.
  
# Executing Tests Individually
The project contains following Unit Testable files.

1) app/src/test/framework/network/BaseRepo.kt
2) app/src/test/framework/CityWeatherRemoteRepositoryTest.kt
3) app/src/test/framework/ForecastRemoteRepositoryTest.kt
4) app/src/androidTest/FileUtilsTest

To run the tests, open the desired file, and click on small green colored play icon, placed just beside the file name.This will run all tests of that file. Individual tests can also be executed, by simply clicking the green play button beside the test method name.  

# Generating Tests Coverage Report
To generate the test coverage report, navigate to app -> src -> test -> java folder. Right click java folder and choose 'Run All Tests with Coverage' option. This will trigger all tests inside test-> java folder to run, and will provide the stats after completion.
