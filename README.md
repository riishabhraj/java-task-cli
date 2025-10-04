# Task CLI

A simple Java command-line application to manage your tasks. Tasks are stored in a local JSON file (`tasks.json`).

## Features
- Add new tasks
- Update task descriptions
- Delete tasks
- Mark tasks as in-progress or done
- List tasks by status
- Find tasks by ID

## Prerequisites
- Java 17 or newer
- Maven

## Setup Instructions

1. **Clone the repository**
   ```
   git clone <your-repo-url>
   cd task-cli
   ```

2. **Build the project**
   ```
   mvn clean package
   ```

3. **Run the CLI application**
   ```
   java -cp target/classes org.example.TaskCLIApp
   ```

## Usage

Use the following commands in the CLI:

- `task-cli add <description>`
  - Add a new task with the given description.
  - Example: `task-cli add "Buy groceries"`

- `task-cli update <id> <description>`
  - Update the description of a task by ID.
  - Example: `task-cli update 1 "Buy groceries and cook dinner"`

- `task-cli delete <id>`
  - Delete a task by ID.
  - Example: `task-cli delete 2`

- `task-cli mark-in-progress <id>`
  - Mark a task as in-progress.
  - Example: `task-cli mark-in-progress 3`

- `task-cli mark-done <id>`
  - Mark a task as done.
  - Example: `task-cli mark-done 3`

- `task-cli list <status>`
  - List tasks by status (`All`, `in-progress`, `done`).
  - Example: `task-cli list All`

- `task-cli find <id>`
  - Find and display a task by ID.
  - Example: `task-cli find 1`

## Data Persistence
- All tasks are saved in `tasks.json` in the project root.
- The file is created automatically if it does not exist.

## Notes
- Make sure to run commands from the project root directory.
- The CLI application must be running to accept commands.

## License
MIT

## Project URL

For more details and roadmap, visit: [Task Tracker Project Roadmap](https://roadmap.sh/projects/task-tracker)
