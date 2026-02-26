# Nock Chatbot

Nock Chatbot User Guide

Welcome to Nock, your personal task management chatbot.
Nock helps you track todos, deadlines, and events using simple text commands.

1. Quick Start

Ensure you have Java 17 or above installed.

Download the latest nock.jar.

Open a terminal in the folder containing the .jar file.

Run:

java -jar nock.jar

Type commands into the chatbot.

2. Features

 2.1 Adding Tasks

   2.1.1Add a Todo

   Adds a simple task without a date.

      todo <description>

   Example:
      todo read book


 2.1.2Add a Deadline

   Adds a task with a due date.

      deadline <description> /by <date>

Example:
deadline submit report /by 2026-03-10

   2.1.3Add an Event

   Adds a task with start and end time.

      event <description> /from <start> /to <end>

Example:

event team meeting /from 2pm /to 4pm
2.2 Listing Tasks

Displays all tasks currently stored.

      list


2.3 Marking a Task as Done

Marks a task as completed.

      mark <task number>

Example:

mark 2


2.4 Unmarking a Task

Marks a completed task as not done.

      unmark <task number>

Example:

unmark 2


2.5 Deleting a Task

Deletes a task from the list.

      delete <task number>

Example:

delete 3


2.6 Finding Tasks

Searches tasks by keyword.

      find <keyword>

Example:

find report


2.7 Exiting the Program

Closes the chatbot.

      bye
3. Command Summary
   Command	Format
   Add todo	todo <description>
   Add deadline	deadline <description> /by <date>
   Add event	event <description> /from <start> /to <end>
   List tasks	list
   Mark task	mark <number>
   Unmark task	unmark <number>
   Delete task	delete <number>
   Find task	find <keyword>
   Exit	bye


4. Notes

   Task numbering starts from 1.

   Dates and times can be entered in flexible text format.

   Invalid commands will prompt an error message. 