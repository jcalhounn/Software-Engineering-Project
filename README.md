# Software-Engineering-Project

![SystemDiagram](https://github.com/jcalhounn/Software-Engineering-Project/blob/main/SystemDiagram.jpg)


Project Operation:
  Takes in an assortment of numbers, finds the GCD, and converts the numbers and GCD to hexadecimal.

Functions:
  - Multithreading; max threads = 5


Assignment 7 CPU Usage Improvements over 3 tests

BASELINE CPU Usage:
CPU Usage: 2.8%, 3.0%, 2.2%

IMPROVED CPU Usage:
CPU Usage: 1.4%, 1.6%, 1.8%

The Following link leads to the Benchmark Test that we used to observe and improve CPU Usage
https://github.com/jcalhounn/Software-Engineering-Project/blob/main/.github/test/BenchmarkTest.java

To gain a significant CPU Usage improvement, we used one less for loop inside of the EngineManager, allowing for the code to iterate less times through the
Iterable<Integer> list that comes in from the inputConfig. This gives the CPU less tasks to complete and lowering the usage overall.
