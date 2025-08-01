<<<<<<< HEAD
# IEC 61131-3 IL to LD Converter

## Overview
This Clojure application converts IEC 61131-3 Instruction List (IL) programs to Ladder Diagram (LD) representation using Instaparse for parsing and cljfx for the graphical user interface.

## Features
- **Graphical User Interface**: Easy-to-use JavaFX-based GUI
- Parse IL programs using a formal grammar
- Convert IL instructions to LD elements
- Generate a textual representation of the Ladder Diagram
- Real-time conversion with error handling
- Example IL code loading
- Split-pane interface for input and output

## Dependencies
- Clojure 1.12.0
- Instaparse 1.5.0
- core.match 1.0.1
- cljfx 1.7.25 (for GUI)

## Usage

### GUI Mode (Default)
Run the application with:
```
lein run
```

This will open a graphical interface with:
- **Left Panel**: IL code input area
- **Right Panel**: LD diagram output (read-only)
- **Convert Button**: Process the IL code
- **Menu Bar**: File operations and help
- **Status Bar**: Shows conversion status and errors

### Console Mode
For testing or CLI usage:
```
lein run --console
```

### GUI Features
- **File Menu**:
  - Load Example: Loads sample IL code
  - Clear All: Clears both input and output
  - Exit: Closes the application

- **Real-time Status**: 
  - Success messages in green
  - Errors in red
  - Warnings in orange
  - Info messages in blue

### Supported IL Operations
- LD/LDN (Load/Load Negated)
- ST/STN (Store/Store Negated)
- AND/ANDN (And/And Negated)
- OR/ORN (Or/Or Negated)

## Example
Input IL in the GUI:
```
LD %I0.0    ; Load input bit 0
ANDN %I0.1  ; AND with inverted input bit 1
ST %Q0.0    ; Store result to output bit 0
```

Output LD (displayed in right panel):
```
Ladder Diagram:
Rung 1: Operand: %I0.0, Type: contact-load, Normally Closed: false
Rung 2: Operand: %I0.1, Type: contact-series, Normally Closed: true
Rung 3: Operand: %Q0.0, Type: coil-store, Normally Closed: false
```

## Running Tests
```
lein test
```

## Building
To create a standalone JAR:
```
lein uberjar
```

Then run with:
```
java -jar target/uberjar/il-to-ld-converter-0.1.0-SNAPSHOT-standalone.jar
```

## System Requirements
- Java 11 or higher (required for JavaFX)
- Leiningen 2.9.0 or higher

## Limitations
- Currently supports a subset of IL instructions
- Basic LD representation generation
- No graphical LD visualization (text-based output)
- Limited to basic IL programming constructs

## Future Enhancements
- Graphical ladder diagram rendering
- Support for more IL instructions (timers, counters, function blocks)
- File import/export functionality
- Syntax highlighting in IL editor
- LD diagram export to various formats
=======
# IEC 61131-3 IL to LD Converter

## Overview
This Clojure application converts IEC 61131-3 Instruction List (IL) programs to Ladder Diagram (LD) representation using Instaparse for parsing.

## Features
- Parse IL programs using a formal grammar
- Convert IL instructions to LD elements
- Generate a textual representation of the Ladder Diagram

## Dependencies
- Clojure 1.12.1
- Instaparse 1.4.12
- core.match 1.0.1

## Usage
Run the application with:
```
lein run
```

### Supported IL Operations
- LD/LDN (Load/Load Negated)
- ST/STN (Store/Store Negated)
- AND/ANDN (And/And Negated)
- OR/ORN (Or/Or Negated)

## Example
Input IL:
```
LD %I0.0    ; Load input bit 0
ANDN %I0.1  ; AND with inverted input bit 1
ST %Q0.0    ; Store result to output bit 0
```

## Running Tests
```
lein test
```

## Limitations
- Currently supports a subset of IL instructions
- Basic LD representation generation
- No graphical LD
>>>>>>> 924db9b (WSL Debian)
