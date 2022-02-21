# CIS-350-Term-Project

## INSTALL
1. Clone this repository in your intellij IdeasProject directory.
2. If you already have an android project copy the local.properties into project.
3. If you don't, rename local.properties.bak to local.properties and set the skd.dir
4. Open project. Intellij will take a few minutes to rebuild gradle.
5. Build project
6. Run project. You may have to create/configure a virtual android phone first.

--- 
## Todo

### Now - things that probably should be started immediately.
1. Junit tests. We need for the code coverage section.  
    Low hanging fruit:
   1. ~~Items Class. All setters and getters~~
   2. DataBaseHelp. Should only have to test: ~~create, add, get, updatedelete~~, returnList  
   3. ItemViewHold. It's like three line. Prob need to google how to set it up in junit.
2. Use Case Descriptions. There is **HIGH** volume of writing that needs to be done on those. I dropped his template into the writeup, and set the Id and name, I haven't had time for anything else.

### Release 1 Requirements
1. ~~Cover page with project name and names of team members~~
2. Project description & List of features implemented in release 1
3. ~~Sample screenshots of your application~~
4. ~~Use case diagram (system boundary diagram)~~
5. Use case descriptions (using the template provided on Blackboard)
   1. ~~template dropped in writeup~~
   2. finished
6. Design diagrams (such as Class diagrams)
   1. ~~Class diagrams~~
   2. Other ones????
7. Usage of static source code analyzers for
   1. Enforcing coding standards/conformance (using tools like Checkstyle or
   other IDE/language specific)
   2. Finding potential bugs in the source code (using tools like SpotBugs or
   other IDE/language specific)
8. ~~URL to application code repository (on GitHub)~~
9. ~~Project website on GitHub Pages at http://username.github.io/repository/~~
10. Unit tests and code coverage reports from unit testing and functional/system
    testing using tools such as
    1. JUnit (and EclEmma for Eclipse)
    2. Or, other appropriate tools for IDE/language used
11. Roles/Responsibilities of each team member of the project
    1. Ben Allen
    2. Devin Elenbaase
    3. Bryan VanDyke
12. Self-reflection by each team member
    1. Ben Allen
    2. Devin Elenbaase
    3. Bryan VanDyke
13. Project Demo (not part of the release document; TBD) 

### Release 2 Requirements
1. TBD

### Program Overall
1. Backend database
   1. ~~Create Database~~
   2. Update Database
   3. ~~Read all~~
   4. ~~Write~~
   5. Update
   6. ~~Delete~~
   7. ~~Read One~~
2. Database Schema
   1. ~~Title~~
   2. ~~Entry Type~~
   3. Calendar Items
   4. Alarm Items
   5. Todo Items
   6. Reminder Items
3. Front End
   1. ~~RecyclerView container~~
   2. ~~Add buttons~~
      1. ~~Extended Floating Action Button~~
   3. Update/Edit button. 
      1. Edit button on each list item?
   4. Individual Add Screens
      1. Calendar
      2. Todo
      3. Reminder
      4. Alarm
   5. Individual Update Screens (reuse add activities?)
      1. Calendar
      2. Todo
      3. Reminder
      4. Alarm
4. Backend Functionality
   1. Alarm 
      1. Play default alarm sound
      2. Notification
   2. Reminder
      1. Notifications
      2. Sleep notification
   3. Todo
      1. check off / done
   4. Calendar
      1. single event
      2. repeating event
      3. multiday event
      4. Notification
      



--- 
## Authors
**Ben Allen**  
Github:[@AllenStudent](https://www.github.com/AllenStudent)  
Email:[allebenj@mail.gvsu.edu](mailto:allebenj@mail.gvsu.edu)  

**Devin Elenbaase**  
Github:[@elenbaad](https://www.github.com/elenbaad)  
Email:[elenbaad@mail.gvsu.edu](mailto:elenbaad@mail.gvsu.edu)  

**Bryan VanDyke**  
Github:[@bryanvandyke](https://www.github.com/bryanvandyke)  
Email:[vandybry@mail.gvsu.edu](mailto:vandybry@mail.gvsu.edu)  

**WIP**
## Synopsis
**Studious** is planned to be an android-based student organizational assistant, with primary feature goals being...
1. Calendar/Event-planner to allow students to keep track of their upcoming exams, projects, presentations, and etc. in one place.
2. Ability to keep track of how many work-hours have been committed to a single project/goal.
3. Ability to set weekly goals/to-do lists for work-hours put toward specific projects.
 

This will assist a student in organizing and using their time efficiently through the chaos that is college scheduling.

A possible secondary stretch feature would be a repository for students to upload a certain professor's general assignment scheduling for a specific class. E.G. allowing students to see if their CIS 350 class will have a semester-long group project as opposed to CIS 241 having 4 individual projects throughout the semester.
