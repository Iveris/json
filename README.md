# What this Program does:

This command line program parses JSON arrays into a single JSON object with key value pairs representing each object in the original array. The current implementation is specific to turning turning this particular data set:

<p>
[ <br>
&ensp;{<br>
&ensp;&ensp;  "url": "https://…. Valid url", <br>
&ensp;&ensp;  "path":  "path_value_1", <br>
&ensp;&ensp;  "size": integer <br>
&ensp;}, ...… <br>
]
</p>

into this:

<p>
{ <br>
&ensp;  "path_value_1": <br>
&ensp;&ensp;  { <br>
&ensp;&ensp;&ensp;  "url": "url value", <br>
&ensp;&ensp;&ensp;  "size": integer<br>
&ensp;&ensp;  }, ...... <br>
}
</p>

## How to use it:

From the command line one only needs to enter the path to the input file (either on the local disk or a URL). The default output file is “output.json”, but a custom output file name can be entered as an optional second argument on the command line. 

## How it works:

The program creates separate threads for read and write operations for efficiency. The threads communicate via a ConcurrentLinkedQueue object that is thread-safe and allows for an object to be added to the queue while another object is removed from the queue. Once the reader thread closes, the write thread will remove each object from the ConcurrentLinkedQueue and write them to the output file at which point the program will print a summary of how many objects were processed or skipped, and report any errors that occurred during processing.

## Design Choices:

Using SOLID design principles as a guide, each class has a single responsibility. 

Read implementations parse the JSON objects into java objects. Read processing classes coordinate the placement of objects created by the read implementation into an object (in this case a ConcurrentLinkedQueue) that can be accessed by the write service classes.

Services such as URL validation, file size validation, path validation, etc., are separated into discreet classes for easy use and reuse. 

Most classes implement behavior defined in an interface and these interfaces are used throughout the program to allow for easy substitution of implementations and to enforce consistent behavioral expectations. 

This design utilizes dependency injection extensively to allow for changes in implementation with minimal updates to the code.

For example: the main program passes the input and output arguments from the command line to the read and write threads. The read and write threads are instantiated with generic runnable processing interfaces that use dependency injection to create more specific processing read and write processing classes. The processing classes coordinate file processing between read and write services. These more specific processing classes themselves also use dependency injection to get specific read or write implementations to process the input and output files.

The result is that should read and write services can be altered or substituted with only a need to update the injector class so that it injects the new implementation files. 

Should the new implementations also require different coordination between the two processes, then the process injectors would also need to be updated to point to the new process files. Even still, the main application class would not need any updating.

Dependency injection also allows each class to be testing as independently as desired. The writing and reading implementations getting the most rigorous testing to ensure that formatting is correct and that errors and exceptions are caught and handled appropriately.

## Additional Features

***Summary Feature:*** &nbsp;the program completes with a summary of the number of JSON objects read and created by the read processor, and a number of any objects that were skipped. 

***Error Reporter:*** &nbsp; the error reporter also prints a summary of any errors or problems encountered in a user friendly format. 

***Logger:*** &nbsp; the logger records exceptions for development and technical support.


## Libraries used 

[GSON](https://github.com/google/gson) for JSON serialization and deserialization <br>
[Apache Commons Validator](https://commons.apache.org/proper/commons-validator/) for URL validation <br>
[Apache Log4j](https://logging.apache.org/log4j/2.x/) for logging <br>
[JUNIT 5 Jupiter](https://junit.org/junit5/) for testing <br>

