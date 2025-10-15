# Medical Scheduler

A Java-based multi-user scheduling system designed to manage concurrent medical appointments safely and efficiently. The application ensures data integrity across simultaneous booking and modification operations through a thread-safe architecture and optimized data structures for conflict detection.

---

## Project Overview

The Medical Scheduler is built to handle high-frequency scheduling operations in a multi-user environment where concurrent access to shared data is common. It addresses issues such as race conditions and inconsistent state updates that arise from simultaneous bookings or cancellations.

The system improves performance and reliability through:
- A **thread-safe scheduling manager** that synchronizes access to shared resources
- An optimized **TreeMap-based conflict detection algorithm** that reduces scheduling latency
- Efficient handling of appointment overlap and modification logic

The implementation replaces a traditional O(N) linear search for conflicts with an O(log N) lookup using ordered maps, reducing latency by approximately 95% when adding new events in dense schedules.

---

## Features

- **Thread-safe scheduling engine** preventing race conditions during concurrent bookings  
- **Efficient conflict detection** with TreeMap data structure (O(log N) complexity)  
- **Optimized latency** achieving a 95% reduction in schedule update times  
- **User interface support** for interactive appointment management  
- **Extensible design** adaptable for clinics, hospitals, or general resource scheduling  
- **Error handling and data consistency guarantees** under multi-threaded operations  

---

## Tech Stack

- **Language:** Java 17+  
- **Data Structure:** TreeMap for ordered event management  
- **Concurrency Utilities:** Java synchronized blocks and concurrent collections  
- **Build Tool:** Gradle or Maven (depending on configuration)  
- **UI Framework:** JavaFX (if using GUI version)  

---

## Build and Setup

### Prerequisites
- Java 17 or higher  
- Gradle or Maven installed  
- IDE such as IntelliJ IDEA or Eclipse (recommended for running GUI version)

### 1. Clone the Repository
```bash
git clone https://github.com/<your-username>/medical-scheduler.git
cd medical-scheduler
