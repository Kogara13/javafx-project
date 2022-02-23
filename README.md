# javafx-project
JavaFX project in which a GUI can be used to keep track of a list of instructors at a university

Inside this project are two .txt files, department.txt and instructor.txt. The former contains department names, locations, and ID numbers respectively. The latter contains information on individual instructors, including their IDs, names, and current departments.

Within the GUI, there are three options

1. Search for an instructor's information using their ID
  - This will retrieve the instructor's name and department from instructor.txt and their department's location from department.txt and display them       under the search field


2. Insert a new instructor into the .txt file
  - The user will be prompted to enter information on the instructor, including their ID, name, and department. Errors will be displayed if the            instructor ID is already taken, the department name does not exist, or both.
  - Once, submitted, the new instructor's information will be written to instructor.txt on a new line.

3. Exit from the program

Note: The absolute path of the .txt files are written into the source code from an external drive
