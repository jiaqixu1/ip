# Nock Chatbot User Guide

> Nock is a lightweight task management application that helps you manage your todos, deadlines, and events efficiently.

Welcome to **Nock**, your personal task management chatbot.  
Nock Chatbot helps you track todos, deadlines, and events using simple text commands.

---

# Table of Contents

- [Quick Start](#quick-start)
- [Features](#features)
   - [Adding Tasks](#adding-tasks)
      - [Add a Todo](#add-a-todo)
      - [Add a Deadline](#add-a-deadline)
      - [Add an Event](#add-an-event)
   - [Listing Tasks](#listing-tasks)
   - [Marking a Task](#marking-a-task-as-done)
   - [Unmarking a Task](#unmarking-a-task)
   - [Deleting a Task](#deleting-a-task)
   - [Finding Tasks](#finding-tasks)
   - [Help Command](#help-command)
   - [Exiting the Program](#exiting-the-program)
- [Notes](#notes)

---

# Quick Start

1. Ensure you have **Java 17 or above** installed.
2. Download the latest `nock.jar`.
3. Open a terminal in the folder containing the `.jar` file.
4. Run:

```
java -jar nock.jar
```

5. Type commands into the chatbot and press **Enter**.

---

# Features

---

## Adding Tasks

### Add a Todo

Adds a simple task without a date.

**Format**

```
todo <description>
```

**Example**

```
todo read book
```

---

### Add a Deadline

Adds a task with a due date.

**Format**

```
deadline <description> /by <yyyy-MM-dd>
```

**Example**

```
deadline submit report /by 2026-03-10
```

> **Important:** Date must follow the format `yyyy-MM-dd`.

---

### Add an Event

Adds a task with start and end time.

**Format**

```
event <description> /from <start> /to <end>
```

**Example**

```
event team meeting /from 2pm /to 4pm
```

---

## Listing Tasks

Displays all tasks currently stored.

**Format**

```
list
```

---

## Marking a Task as Done

Marks a task as completed.

**Format**

```
mark <task number>
```

**Example**

```
mark 2
```

---

## Unmarking a Task

Marks a completed task as not done.

**Format**

```
unmark <task number>
```

**Example**

```
unmark 2
```

---

## Deleting a Task

Deletes a task from the list.

**Format**

```
delete <task number>
```

**Example**

```
delete 3
```

---

## Finding Tasks

Searches tasks by keyword.

**Format**

```
find <keyword>
```

**Example**

```
find report
```

> Search is case-insensitive.

---

## Help Command

Displays a summary of all available commands.

**Format**

```
help
```

---

## Exiting the Program

Closes the chatbot.

**Format**

```
bye
```

---

# Notes

- Task numbering starts from **1**.
- Invalid commands will prompt an error message.
- All commands must follow the specified format.

---

# Command Summary

| Command | Format |
|----------|--------|
| `todo` | `todo <description>` |
| `deadline` | `deadline <description> /by <yyyy-MM-dd>` |
| `event` | `event <description> /from <start> /to <end>` |
| `list` | `list` |
| `mark` | `mark <task number>` |
| `unmark` | `unmark <task number>` |
| `delete` | `delete <task number>` |
| `find` | `find <keyword>` |
| `help` | `help` |
| `bye` | `bye` |