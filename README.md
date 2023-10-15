## Project Description

This project aims to create a versatile file storage management library, offering a range of operations and search capabilities. It will also support storage configuration. The component should be designed with a clear API specification.

In addition to the specification, two separate implementations will be developed. One implementation stores files on Google Drive with Gmail-based authentication, while the other stores files in a local file system.

### Storage Creation and Configuration

- Storage creation involves setting up an initial directory that serves as the root of the storage.
- Configuration options include setting storage size limits, restrictions on file extensions, and the number of files allowed in a directory.
- The configuration file is stored in the storage's root directory.
- All operations will verify that they comply with the specified configuration.

### Core Storage Operations

- Creating directories with various naming patterns.
- Storing one or more files at specified locations within the storage, regardless of the operating system.
- Deleting files and directories.
- Moving files between directories.
- Downloading files from the storage to a local folder.
- Renaming files and directories.
- All paths are defined relative to the root directory.
- If a specific operation is unsupported on Google Drive, it will throw an UnsupportedOperation exception.

### Storage Search Features

- Implement search operations for various criteria, including retrieving files based on directory, extensions, names, and more.
- Sort files based on criteria such as name, creation date, or modification date in ascending or descending order.
- Filter the displayed data for search results to show relevant details.

For the component specification, provide clear API documentation in either Serbian or English.

Additionally, an application should demonstrate the use of the storage component via the command line. The program interacts with a single storage implementation (local or remote) and a specific storage location specified as a command-line argument.

The program should work with the component's specification and a single implementation while being designed to use only the specification's elements.

Ensure that the implementation of components and the application considers the components representing specific implementations as runtime dependencies of the command-line program.

Packaging of components and the command-line program, specification of dependencies, and documentation generation should be automated using a build tool.
