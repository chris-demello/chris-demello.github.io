## Portfolio Navigation
- [Professional Self-Assessment](#professional-self-assessment)
- [Code Review](#code-review)
- [Enhancement 1: Software Design & Engineering](#enhancement-one-software-design-and-engineering)
- [Enhancement 2: Algorithms & Data Structures](#enhancement-two-algorithms-and-data-structures)
- [Enhancement 3: Databases](#enhancement-three-databases)
- [Artifact Access](#artifact-access)

## Professional Self-Assessment

Completing the Computer Science program and creating my ePortfolio has strengthened both my technical expertise and my professional direction. Throughout the program, I progressed from building simple, functional applications to designing and implementing scalable, secure systems that adhere to best practices. The capstone process required me to revisit prior coursework. In this case, it was my Weight Tracker mobile application, and I improved upon it through architectural refactoring, algorithmic optimization, and database redesign. This experience reinforced my professional values of clean design, measurable performance improvement, and secure data handling. It helped me focus on data analytics and software development, where structured data, efficiency, and insight-driven decision-making are crucial.

Collaboration and communication have played an important role in my growth. Although most of my coursework was completed independently, I consistently approached projects as part of a larger collaborative effort by clearly defining my responsibilities, documenting my design decisions, and conducting self-code reviews when needed. In my profession, my experience in healthcare systems and technical support has strengthened my ability to communicate technical concepts to nontechnical stakeholders. Whether explaining system enhancements, discussing security improvements, or adapting workflows, I learned that effective communication is essential to successfully implement software. These experiences prepared me to contribute and to translate technical decisions into meaningful outcomes.

This program deepened my understanding of data structures, algorithms, software engineering, databases, and security. I applied algorithmic principles to improve performance by analyzing time complexity and selecting the appropriate data structures. I improved software engineering practices by implementing modular design, separation of concerns, and refactoring the service layer. My database enhancement incorporated normalization, indexing, and aggregation queries to improve analytical capability and performance. Additionally, I adopted a security-focused mindset by implementing password hashing, input validation, and parameterized queries to protect user data and prevent vulnerabilities. These skills demonstrate my ability to design computing solutions that balance efficiency, maintainability, and security. 

The artifact in this portfolio reflects this progression through software design and engineering, data structures and algorithms, and database, which showcase my abilities in each area. Each enhancement builds upon the same original application. This will explain how I thought out design decisions across multiple layers of a system to create a high-quality software solution. This portfolio will serve both as a reflection of my academic journey and as evidence of my commitment to contribute as a computer science professional.

## Code Review

The following link is my code review. This showcases the original project that was developed in CS360. The review starts by running the application and showing how it works. From a login screen to inputting daily weight entries, it displays a simple program. The video continues by reviewing the issues with the functionality that will need to be addressed. Following this, each class is reviewed, and I give feedback on how to improve the existing code and incorporate the three new enhancements.

[Code Review Link](https://youtu.be/snYcL6vZMM8)

## Enhancement One: Software Design and Engineering

The artifact selected for my ePortfolio is a Weight Tracker mobile application originally created in CS 360: Mobile Architecture and Programming.  It was created in December 2024. The original version allowed users to create an account and add/delete weight entries to help them track their goals. It covered the basic functionality and user interaction needed to work. This was especially true in design, where modularity, scalability, and security were lacking. For this enhancement, the artifact was expanded upon. It included a redesign of architecture, improved security handling, and a new dashboard feature that visualizes weight trends. These planned changes were implemented as part of the CS499 part one enhancement process. This builds upon the existing project while significantly increasing the technical and professional quality.

The selection process for this artifact was chosen because it demonstrates software design and engineering skills across several aspects. The refactoring process highlights object-oriented programming principles and separation of concerns by introducing utility, service, and repository classes. This abstracted away from the MainActivity and LoginActivity classes, improving maintainability and reusability. Adding the dashboard activity with a dynamic trend-line chart displays UI skills and the ability to translate raw data into visual insights for users (See DashBoardActivity and ChartUtils classes). Security improvements, such as password hashing and input validation (See the PasswordUtils and InputValidator classes), enable a security-focused approach to the application. These three areas within my enhancement show the ability to design maintainable, scalable, and secure system.

The enhancement met the following course outcomes. The refactored architecture and dashboard visualization demonstrates the use of techniques that deliver computing solutions. Secure coding practices show an awareness of threats and the importance of protecting user data in the application. The structured code and visual dashboard also align with outcomes supporting professional communication and decision-support systems, as the application provides clearer insights that can support user goal weight tracking and analysis. This resulted in no changes needed to the original outcome plan as these areas within the enhancements met the intended objectives. 

Specific outcomes met:
- Employ strategies for building collaborative environments that enable diverse audiences to support organizational decision-making in the field of computer science.  
-	Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts.  
-	Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals.
-	Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources.  

During the process of modifying my artifact, I found it valuable to understand how architectural decisions can affect an application's maintainability and scalability. One challenge was transitioning the logic out of the activity classes and into reusable components while maintaining the same functionality. Another challenge was implementing security, especially password hashing. This required much more research to ensure practices aligned with security standards. Overall, this process highlighted the importance of designing software that can be promoted to help with future employment and academic work.

Key files showcasing enhancement:
 - [DashboardActivity.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/DashboardActivity.java)
 - [ChartUtils.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/ChartUtils.java)
 - [PasswordUtils.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/PasswordUtils.java)
 - [InputValidator.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/InputValidator.java)
 - [AuthService.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/AuthService.java)
 - [GoalService.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/GoalService.java)


## Enhancement Two: Algorithms and Data Structures

The artifact selected for the algorithms and data structures enhancement is the same Weight Tracker mobile application that was developed in CS 360: Mobile Architecture and Programming. The original implementation contained a basic data storage and retrieval method, the artifact was expanded to include an improved search method. This enhancement implements a more efficient, scalable data structure for searching by weight and date. 

The artifact was selected because it demonstrates how algorithm and data structure choices affect an application's performance. In the original implementation, the search iterated through the list because weight entries were stored there. If a linear search had been used to find a weight by date, it would have to check every weight, or it would never find a match. Thus, resulting in a linear time complexity of O(n). However, for performance, a HashMap lookup mechanism was implemented (in the HashMapWeightLookup class). In this design, the date string was used as the key, and the corresponding WeightEntry object was stored as the value. This means the lookup can jump directly to the entry without having to examine every value. Thus, resulting in O(1) constant time, which is significantly faster than a linear search. Even though HashMap will use more memory, this algorithm's efficiency outweighs the cost of that memory usage. 

This enhancement met the course outcomes that were planned, particularly those related to designing and evaluating computing solutions using algorithmic principles and appropriate data structures. By adding a HashMap, the application has improved performance and scalability as user data grows. The solution also aligns with outcomes focused on delivering value through well-founded computing practices. The optimized lookup will improve the user experience by providing faster results and validating user input before performing the lookup, reinforcing a security-minded approach. No additional changes were required from the original plan, as the enhancement addresses the intended objective. 

Specific outcomes met:
-	Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals.
-	Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources.  
-	Design and evaluate computing solutions that solve a given problem using algorithmic principles and computer science practices and standards appropriate to its solution, while managing the trade-offs involved in design choices (data structures and algorithms)

Throughout this development process, I gained a stronger understanding of how algorithmic concepts translate directly into solutions that meet business needs and improve software. One challenge was to ensure the HashMap stayed synchronized with the database. This required modifying the index whenever LiveData updates were received. Another challenge was reusing the InputValidator class to ensure input was correct and no errors were output. I think this enhancement has strengthened my understanding of time complexity, data structure selection, and how to incorporate optimized algorithms into an Android application to improve performance, reliability, and scalability.

Key files showcasing enhancement:
- [HashMapWeightLookup.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/HashMapWeightLookup.java)

## Enhancement Three: Databases

The artifact selected for the databases enhancement is the same Weight Tracker mobile application as mentioned above. This application stores user credentials, daily weight entries, and goal weight data using a local SQLite database through Android Studio’s Room library. In its original form, the database design was functional but simple, relying on basic table structures and queries that did not fully leverage relational database principles or advanced query capabilities. For this enhancement, the artifact was expanded to improve database design, optimize query performance, and help support analytical features on the dashboard. These changes were implemented as part of the CS499 category three enhancement process to increase the technical and professional quality of the application.

This artifact was selected because it offered a good opportunity to demonstrate database skills that can be applied in software development. The enhancement focused on redesigning the schema to enforce relational integrity through foreign keys, normalizing tables to separate user, weight-entry, and goal-related data, and adding database indexes on frequently queried fields, such as userId and date. These changes improved query efficiency and scalability as the dataset grew larger. In addition, advanced aggregation queries were introduced to compute analytical metrics, such as total weight change and body mass index (BMI), which are displayed on the dashboard screen. Parameterized queries were used through the Room DAOs. This prevents SQL injection and ensures secure data handling. All of these improvements showcase skills in relational database design, indexing strategies, complex function aggregation, and secure query implementation, which are essential for data-driven applications.

The enhancement met the course outcomes that were planned. The redesigned schema and optimized queries demonstrate an ability to implement computing solutions that deliver value through database practices. The analytical queries support clearer communication and decision-making by transforming raw data into meaningful metrics that users can understand. Additionally, parameterized queries support a security mindset by reducing the risk of injection attacks. These enhancements successfully addressed these outcomes, and no update to the plan was needed.

Specific outcomes met:
-	Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts
-	Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals.
-	Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources.  

Through this enhancement, I have learned how database design decisions can affect an application's performance, scalability, and security. One challenge faced was managing schema changes while keeping compatibility with Room’s migration system. This required a careful approach to the versioning and data integrity. Another issue was creating efficient aggregation queries that were easy to maintain and use with the service layer. Overall, this enhancement highlights the importance of database design and reinforces how optimized queries and normalized schemas can improve the reliability and analytics of the application.

Key files showcasing enhancement:
- [WeightEntry.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/WeightEntry.java)
- [WeightEntryDAO.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/WeightEntryDAO.java)
- [WeightRepository.java](https://github.com/chris-demello/chris-demello.github.io/blob/main/WeightRepository.java)

## Artifact Access

- [Download Original Artifact (Zip file)](https://github.com/chris-demello/chris-demello.github.io/raw/main/Weight%20Tracker%20App_CS360_Original.zip)
- [Download Enhanced Artifact (Zip file)](https://github.com/chris-demello/chris-demello.github.io/raw/main/WeightTrackerApp_CS499_Enhanced.zip)
- [Browse Repository](https://github.com/chris-demello/chris-demello.github.io)
