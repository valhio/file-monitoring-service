<!DOCTYPE html>
<html lang="en">

<body>
<h1 align="center"> File Monitoring Service </h1>
 
<p align="center">
The Service scans, analyzes, and reports on unexpected changes to important files in your desired directory.<br> 
It monitors changes such as <strong>CREATION</strong>, <strong>MODIFICATION</strong> and <strong>DELETION</strong>.  <br>
By doing so, the Service provides a layer of file, data, and application security, thus informing you of Malware Attacks. 
</p>
<p align="center"><sub><sup><sub>This application was made quick and is in pre-alpha-beta-gama release, so dont @ me</sub></sup></sub></p>


# Set-up
> * Go into **src/main/resources/fileWatcher.properties** and change the directory.path property's value to your desired path

# Monitoring types

- [CleanDirectoryTreeMonitor](#cleandirectorytreemonitorservice)
- [RawDirectoryTreeMonitor](#rawdirectorytreemonitorservice)
- [SingleDirectoryMonitor](#singledirectorymonitorservice)

# RawDirectoryTreeMonitorService

#### A Service that monitors given directory and all of its sub-directories.

* ## Test case example:

> * You set the Service to monitor folder - **C:/Desktop/files**. ("files" contains the folders: **new-f1/new-f2**)
> * You create a **new-text.txt** in : **C:/Desktop/files/new-f1/new-f2/**
> * Firstly - The Service logs that **new-text.txt** was created:  
> ```ENTRY_CREATE new-text.txt : C:\Desktop\files\new-f1\new-f2\new-text.txt```
> * Secondly - The Service logs that there was a **creation** and **modification** in every single sub folder (**new-f1,new-f2**):  
> ```ENTRY_CREATE new-f2\new-text.txt : C:\Desktop\files\new-f1\new-f2\new-text.txt```  
> ```ENTRY_CREATE new-f1\new-f2\new-text.txt : C:\Desktop\files\new-f1\new-f2\new-text.txt```  
> ```ENTRY_MODIFY new-f2 : C:Desktop\files\new-f1\new-f2```  
> ```ENTRY_MODIFY new-f1\new-f2 : C:\Desktop\files\new-f1\new-f2```  

# CleanDirectoryTreeMonitorService

#### A Service that monitors given directory and all of its sub-directories.

* ## Test case example:

> * You set the Service to monitor folder - **C:/Desktop/files**. ("files" contains the folders: **new-f1/new-f2**)
> * You create a **new-text.txt** in : **C:/Desktop/files/new-f1/new-f2/**
> * Firstly - The Service logs that **new-text.txt** was created:  
> ```ENTRY_CREATE new-text.txt : C:\Desktop\files\new-f1\new-f2\new-text.txt```  
> * Secondly - The service logs only the folder where the **creation** happened was modified:
> ```ENTRY_MODIFY new-f2 : C:\Desktop\files\new-f1\new-f2```

# SingleDirectoryMonitorService

#### A Service that monitors only given directory.

* ## Test case example 1:

> * You set the Service to monitor folder - **C:/Desktop/files**. ("files" contains the folders: **new-f1/new-f2**)
> * You create a **new-text.txt** in : **C:/Desktop/files/**
> * The Service then logs that only a **CREATION** event occurred inside of the monitored directory:  
> ```ENTRY_CREATE new-text.txt : C:\Desktop\files\new-text.txt```  

* ## Test case example 2:

> * You set the Service to monitor folder - **C:/Desktop/files**. ("files" contains the folders: **new-f1/new-f2**)
> * You create a **new-text.txt** in : **C:/Desktop/files/new-f1/**
> * The Service then logs that only a **MODIFICATION** event occurred inside of the *new-f1* directory:  
> ```ENTRY_MODIFY new-f1 : C:\Desktop\files\new-f1```  

* ## Test case example 3:

> * You set the Service to monitor folder - **C:/Desktop/files**. ("files" contains the folders: **new-f1/new-f2**)
> * You create a **new-text.txt** in : **C:/Desktop/files/new-f1/new-f2/**
> * The Service then returns nothing, as it does not know about the existence of the *new-f2* folder.  


# TODO:
> * Implement MySQL or PL/SQL Database integration /w JPA
> * Integrate and Refactor for Spring Web
> * Add Security features
> * Add better Exception Handling
> * Refactor for better abstraction

<p align="center"><sub><sup><sub><sup><sub><sup><sub><sup><sub><sup><sub><sup><sub><sup><sub><sup>holly shit this readme took way too long to make</sup></sub></sup></sub></sup></sub></sup></sub></sup></sub></sup></sub></sup></sub></sup></sub></p>
</body>
</html>
